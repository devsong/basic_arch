package com.gzs.learn.config.dubbo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gzs.learn.config.inf.SysConfigDto;
import com.gzs.learn.config.inf.SysConfigKeyDto;
import com.gzs.learn.config.service.ISysConfigService;

@Component("dubboConfigService")
public class DubboConfigServiceImpl implements DubboConfigService {
    @Autowired
    private ISysConfigService sysConfigService;

    @Override
    public boolean addConfig(SysConfigDto configDto) {
        return sysConfigService.addConfig(configDto);
    }

    @Override
    public boolean delConfig(SysConfigKeyDto key) {
        return sysConfigService.delConfig(key);
    }

    @Override
    public boolean updateConfig(SysConfigDto configDto) {
        return sysConfigService.updateConfig(configDto);
    }

    @Override
    public String getConfig(SysConfigKeyDto key) {
        return sysConfigService.getConfig(key);
    }

    @Override
    public List<SysConfigDto> getConfigs(SysConfigKeyDto key) {
        return sysConfigService.getConfigs(key);
    }
}
