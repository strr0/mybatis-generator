package com.strr.code.mapper.elements.provider;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.util.List;

public class JavaSqlProviderSelectByParamWithBLOBsMethodGenerator extends JavaSqlProviderSelectByParamWithoutBLOBsMethodGenerator {
    public List<IntrospectedColumn> getColumns() {
        return this.introspectedTable.getAllColumns();
    }

    public String getMethodName() {
        return "selectByParamWithBLOBs";
    }

    public boolean callPlugins(Method method, TopLevelClass topLevelClass) {
        return this.context.getPlugins().providerSelectByExampleWithBLOBsMethodGenerated(method, topLevelClass, this.introspectedTable);
    }
}
