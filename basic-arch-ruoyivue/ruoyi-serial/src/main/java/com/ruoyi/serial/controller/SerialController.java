package com.ruoyi.serial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gzs.learn.inf.PageResponseDto;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.serial.common.Result;
import com.ruoyi.serial.common.Status;
import com.ruoyi.serial.domain.SerialAlloc;
import com.ruoyi.serial.domain.SerialSnowflakeInfo;
import com.ruoyi.serial.dto.SegmentSearchDto;
import com.ruoyi.serial.exception.SerialException;
import com.ruoyi.serial.exception.NoKeyException;
import com.ruoyi.serial.segment.SegmentIDGenImpl;
import com.ruoyi.serial.snowflake.SnowflakeIDGenImpl;

@RestController
@RequestMapping("api/serial")
public class SerialController {
    @Autowired
    private SegmentIDGenImpl segmentService;

    @Autowired
    private SnowflakeIDGenImpl snowflakeService;

    @RequestMapping(value = "/segment")
    public AjaxResult getSegmentId(String key) {
        return AjaxResult.success(get(key, segmentService.get(key)));
    }

    @RequestMapping(value = "segment/list")
    public PageResponseDto<SerialAlloc> getSegment(@RequestBody SegmentSearchDto segmentSearchDto) {
        PageResponseDto<SerialAlloc> pageResponseDto = segmentService.searchBizKeys(segmentSearchDto);
        return pageResponseDto;
    }

    @RequestMapping(value = "/snowflake")
    public AjaxResult getSnowflakeId(String key) {
        return AjaxResult.success(get(key, snowflakeService.get(key)));
    }

    @RequestMapping(value = "/snowflake/decode")
    public AjaxResult decode(Long id) {
        SerialSnowflakeInfo decodeSnowflake = snowflakeService.decodeSnowflake(id);
        return AjaxResult.success(decodeSnowflake);
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
