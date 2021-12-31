package com.strr.code.mapper.elements.provider;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class JavaSqlProviderUpdateByParamWithoutBLOBsMethodGenerator extends AbstractJavaSqlProviderMethodGenerator {
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<String> staticImports = new TreeSet();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(NEW_BUILDER_IMPORT);
        importedTypes.add(new FullyQualifiedJavaType("java.util.Map"));
        Method method = new Method(this.getMethodName());
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Map<java.lang.String, java.lang.Object>"), "parameter"));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        method.addBodyLine("SQL sql = new SQL();");
        method.addBodyLine(String.format("%sUPDATE(\"%s\");", this.builderPrefix, StringUtility.escapeStringForJava(this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime())));
        method.addBodyLine("");
        Iterator var5 = ListUtilities.removeGeneratedAlwaysColumns(this.getColumns()).iterator();
        while(var5.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)var5.next();
            StringBuilder sb = new StringBuilder();
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            sb.insert(2, "record.");
            method.addBodyLine(String.format("%sSET(\"%s = %s\");", this.builderPrefix, StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn)), sb.toString()));
        }
        method.addBodyLine("");
        FullyQualifiedJavaType param = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaVOType());
        importedTypes.add(param);
        method.addBodyLine(String.format("%s param = (%s) parameter.get(\"param\");", param.getShortName(), param.getShortName()));
        method.addBodyLine("applyWhere(sql, param);");
        method.addBodyLine("return sql.toString();");
        topLevelClass.addStaticImports(staticImports);
        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }

    public String getMethodName() {
        return "updateByParam";
    }

    public List<IntrospectedColumn> getColumns() {
        return this.introspectedTable.getNonBLOBColumns();
    }

    public boolean callPlugins(Method method, TopLevelClass topLevelClass) {
        return this.context.getPlugins().providerUpdateByExampleWithoutBLOBsMethodGenerated(method, topLevelClass, this.introspectedTable);
    }
}
