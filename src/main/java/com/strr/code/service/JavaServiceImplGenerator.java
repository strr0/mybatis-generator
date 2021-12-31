package com.strr.code.service;

import com.strr.code.config.CustomIntrospectedTable;
import com.strr.code.service.elements.impl.*;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class JavaServiceImplGenerator extends AbstractJavaGenerator {
    public JavaServiceImplGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        this.progressCallback.startTask(Messages.getString("Progress.service.impl", this.introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaServiceImplType());
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
        return answer;
    }

    // 基本
    protected void addBasic(TopLevelClass topLevelClass) {
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        // service注解
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        topLevelClass.addAnnotation("@Service");
        // service
        FullyQualifiedJavaType service = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaServiceType());
        topLevelClass.addImportedType(service);
        topLevelClass.addSuperInterface(service);
        // mapper
        FullyQualifiedJavaType mapper = new FullyQualifiedJavaType(this.introspectedTable.getMyBatis3JavaMapperType());
        topLevelClass.addImportedType(mapper);
        Field field = new Field(basicName + "Mapper", mapper);
        field.setVisibility(JavaVisibility.PRIVATE);
        field.setFinal(true);
        topLevelClass.addField(field);
        // 构造函数
        topLevelClass.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
        Method method = new Method(topLevelClass.getType().getShortName());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.addParameter(new Parameter(mapper, basicName + "Mapper"));
        StringBuilder sb = new StringBuilder();
        sb.append("this.");
        sb.append(basicName);
        sb.append("Mapper = ");
        sb.append(basicName);
        sb.append("Mapper;");
        method.addBodyLine(sb.toString());
        method.addAnnotation("@Autowired");
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        topLevelClass.addMethod(method);
    }

    protected void addGetMethod(TopLevelClass topLevelClass) {
        AbstractJavaServiceImplMethodGenerator methodGenerator = new JavaServiceImplGetMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected void addListMethod(TopLevelClass topLevelClass) {
        AbstractJavaServiceImplMethodGenerator methodGenerator = new JavaServiceImplListMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected void addSaveMethod(TopLevelClass topLevelClass) {
        AbstractJavaServiceImplMethodGenerator methodGenerator = new JavaServiceImplSaveMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected void addRemoveMethod(TopLevelClass topLevelClass) {
        AbstractJavaServiceImplMethodGenerator methodGenerator = new JavaServiceImplRemoveMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected void addUpdateMethod(TopLevelClass topLevelClass) {
        AbstractJavaServiceImplMethodGenerator methodGenerator = new JavaServiceImplUpdateMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected void addPageMethod(TopLevelClass topLevelClass) {
        AbstractJavaServiceImplMethodGenerator methodGenerator = new JavaServiceImplPageMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, topLevelClass);
    }

    protected void initializeAndExecuteGenerator(AbstractJavaServiceImplMethodGenerator methodGenerator, TopLevelClass topLevelClass) {
        methodGenerator.setContext(this.context);
        methodGenerator.setIntrospectedTable(this.introspectedTable);
        methodGenerator.setProgressCallback(this.progressCallback);
        methodGenerator.setWarnings(this.warnings);
        methodGenerator.addClassElements(topLevelClass);
    }
}
