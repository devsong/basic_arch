package com.gzs.learn.config.service;

import com.gzs.learn.config.inf.SysConfigDto;
import com.gzs.learn.config.inf.SysConfigKeyDto;

public interface ZookeeperNotifyService {

    public boolean createNode(SysConfigDto sysConfigDto);

    public boolean deleteNode(SysConfigDto sysConfigDto);

    public String getConfig(SysConfigKeyDto key);
}
