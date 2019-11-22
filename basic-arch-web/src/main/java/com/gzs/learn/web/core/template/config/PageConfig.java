package com.gzs.learn.web.core.template.config;

/**
 * 页面 模板生成的配置
 */
public class PageConfig {
    private static final String VIEW_BASE_DIR = "src/main/webapp/WEB-INF/view/";
    private static final String STATIC_BASE_DIR = "src/main/webapp/static/modular/";
    private ContextConfig contextConfig;

    private String pagePathTemplate;
    private String pageAddPathTemplate;
    private String pageEditPathTemplate;
    private String pageJsPathTemplate;
    private String pageInfoJsPathTemplate;

    public void init() {
        pagePathTemplate = VIEW_BASE_DIR + contextConfig.getModuleName() + "/{}/{}.html";
        pageAddPathTemplate = VIEW_BASE_DIR + contextConfig.getModuleName() + "/{}/{}_add.html";
        pageEditPathTemplate = VIEW_BASE_DIR + contextConfig.getModuleName() + "/{}/{}_edit.html";
        pageJsPathTemplate = STATIC_BASE_DIR + contextConfig.getModuleName() + "/{}/{}.js";
        pageInfoJsPathTemplate = STATIC_BASE_DIR + contextConfig.getModuleName() + "/{}/{}_info.js";
    }

    public String getPagePathTemplate() {
        return pagePathTemplate;
    }

    public void setPagePathTemplate(String pagePathTemplate) {
        this.pagePathTemplate = pagePathTemplate;
    }

    public String getPageJsPathTemplate() {
        return pageJsPathTemplate;
    }

    public void setPageJsPathTemplate(String pageJsPathTemplate) {
        this.pageJsPathTemplate = pageJsPathTemplate;
    }

    public String getPageAddPathTemplate() {
        return pageAddPathTemplate;
    }

    public void setPageAddPathTemplate(String pageAddPathTemplate) {
        this.pageAddPathTemplate = pageAddPathTemplate;
    }

    public String getPageEditPathTemplate() {
        return pageEditPathTemplate;
    }

    public void setPageEditPathTemplate(String pageEditPathTemplate) {
        this.pageEditPathTemplate = pageEditPathTemplate;
    }

    public String getPageInfoJsPathTemplate() {
        return pageInfoJsPathTemplate;
    }

    public void setPageInfoJsPathTemplate(String pageInfoJsPathTemplate) {
        this.pageInfoJsPathTemplate = pageInfoJsPathTemplate;
    }

    public ContextConfig getContextConfig() {
        return contextConfig;
    }

    public void setContextConfig(ContextConfig contextConfig) {
        this.contextConfig = contextConfig;
    }
}
