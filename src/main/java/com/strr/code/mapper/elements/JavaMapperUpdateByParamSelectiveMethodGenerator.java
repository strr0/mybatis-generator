package com.strr.code.mapper.elements;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

public class JavaMapperUpdateByParamSelectiveMethodGenerator extends AbstractJavaMapperMethodGenerator {
    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        Method method = new Method("updateByParamSelective");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        FullyQualifiedJavaType recordType = this.introspectedTable.getRules().calculateAllFieldsClass();
        method.addParameter(new Parameter(recordType, "record", "@Param(\"record\")"));
        importedTypes.add(recordType);
        FullyQualifiedJavaType paramType = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaVOType());
        method.addParameter(new Parameter(paramType, "param", "@Param(\"param\")"));
        importedTypes.add(paramType);
        importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        this.addMapperAnnotations(method);
        this.addExtraImports(interfaze);
        interfaze.addImportedTypes(importedTypes);
        interfaze.addMethod(method);
    }

    public void addMapperAnnotations(Method method) {
        FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(this.introspectedTable.getMyBatis3SqlProviderType());
        StringBuilder sb = new StringBuilder();
        sb.append("@UpdateProvider(type=");
        sb.append(fqjt.getShortName());
        sb.append(".class, method=\"updateByParamSelective\")");
        method.addAnnotation(sb.toString());
    }

    public void addExtraImports(Interface interfaze) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.UpdateProvider"));
    }
}
