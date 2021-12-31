package com.strr.code.controller.elements;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

public class JavaControllerPageMethodGenerator extends AbstractJavaControllerMethodGenerator {
    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.GetMapping"));
        Method method = new Method("page");
        method.addAnnotation("@GetMapping(\"/page\")");
        method.setVisibility(JavaVisibility.PUBLIC);
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("org.springframework.data.domain.Page");
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        importedTypes.add(parameterType);
        returnType.addTypeArgument(parameterType);
        importedTypes.add(returnType);
        method.setReturnType(returnType);
        FullyQualifiedJavaType paramType = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaVOType());
        method.addParameter(new Parameter(paramType, "param"));
        importedTypes.add(paramType);
        FullyQualifiedJavaType pageableType = new FullyQualifiedJavaType("org.springframework.data.domain.Pageable");
        method.addParameter(new Parameter(pageableType, "pageable"));
        importedTypes.add(pageableType);
        method.addBodyLine(String.format("return %sService.page(param, pageable);", basicName));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }
}
