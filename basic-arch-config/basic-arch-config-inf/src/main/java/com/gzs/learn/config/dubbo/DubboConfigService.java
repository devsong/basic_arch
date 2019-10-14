package com.gzs.learn.config.dubbo;

import com.gzs.learn.config.inf.ConfigDto;

public interface DubboConfigService {
    boolean addConfig(ConfigDto configDto);

    boolean delConfig(Long id);

    boolean updateConfig(ConfigDto configDto);
}
