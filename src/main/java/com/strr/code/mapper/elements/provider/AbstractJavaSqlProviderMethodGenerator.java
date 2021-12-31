package com.strr.code.mapper.elements.provider;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractGenerator;

public abstract class AbstractJavaSqlProviderMethodGenerator extends AbstractGenerator {
    protected static final FullyQualifiedJavaType NEW_BUILDER_IMPORT = new FullyQualifiedJavaType("org.apache.ibatis.jdbc.SQL");
    protected static final String builderPrefix = "sql.";

    public abstract void addClassElements(TopLevelClass topLevelClass);
}
