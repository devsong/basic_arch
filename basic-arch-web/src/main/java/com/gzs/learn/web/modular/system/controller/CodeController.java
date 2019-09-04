package com.gzs.learn.web.modular.system.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzs.learn.web.common.annotion.Permission;
import com.gzs.learn.web.common.constant.Const;
import com.gzs.learn.web.common.controller.BaseController;
import com.gzs.learn.web.common.exception.BizExceptionEnum;
import com.gzs.learn.web.common.exception.BussinessException;
import com.gzs.learn.web.core.template.config.ContextConfig;
import com.gzs.learn.web.core.template.engine.SimpleTemplateEngine;
import com.gzs.learn.web.core.template.engine.base.GunsTemplateEngine;
import com.gzs.learn.web.core.util.ToolUtil;

@Controller
@RequestMapping("/code")
public class CodeController extends BaseController {

    private String PREFIX = "/system/code/";

    /**
     * 跳转到代码生成首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "code.html";
    }

    /**
     * 代码生成
     */
    @ApiOperation("生成代码")
    @RequestMapping(value = "/generate", method = RequestMethod.POST)
    @ResponseBody
    @Permission(Const.ADMIN_NAME)
    public Object add(@ApiParam(value = "模块名称", required = true) @RequestParam String moduleName, @RequestParam String bizChName,
            @RequestParam String bizEnName, @RequestParam String path) {
        if (ToolUtil.isOneEmpty(bizChName, bizEnName)) {
            throw new BussinessException(BizExceptionEnum.REQUEST_NULL);
        }
        ContextConfig contextConfig = new ContextConfig();
        contextConfig.setBizChName(bizChName);
        contextConfig.setBizEnName(bizEnName);
        contextConfig.setModuleName(moduleName);
        if (ToolUtil.isNotEmpty(path)) {
            contextConfig.setProjectPath(path);
        }

        GunsTemplateEngine gunsTemplateEngine = new SimpleTemplateEngine();
        gunsTemplateEngine.setContextConfig(contextConfig);
        gunsTemplateEngine.start();

        return SUCCESS_TIP;
    }
}
