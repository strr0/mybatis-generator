package com.strr.code.mapper.elements.provider;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.JavaBeansUtil;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class JavaSqlProviderUpdateByParamSelectiveMethodGenerator extends AbstractJavaSqlProviderMethodGenerator {
    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<String> staticImports = new TreeSet();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(NEW_BUILDER_IMPORT);
        importedTypes.add(new FullyQualifiedJavaType("java.util.Map"));
        Method method = new Method("updateByParamSelective");
        method.setReturnType(FullyQualifiedJavaType.getStringInstance());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.addParameter(new Parameter(new FullyQualifiedJavaType("java.util.Map<java.lang.String, java.lang.Object>"), "parameter"));
        FullyQualifiedJavaType record = this.introspectedTable.getRules().calculateAllFieldsClass();
        importedTypes.add(record);
        method.addBodyLine(String.format("%s record = (%s) parameter.get(\"record\");", record.getShortName(), record.getShortName()));
        FullyQualifiedJavaType param = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaVOType());
        importedTypes.add(param);
        method.addBodyLine(String.format("%s param = (%s) parameter.get(\"param\");", param.getShortName(), param.getShortName()));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        method.addBodyLine("");
        method.addBodyLine("SQL sql = new SQL();");
        method.addBodyLine(String.format("%sUPDATE(\"%s\");", this.builderPrefix, StringUtility.escapeStringForJava(this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime())));
        method.addBodyLine("");
        for(Iterator var7 = ListUtilities.removeGeneratedAlwaysColumns(this.introspectedTable.getAllColumns()).iterator(); var7.hasNext(); method.addBodyLine("")) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)var7.next();
            if (!introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
                method.addBodyLine(String.format("if (record.%s() != null) {", JavaBeansUtil.getGetterMethodName(introspectedColumn.getJavaProperty(), introspectedColumn.getFullyQualifiedJavaType())));
            }
            StringBuilder sb = new StringBuilder();
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            sb.insert(2, "record.");
            method.addBodyLine(String.format("%sSET(\"%s = %s\");", this.builderPrefix, StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn)), sb.toString()));
            if (!introspectedColumn.getFullyQualifiedJavaType().isPrimitive()) {
                method.addBodyLine("}");
            }
        }
        method.addBodyLine("applyWhere(sql, param);");
        method.addBodyLine("return sql.toString();");
        topLevelClass.addStaticImports(staticImports);
        topLevelClass.addImportedTypes(importedTypes);
        topLevelClass.addMethod(method);
    }
}
