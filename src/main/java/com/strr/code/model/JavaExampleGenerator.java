package com.strr.code.model;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class JavaExampleGenerator extends AbstractJavaGenerator {
    public JavaExampleGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = this.introspectedTable.getFullyQualifiedTable();
        this.progressCallback.startTask(Messages.getString("Progress.6", table.toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(this.introspectedTable.getExampleType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        addBasic(topLevelClass);
        commentGenerator.addJavaFileComment(topLevelClass);
        List<CompilationUnit> answer = new ArrayList<>();
        answer.add(topLevelClass);
        return answer;
    }

    protected void addBasic(TopLevelClass topLevelClass) {
        // 基类
        FullyQualifiedJavaType sup = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        topLevelClass.addImportedType(sup);
        topLevelClass.setSuperClass(sup);
    }
}
