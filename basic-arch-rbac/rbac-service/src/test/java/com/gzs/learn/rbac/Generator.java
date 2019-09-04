package com.gzs.learn.rbac;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class Generator {
    public static void main(String[] args) throws Exception {
        String[] generatorConfigFiles = { "/generator.xml" };

        for (String str : generatorConfigFiles) {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            InputStream inputStream = Generator.class.getResourceAsStream(str);
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(inputStream);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        }
    }
}
