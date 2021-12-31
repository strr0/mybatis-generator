package com.strr.code.config;

public class JavaControllerGeneratorConfiguration {
    private String targetPackage;
    private String targetProject;
    private Boolean needResult;
    private String targetResult;

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

    public Boolean getNeedResult() {
        return needResult;
    }

    public void setNeedResult(Boolean needResult) {
        this.needResult = needResult;
    }

    public String getTargetResult() {
        return targetResult;
    }

    public void setTargetResult(String targetResult) {
        this.targetResult = targetResult;
    }
}
