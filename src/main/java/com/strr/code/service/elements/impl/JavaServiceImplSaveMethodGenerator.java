package com.strr.code.service.elements.impl;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

public class JavaServiceImplSaveMethodGenerator extends AbstractJavaServiceImplMethodGenerator {
    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        Method method = new Method("save");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        importedTypes.add(parameterType);
        method.addParameter(new Parameter(parameterType, "record"));
        // 方法体
        StringBuilder sb = new StringBuilder();
        sb.append("return ");
        sb.append(basicName);
        sb.append("Mapper.insert(record);");
        method.addBodyLine(sb.toString());
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }
}
