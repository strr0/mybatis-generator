package com.strr.code.config;

import com.strr.code.controller.JavaControllerGenerator;
import com.strr.code.mapper.JavaMapperGenerator;
import com.strr.code.model.JavaVOGenerator;
import com.strr.code.service.JavaServiceGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.codegen.mybatis3.IntrospectedTableMyBatis3Impl;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.config.ModelType;
import org.mybatis.generator.internal.rules.ConditionalModelRules;
import org.mybatis.generator.internal.rules.FlatModelRules;
import org.mybatis.generator.internal.rules.HierarchicalModelRules;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.List;

public class CustomIntrospectedTable extends IntrospectedTableMyBatis3Impl {
    /**
     * 小写名称
     */
    private String basicName;

    /**
     * vo名称
     */
    private String myBatis3JavaVOType;

    /**
     * service名称
     */
    private String myBatis3JavaServiceType;

    /**
     * serviceImpl名称
     */
    private String myBatis3JavaServiceImplType;

    /**
     * controller名称
     */
    private String myBatis3JavaControllerType;

    public String getBasicName() {
        return basicName;
    }

    public void setBasicName(String basicName) {
        this.basicName = basicName;
    }

    public String getMyBatis3JavaVOType() {
        return myBatis3JavaVOType;
    }

    public void setMyBatis3JavaVOType(String myBatis3JavaVOType) {
        this.myBatis3JavaVOType = myBatis3JavaVOType;
    }

    public String getMyBatis3JavaServiceType() {
        return myBatis3JavaServiceType;
    }

    public void setMyBatis3JavaServiceType(String myBatis3JavaServiceType) {
        this.myBatis3JavaServiceType = myBatis3JavaServiceType;
    }

    public String getMyBatis3JavaServiceImplType() {
        return myBatis3JavaServiceImplType;
    }

    public void setMyBatis3JavaServiceImplType(String myBatis3JavaServiceImplType) {
        this.myBatis3JavaServiceImplType = myBatis3JavaServiceImplType;
    }

    public String getMyBatis3JavaControllerType() {
        return myBatis3JavaControllerType;
    }

    public void setMyBatis3JavaControllerType(String myBatis3JavaControllerType) {
        this.myBatis3JavaControllerType = myBatis3JavaControllerType;
    }

    public String getMapperProject() {
        return ((CustomContext)this.context).getJavaMapperGeneratorConfiguration().getTargetProject();
    }

    public String getServiceProject() {
        return ((CustomContext)this.context).getJavaServiceGeneratorConfiguration().getTargetProject();
    }

    public String getControllerProject() {
        return ((CustomContext)this.context).getJavaControllerGeneratorConfiguration().getTargetProject();
    }

    public String getControllerResult() {
        return ((CustomContext)this.context).getJavaControllerGeneratorConfiguration().getTargetResult();
    }

    // 是否创建Result
    public boolean needResult() {
        return ((CustomContext)this.context).getJavaControllerGeneratorConfiguration().getNeedResult();
    }

    // 初始化
    @Override
    public void initialize() {
        this.calculateModelAttributes();
        super.calculateXmlAttributes();
        this.calculateJavaMapperAttributes();
        this.calculateServiceAttributes();
        this.calculateControllerAttributes();
        if (this.tableConfiguration.getModelType() == ModelType.HIERARCHICAL) {
            this.rules = new HierarchicalModelRules(this);
        } else if (this.tableConfiguration.getModelType() == ModelType.FLAT) {
            this.rules = new FlatModelRules(this);
        } else {
            this.rules = new ConditionalModelRules(this);
        }
        this.context.getPlugins().initialized(this);
    }

    // 设置model属性
    @Override
    protected void calculateModelAttributes() {
        String domainObjectName = this.fullyQualifiedTable.getDomainObjectName();
        this.setBasicName(domainObjectName.substring(0, 1).toLowerCase() + domainObjectName.substring(1));
        String pakkage = this.calculateJavaModelPackage();
        StringBuilder sb = new StringBuilder();
        sb.append(pakkage);
        sb.append('.');
        sb.append(this.fullyQualifiedTable.getDomainObjectName());
        sb.append("Key");
        this.setPrimaryKeyType(sb.toString());
        sb.setLength(0);
        sb.append(pakkage);
        sb.append('.');
        sb.append(this.fullyQualifiedTable.getDomainObjectName());
        this.setBaseRecordType(sb.toString());
        sb.setLength(0);
        sb.append(pakkage);
        sb.append('.');
        sb.append(this.fullyQualifiedTable.getDomainObjectName());
        sb.append("Record");
        this.setKotlinRecordType(sb.toString());
        sb.setLength(0);
        sb.append(pakkage);
        sb.append('.');
        sb.append(this.fullyQualifiedTable.getDomainObjectName());
        sb.append("WithBLOBs");
        this.setRecordWithBLOBsType(sb.toString());
        sb.setLength(0);
        sb.append(pakkage);
        sb.append('.');
        sb.append(this.fullyQualifiedTable.getDomainObjectName());
        sb.append("VO");
        this.setMyBatis3JavaVOType(sb.toString());
    }

