package com.strr.code.service;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import com.strr.code.service.elements.*;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class JavaServiceGenerator extends AbstractJavaGenerator {
    public JavaServiceGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        this.progressCallback.startTask(Messages.getString("Progress.service", this.introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaServiceType());
        Interface interfaze = new Interface(type);
        interfaze.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(interfaze);
        this.addGetMethod(interfaze);
        this.addListMethod(interfaze);
        this.addSaveMethod(interfaze);
        this.addRemoveMethod(interfaze);
        this.addUpdateMethod(interfaze);
        this.addPageMethod(interfaze);
        List<CompilationUnit> answer = new ArrayList();
        answer.add(interfaze);
        List<CompilationUnit> extraCompilationUnits = this.getExtraCompilationUnits();
        if (extraCompilationUnits != null) {
            answer.addAll(extraCompilationUnits);
        }
        return answer;
    }

    protected void addGetMethod(Interface interfaze) {
        AbstractJavaServiceMethodGenerator methodGenerator = new JavaServiceGetMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addListMethod(Interface interfaze) {
        AbstractJavaServiceMethodGenerator methodGenerator = new JavaServiceListMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addSaveMethod(Interface interfaze) {
        AbstractJavaServiceMethodGenerator methodGenerator = new JavaServiceSaveMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addRemoveMethod(Interface interfaze) {
        AbstractJavaServiceMethodGenerator methodGenerator = new JavaServiceRemoveMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addUpdateMethod(Interface interfaze) {
        AbstractJavaServiceMethodGenerator methodGenerator = new JavaServiceUpdateMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void addPageMethod(Interface interfaze) {
        AbstractJavaServiceMethodGenerator methodGenerator = new JavaServicePageMethodGenerator();
        this.initializeAndExecuteGenerator(methodGenerator, interfaze);
    }

    protected void initializeAndExecuteGenerator(AbstractJavaServiceMethodGenerator methodGenerator, Interface interfaze) {
        methodGenerator.setContext(this.context);
        methodGenerator.setIntrospectedTable(this.introspectedTable);
        methodGenerator.setProgressCallback(this.progressCallback);
        methodGenerator.setWarnings(this.warnings);
        methodGenerator.addInterfaceElements(interfaze);
    }

    // serviceImpl
    public List<CompilationUnit> getExtraCompilationUnits() {
        JavaServiceImplGenerator javaServiceImplGenerator = new JavaServiceImplGenerator(this.getProject());
        javaServiceImplGenerator.setContext(this.context);
        javaServiceImplGenerator.setIntrospectedTable(this.introspectedTable);
        javaServiceImplGenerator.setProgressCallback(this.progressCallback);
        javaServiceImplGenerator.setWarnings(this.warnings);
        return javaServiceImplGenerator.getCompilationUnits();
    }
}
