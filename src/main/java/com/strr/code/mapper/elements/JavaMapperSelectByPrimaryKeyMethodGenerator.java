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

public class JavaMapperSelectByPrimaryKeyMethodGenerator extends AbstractJavaMapperMethodGenerator {
    @Override
    public void addInterfaceElements(Interface interfaze) {
        Method method = new Method(this.introspectedTable.getSelectByPrimaryKeyStatementId());
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        FullyQualifiedJavaType returnType = this.introspectedTable.getRules().calculateAllFieldsClass();
        method.setReturnType(returnType);
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        importedTypes.add(returnType);
        if (this.introspectedTable.getRules().generatePrimaryKeyClass()) {
            FullyQualifiedJavaType type = new FullyQualifiedJavaType(this.introspectedTable.getPrimaryKeyType());
            importedTypes.add(type);
            method.addParameter(new Parameter(type, "key"));
        } else {
            List<IntrospectedColumn> introspectedColumns = this.introspectedTable.getPrimaryKeyColumns();
            boolean annotate = introspectedColumns.size() > 1;
            if (annotate) {
                importedTypes.add(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
            }
            StringBuilder sb = new StringBuilder();
            Parameter parameter;
            for(Iterator var8 = introspectedColumns.iterator(); var8.hasNext(); method.addParameter(parameter)) {
                IntrospectedColumn introspectedColumn = (IntrospectedColumn)var8.next();
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
        this.addMapperAnnotations(interfaze, method);
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        if (this.context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(method, interfaze, this.introspectedTable)) {
            this.addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Interface interfaze, Method method) {
        StringBuilder sb = new StringBuilder();
        method.addAnnotation("@Select({");
        OutputUtilities.javaIndent(sb, 1);
        sb.append("\"select\",");
        method.addAnnotation(sb.toString());
        sb.setLength(0);
        OutputUtilities.javaIndent(sb, 1);
        sb.append('"');
        boolean hasColumns = false;
        Iterator iter = this.introspectedTable.getAllColumns().iterator();

        while(iter.hasNext()) {
            sb.append(StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getSelectListPhrase((IntrospectedColumn)iter.next())));
            hasColumns = true;
            if (iter.hasNext()) {
                sb.append(", ");
            }

            if (sb.length() > 80) {
                sb.append("\",");
                method.addAnnotation(sb.toString());
                sb.setLength(0);
                OutputUtilities.javaIndent(sb, 1);
                sb.append('"');
                hasColumns = false;
            }
        }

        if (hasColumns) {
            sb.append("\",");
            method.addAnnotation(sb.toString());
        }

        sb.setLength(0);
        OutputUtilities.javaIndent(sb, 1);
        sb.append("\"from ");
        sb.append(StringUtility.escapeStringForJava(this.introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime()));
        sb.append("\",");
        method.addAnnotation(sb.toString());
        boolean and = false;

        for(iter = this.introspectedTable.getPrimaryKeyColumns().iterator(); iter.hasNext(); method.addAnnotation(sb.toString())) {
            sb.setLength(0);
            OutputUtilities.javaIndent(sb, 1);
            if (and) {
                sb.append("  \"and ");
            } else {
                sb.append("\"where ");
                and = true;
            }

            IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();
            sb.append(StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getAliasedEscapedColumnName(introspectedColumn)));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            sb.append('"');
            if (iter.hasNext()) {
                sb.append(',');
            }
        }

        method.addAnnotation("})");
        if (false) {
            if (!this.introspectedTable.getRules().generateBaseResultMap() && !this.introspectedTable.getRules().generateResultMapWithBLOBs()) {
                this.addAnnotatedResults(interfaze, method);
            } else {
                this.addResultMapAnnotation(method);
            }
        } else {
            this.addAnnotatedResults(interfaze, method);
        }
    }

    private void addResultMapAnnotation(Method method) {
        String annotation = String.format("@ResultMap(\"%s.%s\")", this.introspectedTable.getMyBatis3SqlMapNamespace(), this.introspectedTable.getRules().generateResultMapWithBLOBs() ? this.introspectedTable.getResultMapWithBLOBsId() : this.introspectedTable.getBaseResultMapId());
        method.addAnnotation(annotation);
    }

    private void addAnnotatedResults(Interface interfaze, Method method) {
        if (this.introspectedTable.isConstructorBased()) {
            method.addAnnotation("@ConstructorArgs({");
        } else {
            method.addAnnotation("@Results({");
        }
        StringBuilder sb = new StringBuilder();
        Iterator<IntrospectedColumn> iterPk = this.introspectedTable.getPrimaryKeyColumns().iterator();
        Iterator iterNonPk;
        IntrospectedColumn introspectedColumn;
        for(iterNonPk = this.introspectedTable.getNonPrimaryKeyColumns().iterator(); iterPk.hasNext(); method.addAnnotation(sb.toString())) {
            introspectedColumn = (IntrospectedColumn)iterPk.next();
            sb.setLength(0);
            OutputUtilities.javaIndent(sb, 1);
            sb.append(this.getResultAnnotation(interfaze, introspectedColumn, true, this.introspectedTable.isConstructorBased()));
            if (iterPk.hasNext() || iterNonPk.hasNext()) {
                sb.append(',');
            }
        }
        for(; iterNonPk.hasNext(); method.addAnnotation(sb.toString())) {
            introspectedColumn = (IntrospectedColumn)iterNonPk.next();
            sb.setLength(0);
            OutputUtilities.javaIndent(sb, 1);
            sb.append(this.getResultAnnotation(interfaze, introspectedColumn, false, this.introspectedTable.isConstructorBased()));
            if (iterNonPk.hasNext()) {
                sb.append(',');
            }
        }
        method.addAnnotation("})");
    }

    public void addExtraImports(Interface interfaze) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Select"));
        if (false) {
            if (!this.introspectedTable.getRules().generateBaseResultMap() && !this.introspectedTable.getRules().generateResultMapWithBLOBs()) {
                this.addAnnotationImports(interfaze);
            } else {
                interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.ResultMap"));
            }
        } else {
            this.addAnnotationImports(interfaze);
        }
    }

    private void addAnnotationImports(Interface interfaze) {
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.type.JdbcType"));
        if (this.introspectedTable.isConstructorBased()) {
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Arg"));
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.ConstructorArgs"));
        } else {
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Result"));
            interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Results"));
        }

    }
}
