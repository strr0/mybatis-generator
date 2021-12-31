package com.strr.code.mapper.elements;

import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.config.GeneratedKey;

import java.util.Set;
import java.util.TreeSet;

public class JavaMapperInsertSelectiveMethodGenerator extends AbstractJavaMapperMethodGenerator {
    @Override
    public void addInterfaceElements(Interface interfaze) {
        Method method = new Method("insertSelective");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        FullyQualifiedJavaType parameterType = this.introspectedTable.getRules().calculateAllFieldsClass();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(parameterType);
        method.addParameter(new Parameter(parameterType, "record"));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        this.addMapperAnnotations(method);
        if (this.context.getPlugins().clientInsertSelectiveMethodGenerated(method, interfaze, this.introspectedTable)) {
            this.addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }

    }

    public void addMapperAnnotations(Method method) {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(this.introspectedTable.getMyBatis3SqlProviderType());
        StringBuilder sb = new StringBuilder();
        sb.append("@InsertProvider(type=");
        sb.append(fqjt.getShortName());
        sb.append(".class, method=\"");
        sb.append(this.introspectedTable.getInsertSelectiveStatementId());
        sb.append("\")");
        method.addAnnotation(sb.toString());
        GeneratedKey gk = this.introspectedTable.getGeneratedKey();
        if (gk != null) {
            this.addGeneratedKeyAnnotation(method, gk);
        }
    }

    public void addExtraImports(Interface interfaze) {
        GeneratedKey gk = this.introspectedTable.getGeneratedKey();
        if (gk != null) {
            this.addGeneratedKeyImports(interfaze, gk);
        }
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.InsertProvider"));
    }
}
