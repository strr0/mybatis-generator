package com.strr.code.config;

import org.mybatis.generator.config.PropertyHolder;

public class JavaMapperGeneratorConfiguration extends PropertyHolder {
    private String targetPackage;
    private String targetProject;

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public String getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(String targetProject) {
        this.targetProject = targetProject;
    }
}
