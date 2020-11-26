package com.ruoyi.serial.snowflake;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Charsets;
import com.google.common.collect.Maps;
import com.gzs.learn.common.util.IpUtil;
import com.gzs.learn.common.util.JsonUtil;
import com.ruoyi.serial.config.SerialConfig;
import com.ruoyi.serial.exception.CheckLastTimeException;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SnowflakeZookeeperHolder {
    private String notifyZk;
    private String propPath;
    private String prefixZkPath;
    private String pathForever;// 保存所有数据持久的节点
    private String zkAddressNode = null;// 保存自身的key ip:port-000000001

    private int dataCenterID;
    private int workerID;
    private String appID;
    private String appIp;
    private Integer appPort;
    private long lastUpdateTime;

    @Autowired
    public SnowflakeZookeeperHolder(SerialConfig serialConfig) {
        this.dataCenterID = serialConfig.getDataCenterId();
        if (dataCenterID > SnowflakeConst.MAX_DATA_CENTER_ID) {
            throw new Error("dataCenterId must no more than:" + SnowflakeConst.MAX_DATA_CENTER_ID);
        }
        this.appPort = serialConfig.getServerPort();
        this.appIp = IpUtil.getLocalIp();
        this.appID = appIp + ":" + appPort;
        this.notifyZk = serialConfig.getZk();
        this.prefixZkPath = "/snowflake/" + serialConfig.getName();
        this.pathForever = prefixZkPath + "/forever";
        this.propPath = System.getProperty("java.io.tmpdir") + File.separator + serialConfig.getName()
                + "/serialconf/{port}/workerID.properties";
    }

    public boolean init() {
        try {
            CuratorFramework curator = createWithOptions(notifyZk, new RetryUntilElapsed(1000, 4), 10000, 6000);
            curator.start();
            Stat stat = curator.checkExists().forPath(pathForever);
            if (stat == null) {
                // 不存在根节点,机器第一次启动,创建/snowflake/ip:port-000000000,并上传数据
                zkAddressNode = createNode(curator);
                // worker id 默认是0
                updateLocalWorkerID(workerID);
                // 定时上报本机时间给forever节点
                scheduledUploadData(curator, zkAddressNode);
                return true;
            } else {
                Map<String, Integer> nodeMap = Maps.newHashMap();// ip:port->00001
                Map<String, String> realNode = Maps.newHashMap();// ip:port->(ipport-000001)
                // 存在根节点,先检查是否有属于自己的根节点
                List<String> keys = curator.getChildren().forPath(pathForever);
                for (String key : keys) {
                    String[] nodeKey = key.split("-");
                    realNode.put(nodeKey[0], key);
                    nodeMap.put(nodeKey[0], Integer.parseInt(nodeKey[1]));
                }
                Integer workerid = nodeMap.get(appID);
                if (workerid != null) {
                    // 有自己的节点,zkAddressNode=ip:port
                    zkAddressNode = pathForever + "/" + realNode.get(appID);
                    workerID = workerid;// 启动worder时使用会使用
                    if (!checkInitTimeStamp(curator, zkAddressNode)) {
                        throw new CheckLastTimeException("init timestamp check error,forever node timestamp gt this node time");
                    }
                    // 准备创建临时节点
                    doService(curator);
                    updateLocalWorkerID(workerID);
                    log.info("endpoint ip-{} port-{} workid-{} childnode and start SUCCESS", appIp, appPort, workerID);
                } else {
                    // 表示新启动的节点,创建持久节点 ,不用check时间
                    String newNode = createNode(curator);
                    zkAddressNode = newNode;
                    String[] nodeKey = newNode.split("-");
                    workerID = Integer.parseInt(nodeKey[1]);
                    doService(curator);
                    updateLocalWorkerID(workerID);
                    log.info("endpoint ip-{} port-{} workid-{},create own node on forever node and start SUCCESS ", appIp, appPort,
                            workerID);
                }
            }
        } catch (Exception e) {
            log.error("Start node ERROR {}", e);
            try {
                Properties properties = new Properties();
                properties.load(new FileInputStream(new File(propPath.replace("{port}", appPort + ""))));
                workerID = Integer.valueOf(properties.getProperty("workerID"));
                log.warn("START FAILED ,use local node file properties workerID-{}", workerID);
            } catch (Exception e1) {
                log.error("Read file error ", e1);
                return false;
            }
        }
        return true;
    }

    private void doService(CuratorFramework curator) {
        scheduledUploadData(curator, zkAddressNode);// /snowflake_forever/ip:port-000000001
    }

    private void scheduledUploadData(final CuratorFramework curator, final String zkNode) {
        Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = new Thread(r, "schedule-upload-time");
            thread.setDaemon(true);
            return thread;
        }).scheduleWithFixedDelay(() -> updateNewData(curator, zkNode), 1L, 3L, TimeUnit.SECONDS);// 每3s上报数据
    }

    private boolean checkInitTimeStamp(CuratorFramework curator, String zkNode) throws Exception {
        byte[] bytes = curator.getData().forPath(zkNode);
        Endpoint endPoint = deBuildData(new String(bytes));
        // 该节点的时间不能小于最后一次上报的时间
        return !(endPoint.getTimestamp() > System.currentTimeMillis());
    }

    /**
     * 创建持久顺序节点 ,并把节点数据放入 value
     *
     * @param curator
     * @return
     * @throws Exception
     */
    private String createNode(CuratorFramework curator) throws Exception {
        try {
            return curator.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(pathForever + "/" + appID + "-", buildData().getBytes());
        } catch (Exception e) {
            log.error("create node error msg {} ", e.getMessage());
            throw e;
        }
    }

    private void updateNewData(CuratorFramework curator, String path) {
        try {
            if (System.currentTimeMillis() < lastUpdateTime) {
                return;
            }
            curator.setData().forPath(path, buildData().getBytes());
            lastUpdateTime = System.currentTimeMillis();
        } catch (Exception e) {
            log.info("update init data error path is {} error is {}", path, e);
        }
    }

    /**
     * 构建需要上传的数据
     *
     * @return
     */
    private String buildData() throws JsonProcessingException {
        Endpoint endpoint = new Endpoint(appIp, appPort + "", System.currentTimeMillis());
        String json = JsonUtil.toJSONString(endpoint);
        return json;
    }

    private Endpoint deBuildData(String json) throws IOException {
        Endpoint endpoint = JsonUtil.parseObject(json, Endpoint.class);
        return endpoint;
    }

    /**
     * 在节点文件系统上缓存一个workid值,zk失效,机器重启时保证能够正常启动
     *
     * @param workerID
     */
    private void updateLocalWorkerID(int workerID) {
        File leafConfFile = new File(propPath.replace("{port}", appPort + ""));
        boolean exists = leafConfFile.exists();
        log.info("file exists status is {}", exists);
        if (exists) {
            try {
                FileUtils.writeStringToFile(leafConfFile, "workerID=" + workerID, Charsets.UTF_8, false);
                log.info("update file cache workerID is {}", workerID);
            } catch (IOException e) {
                log.error("update file cache error ", e);
            }
        } else {
            // 不存在文件,父目录也肯定不存在
            try {
                boolean mkdirs = leafConfFile.getParentFile().mkdirs();
                log.info("init local file cache create parent dis status is {}, worker id is {}", mkdirs, workerID);
                if (mkdirs) {
                    if (leafConfFile.createNewFile()) {
                        FileUtils.writeStringToFile(leafConfFile, "workerID=" + workerID, Charsets.UTF_8, false);
                        log.info("local file cache workerID is {}", workerID);
                    }
                } else {
                    log.warn("create parent dir error===");
                }
            } catch (IOException e) {
                log.warn("craete workerID conf file error", e);
            }
        }
    }

    private CuratorFramework createWithOptions(String notifyZk, RetryPolicy retryPolicy, int connectionTimeoutMs, int sessionTimeoutMs) {
        return CuratorFrameworkFactory.builder().connectString(notifyZk).retryPolicy(retryPolicy).connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs).build();
    }

    /**
     * 上报数据结构
     */
    @Data
    public static class Endpoint {
        private String ip;
        private String port;
        private long timestamp;

        public Endpoint() {
        }

        public Endpoint(String ip, String port, long timestamp) {
            this.ip = ip;
            this.port = port;
            this.timestamp = timestamp;
        }
    }

    public String getZkAddressNode() {
        return zkAddressNode;
    }

    public int getWorkerID() {
        return workerID;
    }

    public int getDataCenterID() {
        return dataCenterID;
    }
}
