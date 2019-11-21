package com.gzs.learn.web.core.template.config;

import static com.gzs.learn.web.core.template.TemplateConstant.BASE_PACKAGE;
import static com.gzs.learn.web.core.template.TemplateConstant.BASE_PACKAGE_DIR;

/**
 * Dao模板生成的配置
 */
public class DaoConfig {

    private ContextConfig contextConfig;

    private String daoPathTemplate;
    private String xmlPathTemplate;

    private String packageName;

    public void init() {
        this.daoPathTemplate = BASE_PACKAGE_DIR + "\\modular\\" + contextConfig.getModuleName() + "\\dao\\{}Dao.java";
        this.xmlPathTemplate = BASE_PACKAGE_DIR + "\\modular\\" + contextConfig.getModuleName() + "\\dao\\mapping\\{}Dao.xml";
        this.packageName = BASE_PACKAGE + ".web.modular." + contextConfig.getModuleName() + ".dao";
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getDaoPathTemplate() {
        return daoPathTemplate;
    }

    public void setDaoPathTemplate(String daoPathTemplate) {
        this.daoPathTemplate = daoPathTemplate;
    }

    public String getXmlPathTemplate() {
        return xmlPathTemplate;
    }

    public void setXmlPathTemplate(String xmlPathTemplate) {
        this.xmlPathTemplate = xmlPathTemplate;
    }

    public ContextConfig getContextConfig() {
        return contextConfig;
    }

    public void setContextConfig(ContextConfig contextConfig) {
        this.contextConfig = contextConfig;
    }
}
