package com.strr.code.config;

import org.mybatis.generator.config.*;

public class CustomContext extends Context {
    private JavaMapperGeneratorConfiguration javaMapperGeneratorConfiguration;
    private JavaServiceGeneratorConfiguration javaServiceGeneratorConfiguration;
    private JavaControllerGeneratorConfiguration javaControllerGeneratorConfiguration;

    public CustomContext(ModelType defaultModelType) {
        super(defaultModelType);
        super.setId("simple");
        super.setTargetRuntime("com.strr.code.config.CustomIntrospectedTable");
    }

    public JavaMapperGeneratorConfiguration getJavaMapperGeneratorConfiguration() {
        return javaMapperGeneratorConfiguration;
    }

    public void setJavaMapperGeneratorConfiguration(JavaMapperGeneratorConfiguration javaMapperGeneratorConfiguration) {
        this.javaMapperGeneratorConfiguration = javaMapperGeneratorConfiguration;
    }

    public JavaServiceGeneratorConfiguration getJavaServiceGeneratorConfiguration() {
        return javaServiceGeneratorConfiguration;
    }

    public void setJavaServiceGeneratorConfiguration(JavaServiceGeneratorConfiguration javaServiceGeneratorConfiguration) {
        this.javaServiceGeneratorConfiguration = javaServiceGeneratorConfiguration;
    }

    public JavaControllerGeneratorConfiguration getJavaControllerGeneratorConfiguration() {
        return javaControllerGeneratorConfiguration;
    }

    public void setJavaControllerGeneratorConfiguration(JavaControllerGeneratorConfiguration javaControllerGeneratorConfiguration) {
        this.javaControllerGeneratorConfiguration = javaControllerGeneratorConfiguration;
    }

    // 添加表
    public void addTable(String tableName) {
        TableConfiguration table = new TableConfiguration(this);
        table.setTableName(tableName);
        super.addTableConfiguration(table);
    }
}
