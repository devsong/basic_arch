package com.gzs.learn.web.core.template.engine;

import com.gzs.learn.web.core.template.engine.base.GunsTemplateEngine;
import com.gzs.learn.web.core.util.ToolUtil;

/**
 * 通用的模板生成引擎
 */
public class SimpleTemplateEngine extends GunsTemplateEngine {

    @Override
    protected void generatePageEditHtml() {
        String path = ToolUtil.format(super.getContextConfig().getProjectPath() + getPageConfig().getPageEditPathTemplate(),
                super.getContextConfig().getBizEnName(), super.getContextConfig().getBizEnName());
        generateFile("gunsTemplate/page_edit.html.btl", path);
    }

    @Override
    protected void generatePageAddHtml() {
        String path = ToolUtil.format(super.getContextConfig().getProjectPath() + getPageConfig().getPageAddPathTemplate(),
                super.getContextConfig().getBizEnName(), super.getContextConfig().getBizEnName());
        generateFile("gunsTemplate/page_add.html.btl", path);
    }

    @Override
    protected void generatePageInfoJs() {
        String path = ToolUtil.format(super.getContextConfig().getProjectPath() + getPageConfig().getPageInfoJsPathTemplate(),
                super.getContextConfig().getBizEnName(), super.getContextConfig().getBizEnName());
        generateFile("gunsTemplate/page_info.js.btl", path);
    }

    @Override
    protected void generatePageJs() {
        String path = ToolUtil.format(super.getContextConfig().getProjectPath() + getPageConfig().getPageJsPathTemplate(),
                super.getContextConfig().getBizEnName(), super.getContextConfig().getBizEnName());
        generateFile("gunsTemplate/page.js.btl", path);
    }

    @Override
    protected void generatePageHtml() {
        String path = ToolUtil.format(super.getContextConfig().getProjectPath() + getPageConfig().getPagePathTemplate(),
                super.getContextConfig().getBizEnName(), super.getContextConfig().getBizEnName());
        generateFile("gunsTemplate/page.html.btl", path);
    }

    @Override
    protected void generateController() {
        String controllerPath = ToolUtil.format(
                super.getContextConfig().getProjectPath() + super.getControllerConfig().getControllerPathTemplate(),
                ToolUtil.firstLetterToUpper(super.getContextConfig().getBizEnName()));
        generateFile("gunsTemplate/Controller.java.btl", controllerPath);
    }

    @Override
    protected void generateDao() {
        String daoPath = ToolUtil.format(super.getContextConfig().getProjectPath() + super.getDaoConfig().getDaoPathTemplate(),
                ToolUtil.firstLetterToUpper(super.getContextConfig().getBizEnName()));
        generateFile("gunsTemplate/Dao.java.btl", daoPath);

        String mappingPath = ToolUtil.format(super.getContextConfig().getProjectPath() + super.getDaoConfig().getXmlPathTemplate(),
                ToolUtil.firstLetterToUpper(super.getContextConfig().getBizEnName()));
        generateFile("gunsTemplate/Mapping.xml.btl", mappingPath);
    }

    @Override
    protected void generateService() {
        String servicePath = ToolUtil.format(super.getContextConfig().getProjectPath() + super.getServiceConfig().getServicePathTemplate(),
                ToolUtil.firstLetterToUpper(super.getContextConfig().getBizEnName()));
        generateFile("gunsTemplate/Service.java.btl", servicePath);

        String serviceImplPath = ToolUtil.format(
                super.getContextConfig().getProjectPath() + super.getServiceConfig().getServiceImplPathTemplate(),
                ToolUtil.firstLetterToUpper(super.getContextConfig().getBizEnName()));
        generateFile("gunsTemplate/ServiceImpl.java.btl", serviceImplPath);
    }
}
