package com.strr.code.service.elements.impl;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

public class JavaServiceImplUpdateMethodGenerator extends AbstractJavaServiceImplMethodGenerator {
    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        if (this.introspectedTable.getPrimaryKeyColumns().isEmpty()) {
            return;
        }
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        importedTypes.add(parameterType);
        Method method = new Method("update");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(parameterType, "record"));
        // 方法体
        StringBuilder sb = new StringBuilder();
        sb.append("return ");
        sb.append(basicName);
        sb.append("Mapper.updateByPrimaryKey(record);");
        method.addBodyLine(sb.toString());
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }
}
