package com.ruoyi.serial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gzs.learn.common.util.Base32;
import com.gzs.learn.common.util.Base62;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.serial.common.Result;
import com.ruoyi.serial.common.Status;
import com.ruoyi.serial.domain.SerialSnowflakeInfo;
import com.ruoyi.serial.exception.NoKeyException;
import com.ruoyi.serial.exception.SerialException;
import com.ruoyi.serial.segment.SegmentIdGenService;
import com.ruoyi.serial.snowflake.SnowflakeIDGenImpl;

@RestController
@RequestMapping("/api/serial")
public class SerialApiController {
    @Autowired
    private SegmentIdGenService segmentIdGenService;

    @Autowired
    private SnowflakeIDGenImpl snowflakeService;

    @RequestMapping(value = "/segment")
    public AjaxResult getSegmentId(String key) {
        return AjaxResult.success("success", get(key, segmentIdGenService.get(key)));
    }

    @RequestMapping(value = "/snowflake")
    public AjaxResult getSnowflakeId(String key) {
        return AjaxResult.success("success", get(key, snowflakeService.get(key)));
    }

    @RequestMapping(value = "/snowflake/decode")
    public AjaxResult decode(Long id) {
        SerialSnowflakeInfo decodeSnowflake = snowflakeService.decodeSnowflake(id);
        return AjaxResult.success(decodeSnowflake);
    }

    @RequestMapping(value = "/base32/decode")
    public AjaxResult decodeBase32(String id) {
        long result = Base32.decodeBase32ToLong(id);
        return AjaxResult.success(result);
    }

    @RequestMapping(value = "/base32/encode")
    public AjaxResult encodeBase32(Long id) {
        String result = Base32.encodeLongToBase32(id);
        return AjaxResult.success(result);
    }

    @RequestMapping(value = "/base62/decode")
    public AjaxResult decodeBase64(String id) {
        long result = Base62.toBase10(id);
        return AjaxResult.success(result);
    }

    @RequestMapping(value = "/base62/encode")
    public AjaxResult encodeBase64(Long id) {
        String result = Base62.fromBase10(id);
        return AjaxResult.success(result);
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
