package com.strr.code.mapper.elements.provider;

import com.strr.code.config.CustomIntrospectedTable;
import org.mybatis.generator.api.dom.java.*;

import java.util.Set;
import java.util.TreeSet;

public class JavaSqlProviderApplyWhereMethodGenerator extends AbstractJavaSqlProviderMethodGenerator {
    private static final String[] BEGINNING_METHOD_LINES = new String[]{"if (param == null) {", "return;", "}", "", "StringBuilder sb = new StringBuilder();", ""};
    private static final String[] ENDING_METHOD_LINES = new String[]{"if (sb.length() > 0) {", "sql.WHERE(sb.toString());", "}"};

    @Override
    public void addClassElements(TopLevelClass topLevelClass) {
        Set<String> staticImports = new TreeSet();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(NEW_BUILDER_IMPORT);
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(((CustomIntrospectedTable)this.introspectedTable).getMyBatis3JavaVOType());
        importedTypes.add(type);
        Method method = new Method("applyWhere");
        method.setVisibility(JavaVisibility.PROTECTED);
        method.addParameter(new Parameter(NEW_BUILDER_IMPORT, "sql"));
        method.addParameter(new Parameter(type, "param"));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        for(String methodLine : BEGINNING_METHOD_LINES) {
            method.addBodyLine(methodLine);
        }
        for(String methodLine : ENDING_METHOD_LINES) {
            method.addBodyLine(methodLine);
        }
        if (this.context.getPlugins().providerApplyWhereMethodGenerated(method, topLevelClass, this.introspectedTable)) {
            topLevelClass.addStaticImports(staticImports);
            topLevelClass.addImportedTypes(importedTypes);
            topLevelClass.addMethod(method);
        }
    }
}
