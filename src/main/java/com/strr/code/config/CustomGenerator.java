package com.strr.code.config;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CustomGenerator {
    private List<String> warnings;
    private boolean overwrite;
    private Configuration config;

    // 初始化
    public void initialize() {
        warnings = new ArrayList<String>();
        overwrite = true;
        config = new Configuration();
        Yaml yaml = new Yaml();
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream("generator.yml")) {
            CustomConfiguration configuration = yaml.loadAs(in, CustomConfiguration.class);
            loadConfiguration(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 加载配置
    private void loadConfiguration(CustomConfiguration configuration) {
        CustomContext context = new CustomContext(null);
        context.setJdbcConnectionConfiguration(loadJDBCConnectionConfiguration(configuration));
        context.setJavaModelGeneratorConfiguration(loadJavaModelGeneratorConfiguration(configuration));
        context.setJavaMapperGeneratorConfiguration(loadJavaMapperGeneratorConfiguration(configuration));
        context.setJavaServiceGeneratorConfiguration(loadJavaServiceGeneratorConfiguration(configuration));
        context.setJavaControllerGeneratorConfiguration(loadJavaControllerGeneratorConfiguration(configuration));
        context.setCommentGeneratorConfiguration(loadCommentGeneratorConfiguration());
        for (String table : configuration.getTables()) {
            context.addTable(table);
        }
        config.addContext(context);
    }

    // 加载连接配置
    private JDBCConnectionConfiguration loadJDBCConnectionConfiguration(CustomConfiguration configuration) {
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(configuration.getUrl());
        jdbcConnectionConfiguration.setDriverClass(configuration.getDriver());
        jdbcConnectionConfiguration.setUserId(configuration.getUser());
        jdbcConnectionConfiguration.setPassword(configuration.getPassword());
        return jdbcConnectionConfiguration;
    }

    // 加载model配置
    private JavaModelGeneratorConfiguration loadJavaModelGeneratorConfiguration(CustomConfiguration configuration) {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetPackage(configuration.getModelPackage());
        javaModelGeneratorConfiguration.setTargetProject(configuration.getModelProject());
        return javaModelGeneratorConfiguration;
    }

    // 加载mapper配置
    private JavaMapperGeneratorConfiguration loadJavaMapperGeneratorConfiguration(CustomConfiguration configuration) {
        JavaMapperGeneratorConfiguration javaMapperGeneratorConfiguration = new JavaMapperGeneratorConfiguration();
        javaMapperGeneratorConfiguration.setTargetPackage(configuration.getMapperPackage());
        javaMapperGeneratorConfiguration.setTargetProject(configuration.getMapperProject());
        return javaMapperGeneratorConfiguration;
    }

    // 加载service配置
    private JavaServiceGeneratorConfiguration loadJavaServiceGeneratorConfiguration(CustomConfiguration configuration) {
        JavaServiceGeneratorConfiguration javaServiceGeneratorConfiguration = new JavaServiceGeneratorConfiguration();
        javaServiceGeneratorConfiguration.setTargetPackage(configuration.getServicePackage());
        javaServiceGeneratorConfiguration.setTargetProject(configuration.getServiceProject());
        return javaServiceGeneratorConfiguration;
    }

    // 加载controller配置
    private JavaControllerGeneratorConfiguration loadJavaControllerGeneratorConfiguration(CustomConfiguration configuration) {
        JavaControllerGeneratorConfiguration javaControllerGeneratorConfiguration = new JavaControllerGeneratorConfiguration();
        javaControllerGeneratorConfiguration.setTargetPackage(configuration.getControllerPackage());
        javaControllerGeneratorConfiguration.setTargetProject(configuration.getControllerProject());
        javaControllerGeneratorConfiguration.setNeedResult(configuration.getNeedResult());
        javaControllerGeneratorConfiguration.setTargetResult(configuration.getControllerResult());
        return javaControllerGeneratorConfiguration;
    }

    // 加载注释配置
    private CommentGeneratorConfiguration loadCommentGeneratorConfiguration() {
        CommentGeneratorConfiguration commentGeneratorConfiguration = new CommentGeneratorConfiguration();
        commentGeneratorConfiguration.setConfigurationType("com.strr.code.config.CustomCommentGenerator");
        return commentGeneratorConfiguration;
    }

    public void generate() {
        try {
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        warnings.forEach(System.err::println);
    }
}
