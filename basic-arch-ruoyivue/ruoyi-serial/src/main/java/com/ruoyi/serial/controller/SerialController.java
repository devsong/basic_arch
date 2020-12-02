package com.ruoyi.serial.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gzs.learn.inf.PageResponseDto;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.serial.domain.SerialAlloc;
import com.ruoyi.serial.dto.SegmentSearchDto;
import com.ruoyi.serial.exception.SerialException;
import com.ruoyi.serial.segment.ISerialAllocService;

@RestController
@RequestMapping("/serial")
public class SerialController {
    @Autowired
    private ISerialAllocService serialAllocService;

    @RequestMapping(value = "/segment/list")
    @PreAuthorize("@ss.hasPermi('serial:segment:list')")
    public PageResponseDto<SerialAlloc> getSegmentList(SegmentSearchDto segmentSearchDto) {
        PageResponseDto<SerialAlloc> pageResponseDto = serialAllocService.searchBizKeys(segmentSearchDto);
        return pageResponseDto;
    }

    @RequestMapping(value = "/segment/get")
    @PreAuthorize("@ss.hasPermi('serial:segment:list')")
    public AjaxResult getSegmen(String bizKey) {
        SerialAlloc serialAlloc = serialAllocService.getBizKey(bizKey);
        return AjaxResult.success(serialAlloc);
    }

    @RequestMapping(value = "/segment/add")
    @PreAuthorize("@ss.hasPermi('serial:segment:update')")
    public AjaxResult addSegment(SerialAlloc serialAlloc) {
        boolean success = serialAllocService.add(serialAlloc);
        return AjaxResult.success(success);
    }

    @RequestMapping(value = "/segment/update")
    @PreAuthorize("@ss.hasPermi('serial:segment:update')")
    public AjaxResult updateSegment(SerialAlloc serialAlloc) {
        boolean success = serialAllocService.update(serialAlloc);
        return AjaxResult.success(success);
    }

    @RequestMapping(value = "/segment/update_status")
    @PreAuthorize("@ss.hasPermi('serial:segment:update')")
    public AjaxResult updateStatus(String key, Integer status) {
        if (status == null || StringUtils.isBlank(key)) {
            throw new SerialException(String.format("params %s,%s is not illegal", key, status));
        }
        boolean success = serialAllocService.updateStatus(key, status);
        return AjaxResult.success(success);
    }
}
