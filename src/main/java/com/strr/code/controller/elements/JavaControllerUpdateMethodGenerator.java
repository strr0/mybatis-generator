package com.strr.code.controller.elements;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

public class JavaControllerUpdateMethodGenerator extends AbstractJavaControllerMethodGenerator {
    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        String result = ((CustomIntrospectedTable) this.introspectedTable).getControllerResult();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        importedTypes.add(parameterType);
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.PutMapping"));
        Method method = new Method("update");
        method.addAnnotation("@PutMapping(\"/update\")");
        method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(result);
        returnType.addTypeArgument(parameterType);
        method.setReturnType(returnType);
        method.addParameter(new Parameter(parameterType, "record"));
        // 方法体
        method.addBodyLine(String.format("if (%sService.update(record) == 1) {", basicName));
        method.addBodyLine("return Result.ok(record);");
        method.addBodyLine("}");
        method.addBodyLine("return Result.error();");
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }
}
