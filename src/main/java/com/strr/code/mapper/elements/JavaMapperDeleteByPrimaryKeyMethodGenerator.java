package com.strr.code.mapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class JavaMapperDeleteByPrimaryKeyMethodGenerator extends AbstractJavaMapperMethodGenerator {
    @Override
    public void addInterfaceElements(Interface interfaze) {
        List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getPrimaryKeyColumns();
        if (introspectedColumns.isEmpty()) {
            return;
        }
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        Method method = new Method("deleteByPrimaryKey");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        if (this.introspectedTable.getRules().generatePrimaryKeyClass()) {
            FullyQualifiedJavaType type = new FullyQualifiedJavaType(this.introspectedTable.getPrimaryKeyType());
            importedTypes.add(type);
            method.addParameter(new Parameter(type, "key"));
        } else {
            boolean annotate = introspectedColumns.size() > 1;
            if (annotate) {
                importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
            }
            StringBuilder sb = new StringBuilder();
            Parameter parameter;
            for(Iterator var7 = introspectedColumns.iterator(); var7.hasNext(); method.addParameter(parameter)) {
                IntrospectedColumn introspectedColumn = (IntrospectedColumn)var7.next();
                FullyQualifiedJavaType type = introspectedColumn.getFullyQualifiedJavaType();
                importedTypes.add(type);
                parameter = new Parameter(type, introspectedColumn.getJavaProperty());
                if (annotate) {
                    sb.setLength(0);
                    sb.append("@Param(\"");
                    sb.append(introspectedColumn.getJavaProperty());
                    sb.append("\")");
                    parameter.addAnnotation(sb.toString());
                }
            }
        }
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        this.addMapperAnnotations(method);
        if (this.context.getPlugins().clientDeleteByPrimaryKeyMethodGenerated(method, interfaze, this.introspectedTable)) {
            this.addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Method method) {
        method.addAnnotation("@Delete({");
        StringBuilder sb = new StringBuilder();
        OutputUtilities.javaIndent(sb, 1);
        sb.append("\"delete from ");
        sb.append(StringUtility.escapeStringForJava(this.introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        sb.append("\",");
        method.addAnnotation(sb.toString());
        boolean and = false;
        for(Iterator iter = this.introspectedTable.getPrimaryKeyColumns().iterator(); iter.hasNext(); method.addAnnotation(sb.toString())) {
            sb.setLength(0);
            OutputUtilities.javaIndent(sb, 1);
            if (and) {
                sb.append("  \"and ");
            } else {
                sb.append("\"where ");
                and = true;
            }
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();
            sb.append(StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn)));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(',');
            }
        }
        method.addAnnotation("})");
    }

    public void addExtraImports(Interface interfaze) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Delete"));
    }
}
