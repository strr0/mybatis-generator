package com.strr.code.controller.elements;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class JavaControllerRemoveMethodGenerator extends AbstractJavaControllerMethodGenerator {
    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        String result = ((CustomIntrospectedTable) this.introspectedTable).getControllerResult();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.DeleteMapping"));
        Method method = new Method("remove");
        method.addAnnotation("@DeleteMapping(\"/remove\")");
        method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(result);
        FullyQualifiedJavaType integer = new FullyQualifiedJavaType("Integer");
        returnType.addTypeArgument(integer);
        method.setReturnType(returnType);
        List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getPrimaryKeyColumns();
        if (introspectedColumns.size() > 0) {
            IntrospectedColumn introspectedColumn = introspectedColumns.get(0);
            FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
            importedTypes.add(type);
            method.addParameter(new Parameter(type, introspectedColumn.getJavaProperty()));
            // 方法体
            method.addBodyLine(String.format("if (%sService.remove(%s) == 1) {", basicName, introspectedColumn.getJavaProperty()));
            method.addBodyLine("return Result.ok();");
            method.addBodyLine("}");
            method.addBodyLine("return Result.error();");
        }
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }
}
