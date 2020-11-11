package com.ruoyi.quartz.task;

import org.springframework.stereotype.Component;
import com.ruoyi.common.utils.StringUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 定时任务调度测试
 * 
 * @author guanzhisong
 */
@Component("ryTask")
@Slf4j
public class RyTask {
    public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
        log.info(StringUtils.format("执行多参方法： 字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i));
    }

    public void ryParams(String params) {
        log.info("执行有参方法：" + params);
    }

    public void ryNoParams() {
        log.info("执行无参方法");
    }
}
