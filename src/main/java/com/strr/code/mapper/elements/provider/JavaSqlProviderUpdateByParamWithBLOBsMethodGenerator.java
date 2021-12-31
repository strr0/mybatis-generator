package com.strr.code.mapper.elements.provider;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

public class JavaSqlProviderUpdateByParamWithBLOBsMethodGenerator extends JavaSqlProviderUpdateByParamWithoutBLOBsMethodGenerator {
    public String getMethodName() {
        return "updateByParamWithBLOBs";
    }

    public List<IntrospectedColumn> getColumns() {
        return this.introspectedTable.getAllColumns();
    }

    public boolean callPlugins(Method method, TopLevelClass topLevelClass) {
        return this.context.getPlugins().providerUpdateByExampleWithBLOBsMethodGenerated(method, topLevelClass, this.introspectedTable);
    }
}
