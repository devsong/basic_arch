package com.ruoyi.web.controller.serial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gzs.learn.inf.PageResponseDto;
import com.gzs.learn.serial.dubbo.DubboSerialManagerService;
import com.gzs.learn.serial.inf.SerialGroup;
import com.gzs.learn.serial.inf.SerialGroupSearch;

@RestController
@RequestMapping("serial")
public class SerialController {

    @Autowired
    private DubboSerialManagerService dubboSerialManagerService;

    @RequestMapping("list")
    public PageResponseDto<SerialGroup> list(@RequestBody SerialGroupSearch search) {
        PageResponseDto<SerialGroup> groups = dubboSerialManagerService.getSerialGroup(search);
        return groups;
    }
}
