package com.gzs.learn.common.util;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IpUtil {
    private static String serverIp;
    private static String serverName;

    static {
        try {
            serverIp = java.net.InetAddress.getLocalHost().getHostAddress().toString();
            serverName = java.net.InetAddress.getLocalHost().getHostName().toString();
        } catch (Throwable e) {
            serverIp = "";
            serverName = "";
        }
    }

    public static String getServerIp() {
        return serverIp;
    }

    public static String getServerName() {
        return serverName;
    }

    public static String getIpAdrress(HttpServletRequest request) {
        String xForwardIp = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(xForwardIp) && !"unKnown".equalsIgnoreCase(xForwardIp)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = xForwardIp.indexOf(",");
            if (index != -1) {
                return xForwardIp.substring(0, index);
            } else {
                return xForwardIp;
            }
        }
        String xRealIp = request.getHeader("X-Real-IP");
        xForwardIp = xRealIp;
        if (StringUtils.isNotEmpty(xForwardIp) && !"unKnown".equalsIgnoreCase(xForwardIp)) {
            return xForwardIp;
        }
        if (StringUtils.isBlank(xForwardIp) || "unknown".equalsIgnoreCase(xForwardIp)) {
            xForwardIp = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xForwardIp) || "unknown".equalsIgnoreCase(xForwardIp)) {
            xForwardIp = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(xForwardIp) || "unknown".equalsIgnoreCase(xForwardIp)) {
            xForwardIp = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(xForwardIp) || "unknown".equalsIgnoreCase(xForwardIp)) {
            xForwardIp = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(xForwardIp) || "unknown".equalsIgnoreCase(xForwardIp)) {
            xForwardIp = request.getRemoteAddr();
        }
        return xForwardIp;
    }

    public static String getLocalIp() {
        String ip;
        try {
            List<String> ipList = getHostAddress(null);
            // default the first
            ip = (!ipList.isEmpty()) ? ipList.get(0) : "";
        } catch (Exception ex) {
            ip = "";
            log.warn("Iputil get IP warn", ex);
        }
        return ip;
    }

    public static String getLocalIp(String interfaceName) {
        String ip;
        interfaceName = interfaceName.trim();
        try {
            List<String> ipList = getHostAddress(interfaceName);
            ip = (!ipList.isEmpty()) ? ipList.get(0) : "";
        } catch (Exception ex) {
            ip = "";
            log.warn("Iputil get IP warn", ex);
        }
        return ip;
    }

    /**
     * 获取已激活网卡的IP地址
     *
     * @param interfaceName 可指定网卡名称,null则获取全部
     * @return List<String>
     */
    private static List<String> getHostAddress(String interfaceName) throws SocketException {
        List<String> ipList = new ArrayList<String>(5);
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            Enumeration<InetAddress> allAddress = ni.getInetAddresses();
            while (allAddress.hasMoreElements()) {
                InetAddress address = allAddress.nextElement();
                if (address.isLoopbackAddress()) {
                    // skip the loopback addr
                    continue;
                }
                if (address instanceof Inet6Address) {
                    // skip the IPv6 addr
                    continue;
                }
                String hostAddress = address.getHostAddress();
                if (null == interfaceName) {
                    ipList.add(hostAddress);
                } else if (interfaceName.equals(ni.getDisplayName())) {
                    ipList.add(hostAddress);
                }
            }
        }
        return ipList;
    }
}
