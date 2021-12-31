package com.strr.code.controller;

import com.strr.code.config.CustomIntrospectedTable;
import com.strr.code.controller.elements.*;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class JavaControllerGenerator extends AbstractJavaGenerator {
    public JavaControllerGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        this.progressCallback.startTask(Messages.getString("Progress.controller", this.introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaControllerType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        this.addBasic(topLevelClass);
        this.addGetMethod(topLevelClass);
        this.addListMethod(topLevelClass);
        this.addSaveMethod(topLevelClass);
        this.addRemoveMethod(topLevelClass);
        this.addUpdateMethod(topLevelClass);
        this.addPageMethod(topLevelClass);
        List<CompilationUnit> answer = new ArrayList();
        answer.add(topLevelClass);
        // 是否创建Result
        if (((CustomIntrospectedTable) this.introspectedTable).needResult()) {
            List<CompilationUnit> extraCompilationUnits = this.getExtraCompilationUnits();
            if (extraCompilationUnits != null) {
                answer.addAll(extraCompilationUnits);
            }
        }
        return answer;
    }

    // 基本
    protected void addBasic(TopLevelClass topLevelClass) {
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        // controller注解
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RestController"));
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMapping"));
        topLevelClass.addAnnotation("@RestController");
        StringBuilder sb = new StringBuilder();
        sb.append("@RequestMapping(\"/");
        sb.append(basicName);
        sb.append("\")");
        topLevelClass.addAnnotation(sb.toString());
        // mapper
        FullyQualifiedJavaType service = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaServiceType());
        topLevelClass.addImportedType(service);
        Field field = new Field(basicName + "Service", service);
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setFinal(true);
        topLevelClass.addField(field);
        // 构造函数
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        Method method = new Method(topLevelClass.getType().getShortName());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.addParameter(new Parameter(service, basicName + "Service"));
        sb.setLength(0);
        sb.append("this.");
        sb.append(basicName);
        sb.append("Service = ");
        sb.append(basicName);
        sb.append("Service;");
        method.addBodyLine(sb.toString());
        method.addAnnotation("@Autowired");
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        topLevelClass.addMethod(method);
    }

    protected void addGetMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new JavaControllerGetMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected void addListMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new JavaControllerListMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected void addSaveMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new JavaControllerSaveMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected void addRemoveMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new JavaControllerRemoveMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected void addUpdateMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new JavaControllerUpdateMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected void addPageMethod(TopLevelClass topLevelClass) {
        AbstractJavaControllerMethodGenerator methodGenerator = new JavaControllerPageMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected void initializeAndExecuteGenerator(AbstractJavaControllerMethodGenerator methodGenerator, TopLevelClass topLevelClass) {
        methodGenerator.setContext(this.context);
        methodGenerator.setIntrospectedTable(this.introspectedTable);
        methodGenerator.setProgressCallback(this.progressCallback);
        methodGenerator.setWarnings(this.warnings);
        methodGenerator.addClassElements(topLevelClass);
    }

    public List<CompilationUnit> getExtraCompilationUnits() {
        JavaControllerResultGenerator javaControllerResultGenerator = new JavaControllerResultGenerator(this.getProject());
        javaControllerResultGenerator.setContext(this.context);
        javaControllerResultGenerator.setIntrospectedTable(this.introspectedTable);
        javaControllerResultGenerator.setProgressCallback(this.progressCallback);
        javaControllerResultGenerator.setWarnings(this.warnings);
        return javaControllerResultGenerator.getCompilationUnits();
    }
}