    // 设置mapper属性
    protected void calculateJavaMapperAttributes() {
        JavaMapperGeneratorConfiguration config = ((CustomContext)this.context).getJavaMapperGeneratorConfiguration();
        if (config != null) {
            // mapper
            StringBuilder sb = new StringBuilder();
            sb.append(this.calculateJavaMapperInterfacePackage());
            sb.append('.');
            if (StringUtility.stringHasValue(this.fullyQualifiedTable.getDomainObjectSubPackage())) {
                sb.append(this.fullyQualifiedTable.getDomainObjectSubPackage());
                sb.append('.');
            }
            sb.append(this.fullyQualifiedTable.getDomainObjectName());
            sb.append("Mapper");
            this.setMyBatis3JavaMapperType(sb.toString());
            // sqlProvider
            sb.setLength(0);
            sb.append(this.calculateJavaMapperInterfacePackage());
            sb.append('.');
            if (StringUtility.stringHasValue(this.fullyQualifiedTable.getDomainObjectSubPackage())) {
                sb.append(this.fullyQualifiedTable.getDomainObjectSubPackage());
                sb.append('.');
            }
            sb.append(this.fullyQualifiedTable.getDomainObjectName());
            sb.append("SqlProvider");
            this.setMyBatis3SqlProviderType(sb.toString());
        }
    }

    // 设置service属性
    protected void calculateServiceAttributes() {
        JavaServiceGeneratorConfiguration config = ((CustomContext)this.context).getJavaServiceGeneratorConfiguration();
        if (config != null) {
            // service
            StringBuilder sb = new StringBuilder();
            sb.append(this.calculateJavaServiceInterfacePackage());
            sb.append('.');
            if (StringUtility.stringHasValue(this.fullyQualifiedTable.getDomainObjectSubPackage())) {
                sb.append(this.fullyQualifiedTable.getDomainObjectSubPackage());
                sb.append('.');
            }
            sb.append(this.fullyQualifiedTable.getDomainObjectName());
            sb.append("Service");
            this.setMyBatis3JavaServiceType(sb.toString());
            // serviceImpl
            sb.setLength(0);
            sb.append(this.calculateJavaServiceInterfacePackage());
            sb.append('.');
            if (StringUtility.stringHasValue(this.fullyQualifiedTable.getDomainObjectSubPackage())) {
                sb.append(this.fullyQualifiedTable.getDomainObjectSubPackage());
                sb.append('.');
            }
            sb.append("impl");
            sb.append('.');
            sb.append(this.fullyQualifiedTable.getDomainObjectName());
            sb.append("ServiceImpl");
            this.setMyBatis3JavaServiceImplType(sb.toString());
        }
    }

    // 设置controller
    protected void calculateControllerAttributes() {
        JavaControllerGeneratorConfiguration config = ((CustomContext)this.context).getJavaControllerGeneratorConfiguration();
        if (config != null) {
            // controller
            StringBuilder sb = new StringBuilder();
            sb.append(this.calculateJavaControllerClassPackage());
            sb.append('.');
            if (StringUtility.stringHasValue(this.fullyQualifiedTable.getDomainObjectSubPackage())) {
                sb.append(this.fullyQualifiedTable.getDomainObjectSubPackage());
                sb.append('.');
            }
            sb.append(this.fullyQualifiedTable.getDomainObjectName());
            sb.append("Controller");
            this.setMyBatis3JavaControllerType(sb.toString());
        }
    }

    // mapper包名
    protected String calculateJavaMapperInterfacePackage() {
        JavaMapperGeneratorConfiguration config = ((CustomContext)this.context).getJavaMapperGeneratorConfiguration();
        if (config == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(config.getTargetPackage());
            sb.append(this.fullyQualifiedTable.getSubPackageForClientOrSqlMap(true));
            return sb.toString();
        }
    }

    // service包名
    protected String calculateJavaServiceInterfacePackage() {
        JavaServiceGeneratorConfiguration config = ((CustomContext)this.context).getJavaServiceGeneratorConfiguration();
        if (config == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(config.getTargetPackage());
            sb.append(this.fullyQualifiedTable.getSubPackageForClientOrSqlMap(true));
            return sb.toString();
        }
    }

    // controller包名
    protected String calculateJavaControllerClassPackage() {
        JavaControllerGeneratorConfiguration config = ((CustomContext)this.context).getJavaControllerGeneratorConfiguration();
        if (config == null) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(config.getTargetPackage());
            sb.append(this.fullyQualifiedTable.getSubPackageForClientOrSqlMap(true));
            return sb.toString();
        }
    }

    // 生成
    @Override
    public void calculateGenerators(List<String> warnings, ProgressCallback progressCallback) {
        this.calculateJavaModelGenerators(warnings, progressCallback);
        this.calculateJavaMapperGenerators(warnings, progressCallback);
        this.calculateJavaServiceGenerators(warnings, progressCallback);
        this.calculateJavaControllerGenerators(warnings, progressCallback);
    }

    // model
    @Override
    protected void calculateJavaModelGenerators(List<String> warnings, ProgressCallback progressCallback) {
        AbstractJavaGenerator baseRecord = new BaseRecordGenerator(this.getModelProject());
        this.initializeAbstractGenerator(baseRecord, warnings, progressCallback);
        this.javaGenerators.add(baseRecord);
        // 自定义vo类
        AbstractJavaGenerator vo = new JavaVOGenerator(this.getModelProject());
        this.initializeAbstractGenerator(vo, warnings, progressCallback);
        this.javaGenerators.add(vo);
    }

    // mapper
    protected void calculateJavaMapperGenerators(List<String> warnings, ProgressCallback progressCallback) {
        AbstractJavaGenerator javaGenerator = new JavaMapperGenerator(this.getMapperProject());
        this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        this.javaGenerators.add(javaGenerator);
    }

    // service
    protected void calculateJavaServiceGenerators(List<String> warnings, ProgressCallback progressCallback) {
        AbstractJavaGenerator javaGenerator = new JavaServiceGenerator(this.getServiceProject());
        this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        this.javaGenerators.add(javaGenerator);
    }

    // controller
    protected void calculateJavaControllerGenerators(List<String> warnings, ProgressCallback progressCallback) {
        AbstractJavaGenerator javaGenerator = new JavaControllerGenerator(this.getControllerProject());
        this.initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        this.javaGenerators.add(javaGenerator);
    }
}
