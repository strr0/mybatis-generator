package com.strr.code.mapper.elements;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class JavaMapperSelectByParamSelectiveMethodGenerator extends AbstractJavaMapperMethodGenerator {
    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(FullyQualifiedJavaType.getNewListInstance());
        Method method = new Method("selectByParamSelective");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        FullyQualifiedJavaType listType;
        if (this.introspectedTable.getRules().generateBaseRecordClass()) {
            listType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        } else {
            if (!this.introspectedTable.getRules().generatePrimaryKeyClass()) {
                throw new RuntimeException(Messages.getString("RuntimeError.12"));
            }
            listType = new FullyQualifiedJavaType(this.introspectedTable.getPrimaryKeyType());
        }
        importedTypes.add(listType);
        returnType.addTypeArgument(listType);
        method.setReturnType(returnType);
        FullyQualifiedJavaType paramType = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaVOType());
        method.addParameter(new Parameter(paramType, "param", "@Param(\"param\")"));
        importedTypes.add(paramType);
        FullyQualifiedJavaType pageableType = new FullyQualifiedJavaType("org.springframework.data.domain.Pageable");
        method.addParameter(new Parameter(pageableType, "pageable", "@Param(\"pageable\")"));
        importedTypes.add(pageableType);
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        this.addMapperAnnotations(interfaze, method);
        this.addExtraImports(interfaze);
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    public void addMapperAnnotations(Interface interfaze, Method method) {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(this.introspectedTable.getMyBatis3SqlProviderType());
        StringBuilder sb = new StringBuilder();
        sb.append("@SelectProvider(type=");
        sb.append(fqjt.getShortName());
        sb.append(".class, method=\"selectByParamSelective\")");
        method.addAnnotation(sb.toString());
        if (this.introspectedTable.isConstructorBased()) {
            method.addAnnotation("@ConstructorArgs({");
        } else {
            method.addAnnotation("@Results({");
        }
        Iterator<IntrospectedColumn> iterPk = this.introspectedTable.getPrimaryKeyColumns().iterator();
        Iterator iterNonPk;
        IntrospectedColumn introspectedColumn;
        for(iterNonPk = this.introspectedTable.getBaseColumns().iterator(); iterPk.hasNext(); method.addAnnotation(sb.toString())) {
            introspectedColumn = (IntrospectedColumn)iterPk.next();
            sb.setLength(0);
            OutputUtilities.javaIndent(sb, 1);
            sb.append(this.getResultAnnotation(interfaze, introspectedColumn, true, this.introspectedTable.isConstructorBased()));
            if (iterPk.hasNext() || iterNonPk.hasNext()) {
                sb.append(',');
            }
        }
        for(; iterNonPk.hasNext(); method.addAnnotation(sb.toString())) {
            introspectedColumn = (IntrospectedColumn)iterNonPk.next();
            sb.setLength(0);
            OutputUtilities.javaIndent(sb, 1);
            sb.append(this.getResultAnnotation(interfaze, introspectedColumn, false, this.introspectedTable.isConstructorBased()));
            if (iterNonPk.hasNext()) {
                sb.append(',');
            }
        }
        method.addAnnotation("})");
    }

    public void addExtraImports(Interface interfaze) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.SelectProvider"));
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.type.JdbcType"));
        if (this.introspectedTable.isConstructorBased()) {
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Arg"));
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.ConstructorArgs"));
        } else {
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Result"));
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Results"));
        }
    }
}
