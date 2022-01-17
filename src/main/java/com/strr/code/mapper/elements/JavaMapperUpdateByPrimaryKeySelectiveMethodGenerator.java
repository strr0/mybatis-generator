package com.strr.code.mapper.elements;

import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

public class JavaMapperUpdateByPrimaryKeySelectiveMethodGenerator extends AbstractJavaMapperMethodGenerator {
    @Override
    public void addInterfaceElements(Interface interfaze) {
        if (this.introspectedTable.getPrimaryKeyColumns().isEmpty()) {
            return;
        }
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        FullyQualifiedJavaType parameterType;
        if (this.introspectedTable.getRules().generateRecordWithBLOBsClass()) {
            parameterType = new FullyQualifiedJavaType(this.introspectedTable.getRecordWithBLOBsType());
        } else {
            parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        }
        importedTypes.add(parameterType);
        Method method = new Method("updateByPrimaryKeySelective");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(parameterType, "record"));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        this.addMapperAnnotations(method);
        if (this.context.getPlugins().clientUpdateByPrimaryKeySelectiveMethodGenerated(method, interfaze, this.introspectedTable)) {
            this.addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Method method) {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(this.introspectedTable.getMyBatis3SqlProviderType());
        StringBuilder sb = new StringBuilder();
        sb.append("@UpdateProvider(type=");
        sb.append(fqjt.getShortName());
        sb.append(".class, method=\"");
        sb.append(this.introspectedTable.getUpdateByPrimaryKeySelectiveStatementId());
        sb.append("\")");
        method.addAnnotation(sb.toString());
    }

    public void addExtraImports(Interface interfaze) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.UpdateProvider"));
    }
}
