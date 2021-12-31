package com.strr.code.mapper;

import com.strr.code.mapper.elements.provider.*;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class JavaSqlProviderGenerator extends AbstractJavaGenerator {
    public JavaSqlProviderGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        this.progressCallback.startTask(Messages.getString("Progress.18", this.introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(this.introspectedTable.getMyBatis3SqlProviderType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        boolean addApplyWhereMethod = false;
        addApplyWhereMethod |= this.addCountByParamMethod(topLevelClass);
        addApplyWhereMethod |= this.addDeleteByParamMethod(topLevelClass);
        this.addInsertSelectiveMethod(topLevelClass);
        addApplyWhereMethod |= this.addSelectByParamMethod(topLevelClass);
        addApplyWhereMethod |= this.addUpdateByParamSelectiveMethod(topLevelClass);
        addApplyWhereMethod |= this.addUpdateByParamMethod(topLevelClass);
        this.addUpdateByPrimaryKeySelectiveMethod(topLevelClass);
        this.addSelectByParamSelectiveMethod(topLevelClass);
        if (addApplyWhereMethod) {
            this.addApplyWhereMethod(topLevelClass);
        }

        List<CompilationUnit> answer = new ArrayList();
        if (!topLevelClass.getMethods().isEmpty() && this.context.getPlugins().providerGenerated(topLevelClass, this.introspectedTable)) {
            answer.add(topLevelClass);
        }

        return answer;
    }

    protected boolean addCountByParamMethod(TopLevelClass topLevelClass) {
        AbstractJavaSqlProviderMethodGenerator methodGenerator = new JavaSqlProviderCountByParamMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
        return true;
    }

    protected boolean addDeleteByParamMethod(TopLevelClass topLevelClass) {
        AbstractJavaSqlProviderMethodGenerator methodGenerator = new JavaSqlProviderDeleteByParamMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
        return true;
    }

    protected void addInsertSelectiveMethod(TopLevelClass topLevelClass) {
        AbstractJavaSqlProviderMethodGenerator methodGenerator = new JavaSqlProviderInsertSelectiveMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected boolean addSelectByParamMethod(TopLevelClass topLevelClass) {
        AbstractJavaSqlProviderMethodGenerator methodGenerator = new JavaSqlProviderSelectByParamMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
        return true;
    }

    protected boolean addUpdateByParamSelectiveMethod(TopLevelClass topLevelClass) {
        AbstractJavaSqlProviderMethodGenerator methodGenerator = new JavaSqlProviderUpdateByParamSelectiveMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
        return true;
    }

    protected boolean addUpdateByParamMethod(TopLevelClass topLevelClass) {
        AbstractJavaSqlProviderMethodGenerator methodGenerator = new JavaSqlProviderUpdateByParamMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
        return true;
    }

    protected void addUpdateByPrimaryKeySelectiveMethod(TopLevelClass topLevelClass) {
        if (this.introspectedTable.getRules().generateUpdateByPrimaryKeySelective()) {
            AbstractJavaSqlProviderMethodGenerator methodGenerator = new JavaSqlProviderUpdateByPrimaryKeySelectiveMethodGenerator();
            this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
        }

    }

    protected void addSelectByParamSelectiveMethod(TopLevelClass topLevelClass) {
        AbstractJavaSqlProviderMethodGenerator methodGenerator = new JavaSqlProviderSelectByParamSelectiveMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);

    }

    protected void addApplyWhereMethod(TopLevelClass topLevelClass) {
        AbstractJavaSqlProviderMethodGenerator methodGenerator = new JavaSqlProviderApplyWhereMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected void initializeAndExecuteGenerator(AbstractJavaSqlProviderMethodGenerator methodGenerator, TopLevelClass topLevelClass) {
        methodGenerator.setContext(this.context);
        methodGenerator.setIntrospectedTable(this.introspectedTable);
        methodGenerator.setProgressCallback(this.progressCallback);
        methodGenerator.setWarnings(this.warnings);
        methodGenerator.addClassElements(topLevelClass);
    }
}
