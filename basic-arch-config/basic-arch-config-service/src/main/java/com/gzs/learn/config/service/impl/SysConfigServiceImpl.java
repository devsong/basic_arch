package com.gzs.learn.config.service.impl;

import static com.gzs.learn.config.IConfigConstant.RESERVE_CONFIG_ITEMS;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.gzs.learn.common.util.BeanUtil;
import com.gzs.learn.config.dao.SysConfigMapper;
import com.gzs.learn.config.exception.ConfigException;
import com.gzs.learn.config.inf.SysConfigDto;
import com.gzs.learn.config.inf.SysConfigKeyDto;
import com.gzs.learn.config.po.SysConfigPo;
import com.gzs.learn.config.service.ISysConfigService;
import com.gzs.learn.config.service.ZookeeperNotifyService;

import tk.mybatis.mapper.entity.Example;

@Component
@Transactional
public class SysConfigServiceImpl implements ISysConfigService {

    @Autowired
    private ZookeeperNotifyService zookeeperNotifyService;

    @Autowired
    private SysConfigMapper sysConfigMapper;

    @Override
    public boolean addConfig(SysConfigDto configDto) {
        Example example = new Example(SysConfigPo.class);
        example.createCriteria().andEqualTo("product", configDto.getProduct()).andEqualTo("groupName", configDto.getGroupName())
                .andEqualTo("app", configDto.getApp()).andEqualTo("configKey", configDto.getConfigKey());
        example.setOrderByClause("id desc");
        RowBounds rowBounds = new RowBounds(0, RESERVE_CONFIG_ITEMS);
        List<SysConfigPo> existConfigs = sysConfigMapper.selectByExampleAndRowBounds(example, rowBounds);
        int version = 0;
        if (!CollectionUtils.isEmpty(existConfigs)) {
            // version increment
            version = existConfigs.get(0).getVersion() + 1;
        }
        configDto.setVersion(version);
        SysConfigPo po = new SysConfigPo();
        BeanUtil.copyProperties(configDto, po);
        int row = sysConfigMapper.insertSelective(po);
        if (row == 0) {
            String key = "product:" + configDto.getProduct() + "|group:" + configDto.getGroupName() + "|app:" + configDto.getApp()
                    + "|configKey:" + configDto.getConfigKey();
            throw new ConfigException("insert config error,key:" + key);
        }
        // set insert id
        configDto.setId(po.getId());
        long lastId = existConfigs.get(existConfigs.size() - 1).getId();
        if (existConfigs.size() == RESERVE_CONFIG_ITEMS) {
            // 删除旧有的数据
            Example delExample = new Example(SysConfigPo.class);
            delExample.createCriteria().andEqualTo("product", configDto.getProduct()).andEqualTo("groupName", configDto.getGroupName())
                    .andEqualTo("app", configDto.getApp()).andEqualTo("configKey", configDto.getConfigKey()).andLessThan("id", lastId);
            sysConfigMapper.deleteByExample(delExample);
        }
        zookeeperNotifyService.createNode(configDto);
        return true;
    }

    @Override
    public boolean delConfig(SysConfigKeyDto key) {
        Example example = new Example(SysConfigPo.class);
        example.createCriteria().andEqualTo("product", key.getProduct()).andEqualTo("groupName", key.getGroupName())
                .andEqualTo("app", key.getApp()).andEqualTo("configKey", key.getConfigKey());

        int row = sysConfigMapper.deleteByExample(example);
        if (row == 1) {
            SysConfigDto sysConfigDto = new SysConfigDto();
            BeanUtil.copyProperties(key, sysConfigDto);
            zookeeperNotifyService.deleteNode(sysConfigDto);
        }
        return true;
    }

    @Override
    public boolean updateConfig(SysConfigDto configDto) {
        return addConfig(configDto);
    }

    @Override
    public String getConfig(SysConfigKeyDto key) {
        String json = zookeeperNotifyService.getConfig(key);
        if (StringUtils.isNotBlank(json)) {
            SysConfigDto sysConfigDto = JSON.parseObject(json, SysConfigDto.class);
            return sysConfigDto.getConfigValue();
        }
        return null;
    }

    @Override
    public List<SysConfigDto> getConfigs(SysConfigKeyDto key) {
        Example example = new Example(SysConfigPo.class);
        example.createCriteria().andEqualTo("product", key.getProduct()).andEqualTo("groupName", key.getGroupName())
                .andEqualTo("app", key.getApp()).andEqualTo("configKey", key.getConfigKey());
        example.setOrderByClause("version desc");
        List<SysConfigPo> configPos = sysConfigMapper.selectByExample(example);
        List<SysConfigDto> configDtos = BeanUtil.copyList(configPos, SysConfigDto.class);
        return configDtos;
    }

}
