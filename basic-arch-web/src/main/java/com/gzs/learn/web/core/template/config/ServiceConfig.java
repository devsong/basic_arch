package com.gzs.learn.web.core.template.config;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Service模板生成的配置
 *
 * @author fengshuonan
 * @date 2017-05-07 22:12
 */
public class ServiceConfig {
    private static final String BASE_PACKAGE = "com.gzs.learn.web.modular.";
    private static final String BASE_PACKAGE_DIR = "\\src\\main\\java\\com\\gzs\\learn\\web\\modular\\";
    private ContextConfig contextConfig;

    private String servicePathTemplate;
    private String serviceImplPathTemplate;

    private String packageName;

    private List<String> serviceImplImports;

    public void init() {
        List<String> imports = Lists.newArrayList();
        imports.add("org.springframework.stereotype.Service");
        imports.add(BASE_PACKAGE + contextConfig.getModuleName() + ".service.I" + contextConfig.getBizEnBigName() + "Service");
        this.serviceImplImports = imports;
        this.servicePathTemplate = BASE_PACKAGE_DIR + contextConfig.getModuleName() + "\\service\\I{}Service.java";
        this.serviceImplPathTemplate = BASE_PACKAGE_DIR + contextConfig.getModuleName() + "\\service\\impl\\{}ServiceImpl.java";
        this.packageName = BASE_PACKAGE + contextConfig.getModuleName() + ".service";
    }

    public String getServicePathTemplate() {
        return servicePathTemplate;
    }

    public void setServicePathTemplate(String servicePathTemplate) {
        this.servicePathTemplate = servicePathTemplate;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getServiceImplPathTemplate() {
        return serviceImplPathTemplate;
    }

    public void setServiceImplPathTemplate(String serviceImplPathTemplate) {
        this.serviceImplPathTemplate = serviceImplPathTemplate;
    }

    public List<String> getServiceImplImports() {
        return serviceImplImports;
    }

    public void setServiceImplImports(List<String> serviceImplImports) {
        this.serviceImplImports = serviceImplImports;
    }

    public ContextConfig getContextConfig() {
        return contextConfig;
    }

    public void setContextConfig(ContextConfig contextConfig) {
        this.contextConfig = contextConfig;
    }
}
