package com.gzs.learn.serial.service.imp;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gzs.learn.serial.service.SerialManagerService;

@Service
public class InitService {
    @Autowired
    private SerialManagerService serialManagerService;

    @PostConstruct
    public void init() {
        this.serialManagerService.init();
    }
}
