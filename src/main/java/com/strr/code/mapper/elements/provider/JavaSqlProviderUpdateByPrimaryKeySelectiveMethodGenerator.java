package com.strr.code.mapper.elements.provider;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class JavaSqlProviderUpdateByPrimaryKeySelectiveMethodGenerator extends AbstractJavaSqlProviderMethodGenerator {
    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<String> staticImports = new TreeSet();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(NEW_BUILDER_IMPORT);
        FullyQualifiedJavaType fqjt = this.introspectedTable.getRules().calculateAllFieldsClass();
        importedTypes.add(fqjt);
        Method method = new Method("updateByPrimaryKeySelective");
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(new Parameter(fqjt, "record"));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        method.addBodyLine("SQL sql = new SQL();");
        method.addBodyLine(String.format("%sUPDATE(\"%s\");", this.builderPrefix, StringUtility.escapeStringForJava(this.introspectedTable.getFullyQualifiedTableNameAtRuntime())));
        method.addBodyLine("");
        Iterator var6;
        IntrospectedColumn introspectedColumn;
        for(var6 = ListUtilities.removeGeneratedAlwaysColumns(this.introspectedTable.getNonPrimaryKeyColumns()).iterator(); var6.hasNext(); method.addBodyLine("")) {
            introspectedColumn = (IntrospectedColumn)var6.next();
            if (!introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
                method.addBodyLine(String.format("if (record.%s() != null) {", JavaBeansUtil.getGetterMethodName(introspectedColumn.getJavaProperty(), introspectedColumn.getFullyQualifiedJavaType())));
            }
            method.addBodyLine(String.format("%sSET(\"%s = %s\");", this.builderPrefix, StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn)), MyBatis3FormattingUtilities.getParameterClause(introspectedColumn)));
            if (!introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
                method.addBodyLine("}");
            }
        }
        var6 = this.introspectedTable.getPrimaryKeyColumns().iterator();
        while(var6.hasNext()) {
            introspectedColumn = (IntrospectedColumn)var6.next();
            method.addBodyLine(String.format("%sWHERE(\"%s = %s\");", this.builderPrefix, StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn)), MyBatis3FormattingUtilities.getParameterClause(introspectedColumn)));
        }
        method.addBodyLine("");
        method.addBodyLine("return sql.toString();");

        if (this.context.getPlugins().providerUpdateByPrimaryKeySelectiveMethodGenerated(method, topLevelClass, this.introspectedTable)) {
            topLevelClass.addStaticImports(staticImports);
            topLevelClass.addImportedTypes(importedTypes);
            topLevelClass.addMethod(method);
        }
    }
}
