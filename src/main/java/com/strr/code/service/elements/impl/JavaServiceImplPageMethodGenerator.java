package com.strr.code.service.elements.impl;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

public class JavaServiceImplPageMethodGenerator extends AbstractJavaServiceImplMethodGenerator {
    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        String basicName = ((CustomIntrospectedTable)this.introspectedTable).getBasicName();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        Method method = new Method("page");
        method.setVisibility(JavaVisibility.PUBLIC);
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
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.data.domain.PageImpl"));
        importedTypes.add(new FullyQualifiedJavaType("org.springframework.data.domain.PageRequest"));
        method.addBodyLine(String.format("long count = %sMapper.countByParam(param);", basicName));
        method.addBodyLine(String.format("List<%s> list = %sMapper.selectByParamSelective(param, pageable);", listType.getShortName(), basicName));
        method.addBodyLine("return new PageImpl<>(list, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), count);");
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }
}
