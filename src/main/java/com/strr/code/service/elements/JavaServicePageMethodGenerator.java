package com.strr.code.service.elements;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

public class JavaServicePageMethodGenerator extends AbstractJavaServiceMethodGenerator {
    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        Method method = new Method("page");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("org.springframework.data.domain.Page");
        importedTypes.add(returnType);
        FullyQualifiedJavaType listType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        importedTypes.add(listType);
        returnType.addTypeArgument(listType);
        method.setReturnType(returnType);
        FullyQualifiedJavaType paramType = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaVOType());
        method.addParameter(new Parameter(paramType, "param"));
        importedTypes.add(paramType);
        FullyQualifiedJavaType pageableType = new FullyQualifiedJavaType("org.springframework.data.domain.Pageable");
        method.addParameter(new Parameter(pageableType, "pageable"));
        importedTypes.add(pageableType);
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }
}
