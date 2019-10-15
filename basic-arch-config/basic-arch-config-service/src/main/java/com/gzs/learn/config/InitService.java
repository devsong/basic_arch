package com.gzs.learn.config;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitService {

    @PostConstruct
    public void init() {
        log.info("init execute...");
    }
}
