package com.strr.code.mapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.codegen.AbstractGenerator;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.config.GeneratedKey;
import org.mybatis.generator.internal.util.StringUtility;

public abstract class AbstractJavaMapperMethodGenerator extends AbstractGenerator {
    public abstract void addInterfaceElements(Interface interfaze);

    protected String getResultAnnotation(Interface interfaze, IntrospectedColumn introspectedColumn, boolean idColumn, boolean constructorBased) {
        StringBuilder sb = new StringBuilder();
        if (constructorBased) {
            interfaze.addImportedType(introspectedColumn.getFullyQualifiedJavaType());
            sb.append("@Arg(column=\"");
            sb.append(MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn));
            sb.append("\", javaType=");
            sb.append(introspectedColumn.getFullyQualifiedJavaType().getShortName());
            sb.append(".class");
        } else {
            sb.append("@Result(column=\"");
            sb.append(MyBatis3FormattingUtilities.getRenamedColumnNameForResultMap(introspectedColumn));
            sb.append("\", property=\"");
            sb.append(introspectedColumn.getJavaProperty());
            sb.append('"');
        }
        if (StringUtility.stringHasValue(introspectedColumn.getTypeHandler())) {
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(introspectedColumn.getTypeHandler());
            interfaze.addImportedType(fqjt);
            sb.append(", typeHandler=");
            sb.append(fqjt.getShortName());
            sb.append(".class");
        }
        sb.append(", jdbcType=JdbcType.");
        sb.append(introspectedColumn.getJdbcTypeName());
        if (idColumn) {
            sb.append(", id=true");
        }
        sb.append(')');
        return sb.toString();
    }

    protected void addGeneratedKeyAnnotation(Method method, GeneratedKey gk) {
        StringBuilder sb = new StringBuilder();
        this.introspectedTable.getColumn(gk.getColumn()).ifPresent((introspectedColumn) -> {
            if (gk.isJdbcStandard()) {
                sb.append("@Options(useGeneratedKeys=true,keyProperty=\"");
                sb.append(introspectedColumn.getJavaProperty());
                sb.append("\")");
                method.addAnnotation(sb.toString());
            } else {
                FullyQualifiedJavaType fqjt = introspectedColumn.getFullyQualifiedJavaType();
                sb.append("@SelectKey(statement=\"");
                sb.append(gk.getRuntimeSqlStatement());
                sb.append("\", keyProperty=\"");
                sb.append(introspectedColumn.getJavaProperty());
                sb.append("\", before=");
                sb.append(gk.isIdentity() ? "false" : "true");
                sb.append(", resultType=");
                sb.append(fqjt.getShortName());
                sb.append(".class)");
                method.addAnnotation(sb.toString());
            }
        });
    }

    protected void addGeneratedKeyImports(Interface interfaze, GeneratedKey gk) {
        this.introspectedTable.getColumn(gk.getColumn()).ifPresent((introspectedColumn) -> {
            if (gk.isJdbcStandard()) {
                interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Options"));
            } else {
                interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.SelectKey"));
                FullyQualifiedJavaType fqjt = introspectedColumn.getFullyQualifiedJavaType();
                interfaze.addImportedType(fqjt);
            }
        });
    }
}
