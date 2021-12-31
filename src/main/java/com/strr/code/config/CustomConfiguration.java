package com.strr.code.config;

/**
 * 代码生成器配置
 */
public class CustomConfiguration {
    private String url;
    private String driver;
    private String user;
    private String password;

    private String modelPackage;
    private String modelProject;

    private String mapperPackage;
    private String mapperProject;

    private String servicePackage;
    private String serviceProject;

    private String controllerPackage;
    private String controllerProject;
    private Boolean needResult;
    private String controllerResult;

    private String[] tables;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getModelPackage() {
        return modelPackage;
    }

    public void setModelPackage(String modelPackage) {
        this.modelPackage = modelPackage;
    }

    public String getModelProject() {
        return modelProject;
    }

    public void setModelProject(String modelProject) {
        this.modelProject = modelProject;
    }

    public String getMapperPackage() {
        return mapperPackage;
    }

    public void setMapperPackage(String mapperPackage) {
        this.mapperPackage = mapperPackage;
    }

    public String getMapperProject() {
        return mapperProject;
    }

    public void setMapperProject(String mapperProject) {
        this.mapperProject = mapperProject;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getServiceProject() {
        return serviceProject;
    }

    public void setServiceProject(String serviceProject) {
        this.serviceProject = serviceProject;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getControllerProject() {
        return controllerProject;
    }

    public void setControllerProject(String controllerProject) {
        this.controllerProject = controllerProject;
    }

    public Boolean getNeedResult() {
        return needResult;
    }

    public void setNeedResult(Boolean needResult) {
        this.needResult = needResult;
    }

    public String getControllerResult() {
        return controllerResult;
    }

    public void setControllerResult(String controllerResult) {
        this.controllerResult = controllerResult;
    }

    public String[] getTables() {
        return tables;
    }

    public void setTables(String[] tables) {
        this.tables = tables;
    }
}
