package com.strr.code.service.elements.impl;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class JavaServiceImplRemoveMethodGenerator extends AbstractJavaServiceImplMethodGenerator {
    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getPrimaryKeyColumns();
        if (introspectedColumns.isEmpty()) {
            return;
        }
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        Method method = new Method("remove");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        // 主键
        IntrospectedColumn introspectedColumn = introspectedColumns.get(0);
        FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
        importedTypes.add(type);
        method.addParameter(new Parameter(type, introspectedColumn.getJavaProperty()));
        // 方法体
        StringBuilder sb = new StringBuilder();
        sb.append("return ");
        sb.append(basicName);
        sb.append("Mapper.deleteByPrimaryKey(");
        sb.append(introspectedColumn.getJavaProperty());
        sb.append(')');
        sb.append(';');
        method.addBodyLine(sb.toString());
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }
}
