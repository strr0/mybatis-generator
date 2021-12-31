package com.strr.code.mapper.elements.provider;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class JavaSqlProviderSelectByParamWithoutBLOBsMethodGenerator extends AbstractJavaSqlProviderMethodGenerator {
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<String> staticImports = new TreeSet();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(NEW_BUILDER_IMPORT);
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaVOType());
        importedTypes.add(type);
        Method method = new Method(this.getMethodName());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.addParameter(new Parameter(type, "param"));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        method.addBodyLine("SQL sql = new SQL();");
        for(Iterator var7 = this.getColumns().iterator(); var7.hasNext(); ) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)var7.next();
            method.addBodyLine(String.format("%sSELECT(\"%s\");", this.builderPrefix, StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getSelectListPhrase(introspectedColumn))));
        }
        method.addBodyLine(String.format("%sFROM(\"%s\");", this.builderPrefix, StringUtility.escapeStringForJava(this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime())));
        method.addBodyLine("applyWhere(sql, param);");
        method.addBodyLine("");
        method.addBodyLine("return sql.toString();");
        topLevelClass.addStaticImports(staticImports);
        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }

    public List<IntrospectedColumn> getColumns() {
        return this.introspectedTable.getNonBLOBColumns();
    }

    public String getMethodName() {
        return "selectByParam";
    }

    public boolean callPlugins(Method method, TopLevelClass topLevelClass) {
        return this.context.getPlugins().providerSelectByExampleWithoutBLOBsMethodGenerated(method, topLevelClass, this.introspectedTable);
    }
}
