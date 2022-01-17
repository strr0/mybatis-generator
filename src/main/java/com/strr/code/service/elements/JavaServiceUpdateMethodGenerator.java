package com.strr.code.service.elements;

import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

public class JavaServiceUpdateMethodGenerator extends AbstractJavaServiceMethodGenerator {
    @Override
    public void addInterfaceElements(Interface interfaze) {
        if (this.introspectedTable.getPrimaryKeyColumns().isEmpty()) {
            return;
        }
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        importedTypes.add(parameterType);
        Method method = new Method("update");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(parameterType, "record"));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }
}
