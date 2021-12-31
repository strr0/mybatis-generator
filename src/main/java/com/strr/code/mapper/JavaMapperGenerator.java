package com.strr.code.mapper;

import com.strr.code.config.CustomContext;
import com.strr.code.mapper.elements.*;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.StringUtility;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class JavaMapperGenerator extends AbstractJavaGenerator {
    public JavaMapperGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        this.progressCallback.startTask(Messages.getString("Progress.17", this.introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(this.introspectedTable.getMyBatis3JavaMapperType());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Mapper"));
        interfaze.addAnnotation("@Mapper");
        commentGenerator.addJavaFileComment(interfaze);
        String rootInterface = this.introspectedTable.getTableConfigurationProperty("rootInterface");
        if (!StringUtility.stringHasValue(rootInterface)) {
            rootInterface = ((CustomContext)this.context).getJavaMapperGeneratorConfiguration().getProperty("rootInterface");
        }
        if (StringUtility.stringHasValue(rootInterface)) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
            interfaze.addSuperInterface(fqjt);
            interfaze.addImportedType(fqjt);
        }
        this.addCountByParamMethod(interfaze);
        this.addDeleteByParamMethod(interfaze);
        this.addDeleteByPrimaryKeyMethod(interfaze);
        this.addInsertMethod(interfaze);
        this.addInsertSelectiveMethod(interfaze);
        this.addSelectByParamMethod(interfaze);
        this.addSelectByPrimaryKeyMethod(interfaze);
        this.addUpdateByParamSelectiveMethod(interfaze);
        this.addUpdateByParamMethod(interfaze);
        this.addUpdateByPrimaryKeySelectiveMethod(interfaze);
        this.addUpdateByPrimaryKeyMethod(interfaze);
        this.addSelectByParamSelectiveMethod(interfaze);
        List<CompilationUnit> answer = new ArrayList();
        if (this.context.getPlugins().clientGenerated(interfaze, this.introspectedTable)) {
            answer.add(interfaze);
        }

        List<CompilationUnit> extraCompilationUnits = this.getExtraCompilationUnits();
        if (extraCompilationUnits != null) {
            answer.addAll(extraCompilationUnits);
        }

        return answer;
    }

    protected void addCountByParamMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new JavaMapperCountByParamMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addDeleteByParamMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new JavaMapperDeleteByParamMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addDeleteByPrimaryKeyMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new JavaMapperDeleteByPrimaryKeyMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addInsertMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new JavaMapperInsertMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addInsertSelectiveMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new JavaMapperInsertSelectiveMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addSelectByParamMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new JavaMapperSelectByParamMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addSelectByPrimaryKeyMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new JavaMapperSelectByPrimaryKeyMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addUpdateByParamSelectiveMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new JavaMapperUpdateByParamSelectiveMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addUpdateByParamMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new JavaMapperUpdateByParamMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addUpdateByPrimaryKeySelectiveMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new JavaMapperUpdateByPrimaryKeySelectiveMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addUpdateByPrimaryKeyMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new JavaMapperUpdateByPrimaryKeyMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addSelectByParamSelectiveMethod(Interface interfaze) {
        AbstractJavaMapperMethodGenerator methodGenerator = new JavaMapperSelectByParamSelectiveMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);

    }

    protected void initializeAndExecuteGenerator(AbstractJavaMapperMethodGenerator methodGenerator, Interface interfaze) {
        methodGenerator.setContext(this.context);
        methodGenerator.setIntrospectedTable(this.introspectedTable);
        methodGenerator.setProgressCallback(this.progressCallback);
        methodGenerator.setWarnings(this.warnings);
        methodGenerator.addInterfaceElements(interfaze);
    }

    public List<CompilationUnit> getExtraCompilationUnits() {
        JavaSqlProviderGenerator javaSqlProviderGenerator = new JavaSqlProviderGenerator(this.getProject());
        javaSqlProviderGenerator.setContext(this.context);
        javaSqlProviderGenerator.setIntrospectedTable(this.introspectedTable);
        javaSqlProviderGenerator.setProgressCallback(this.progressCallback);
        javaSqlProviderGenerator.setWarnings(this.warnings);
        return javaSqlProviderGenerator.getCompilationUnits();
    }
}
