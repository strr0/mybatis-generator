package com.strr.code.service.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class JavaServiceGetMethodGenerator extends AbstractJavaServiceMethodGenerator {
    @Override
    public void addInterfaceElements(Interface interfaze) {
        List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getPrimaryKeyColumns();
        if (introspectedColumns.isEmpty()) {
            return;
        }
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        Method method = new Method("get");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        FullyQualifiedJavaType returnType = this.introspectedTable.getRules().calculateAllFieldsClass();
        method.setReturnType(returnType);
        importedTypes.add(returnType);
        // 主键
        IntrospectedColumn introspectedColumn = introspectedColumns.get(0);
        FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
        importedTypes.add(type);
        method.addParameter(new Parameter(type, introspectedColumn.getJavaProperty()));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }
}
