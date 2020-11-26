package com.ruoyi.serial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.serial.common.Result;
import com.ruoyi.serial.common.Status;
import com.ruoyi.serial.domain.SerialSnowflakeInfo;
import com.ruoyi.serial.exception.SerialException;
import com.ruoyi.serial.exception.NoKeyException;
import com.ruoyi.serial.segment.SegmentIDGenImpl;
import com.ruoyi.serial.snowflake.SnowflakeIDGenImpl;

@RestController
@RequestMapping("api/serial")
public class SerialController {
    // private Logger log = LoggerFactory.getLogger(LeafController.class);

    @Autowired
    private SegmentIDGenImpl segmentService;

    @Autowired
    private SnowflakeIDGenImpl snowflakeService;

    @RequestMapping(value = "/segment/get/{key}")
    public String getSegmentId(@PathVariable("key") String key) {
        return get(key, segmentService.get(key));
    }

    @RequestMapping(value = "/snowflake/get/{key}")
    public String getSnowflakeId(@PathVariable("key") String key) {
        return get(key, snowflakeService.get(key));
    }

    @RequestMapping("/snowflake/decode/{id}")
    public SerialSnowflakeInfo decode(@PathVariable Long id) {
        return snowflakeService.decodeSnowflake(id);
    }

    private String get(@PathVariable("key") String key, Result id) {
        Result result;
        if (key == null || key.isEmpty()) {
            throw new NoKeyException();
        }
        result = id;
        if (result.getStatus().equals(Status.EXCEPTION)) {
            throw new SerialException(result.toString());
        }
        return String.valueOf(result.getId());
    }
}
