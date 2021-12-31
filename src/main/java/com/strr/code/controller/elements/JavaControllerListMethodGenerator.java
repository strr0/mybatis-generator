package com.strr.code.controller.elements;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

public class JavaControllerListMethodGenerator extends AbstractJavaControllerMethodGenerator {
    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        String result = ((CustomIntrospectedTable) this.introspectedTable).getControllerResult();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.GetMapping"));
        Method method = new Method("list");
        method.addAnnotation("@GetMapping(\"/list\")");
        method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(result);
        // list
        FullyQualifiedJavaType listType = FullyQualifiedJavaType.getNewListInstance();
        importedTypes.add(listType);
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        importedTypes.add(parameterType);
        listType.addTypeArgument(parameterType);
        returnType.addTypeArgument(listType);
        importedTypes.add(returnType);
        method.setReturnType(returnType);
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaVOType());
        importedTypes.add(type);
        method.addParameter(new Parameter(type, "param"));
        method.addBodyLine(String.format("List<%s> %sList = %sService.list(param);", parameterType.getShortName(), basicName, basicName));
        method.addBodyLine(String.format("return Result.ok(%sList);", basicName));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }
}
