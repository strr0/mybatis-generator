package com.strr.code.controller.elements;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class JavaControllerGetMethodGenerator extends AbstractJavaControllerMethodGenerator {
    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getPrimaryKeyColumns();
        if (introspectedColumns.isEmpty()) {
            return;
        }
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        String result = ((CustomIntrospectedTable) this.introspectedTable).getControllerResult();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.GetMapping"));
        Method method = new Method("get");
        method.addAnnotation("@GetMapping(\"/get\")");
        method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(result);
        // java实体
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        returnType.addTypeArgument(parameterType);
        importedTypes.add(parameterType);
        method.setReturnType(returnType);
        importedTypes.add(returnType);
        // 主键
        IntrospectedColumn introspectedColumn = introspectedColumns.get(0);
        FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
        importedTypes.add(type);
        method.addParameter(new Parameter(type, introspectedColumn.getJavaProperty()));
        method.addBodyLine(String.format("%s %s = %sService.get(%s);", parameterType.getShortName(), basicName, basicName, introspectedColumn.getJavaProperty()));
        method.addBodyLine(String.format("if (%s != null) {", basicName));
        method.addBodyLine("return Result.ok();");
        method.addBodyLine("}");
        method.addBodyLine("return Result.error();");
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }
}
