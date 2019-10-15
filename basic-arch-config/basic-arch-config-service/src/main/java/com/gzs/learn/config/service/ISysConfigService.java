package com.gzs.learn.config.service;

import java.util.List;

import com.gzs.learn.config.inf.SysConfigDto;
import com.gzs.learn.config.inf.SysConfigKeyDto;

public interface ISysConfigService {

    boolean addConfig(SysConfigDto configDto);

    boolean delConfig(SysConfigKeyDto key);

    boolean updateConfig(SysConfigDto configDto);

    String getConfig(SysConfigKeyDto key);

    List<SysConfigDto> getConfigs(SysConfigKeyDto key);

}
