package com.strr.code.mapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.*;

public class JavaMapperInsertMethodGenerator extends AbstractJavaMapperMethodGenerator {
    @Override
    public void addInterfaceElements(Interface interfaze) {
        Method method = new Method("insert");
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        FullyQualifiedJavaType parameterType;
        parameterType = this.introspectedTable.getRules().calculateAllFieldsClass();
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(parameterType);
        method.addParameter(new Parameter(parameterType, "record"));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        this.addMapperAnnotations(method);
        if (this.context.getPlugins().clientInsertMethodGenerated(method, interfaze, this.introspectedTable)) {
            this.addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Method method) {
        method.addAnnotation("@Insert({");
        StringBuilder insertClause = new StringBuilder();
        StringBuilder valuesClause = new StringBuilder();
        OutputUtilities.javaIndent(insertClause, 1);
        OutputUtilities.javaIndent(valuesClause, 1);
        insertClause.append("\"insert into ");
        insertClause.append(StringUtility.escapeStringForJava(this.introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        insertClause.append(" (");
        valuesClause.append("\"values (");
        List<String> valuesClauses = new ArrayList();
        Iterator<IntrospectedColumn> iter = ListUtilities.removeIdentityAndGeneratedAlwaysColumns(this.introspectedTable.getAllColumns()).iterator();
        boolean hasFields = false;
        while(iter.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();
            insertClause.append(StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn)));
            valuesClause.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            hasFields = true;
            if (iter.hasNext()) {
                insertClause.append(", ");
                valuesClause.append(", ");
            }
            if (valuesClause.length() > 60) {
                if (!iter.hasNext()) {
                    insertClause.append(')');
                    valuesClause.append(')');
                }
                insertClause.append("\",");
                valuesClause.append('"');
                if (iter.hasNext()) {
                    valuesClause.append(',');
                }
                method.addAnnotation(insertClause.toString());
                insertClause.setLength(0);
                OutputUtilities.javaIndent(insertClause, 1);
                insertClause.append('"');
                valuesClauses.add(valuesClause.toString());
                valuesClause.setLength(0);
                OutputUtilities.javaIndent(valuesClause, 1);
                valuesClause.append('"');
                hasFields = false;
            }
        }
        if (hasFields) {
            insertClause.append(")\",");
            method.addAnnotation(insertClause.toString());
            valuesClause.append(")\"");
            valuesClauses.add(valuesClause.toString());
        }
        Iterator var9 = valuesClauses.iterator();
        while(var9.hasNext()) {
            String clause = (String)var9.next();
            method.addAnnotation(clause);
        }
        method.addAnnotation("})");
        GeneratedKey gk = this.introspectedTable.getGeneratedKey();
        if (gk != null) {
            this.addGeneratedKeyAnnotation(method, gk);
        }
    }

    public void addExtraImports(Interface interfaze) {
        GeneratedKey gk = this.introspectedTable.getGeneratedKey();
        if (gk != null) {
            this.addGeneratedKeyImports(interfaze, gk);
        }
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Insert"));
    }
}
