package com.strr.code.mapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.OutputUtilities;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.mybatis3.ListUtilities;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;
import org.mybatis.generator.internal.util.StringUtility;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class JavaMapperUpdateByPrimaryKeyWithoutBLOBsMethodGenerator extends AbstractJavaMapperMethodGenerator {
    @Override
    public void addInterfaceElements(Interface interfaze) {
        if (this.introspectedTable.getPrimaryKeyColumns().isEmpty()) {
            return;
        }
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet();
        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(this.introspectedTable.getBaseRecordType());
        importedTypes.add(parameterType);
        Method method = new Method("updateByPrimaryKey");
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setAbstract(true);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.addParameter(new Parameter(parameterType, "record"));
        this.context.getCommentGenerator().addGeneralMethodComment(method, this.introspectedTable);
        this.addMapperAnnotations(method);
        if (this.context.getPlugins().clientUpdateByPrimaryKeyWithoutBLOBsMethodGenerated(method, interfaze, this.introspectedTable)) {
            this.addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Method method) {
        method.addAnnotation("@Update({");
        StringBuilder sb = new StringBuilder();
        OutputUtilities.javaIndent(sb, 1);
        sb.append("\"update ");
        sb.append(StringUtility.escapeStringForJava(this.introspectedTable.getFullyQualifiedTableNameAtRuntime()));
        sb.append("\",");
        method.addAnnotation(sb.toString());
        sb.setLength(0);
        OutputUtilities.javaIndent(sb, 1);
        sb.append("\"set ");
        Iterator iter = ListUtilities.removeGeneratedAlwaysColumns(this.introspectedTable.getBaseColumns()).iterator();
        while(iter.hasNext()) {
            IntrospectedColumn introspectedColumn = (IntrospectedColumn)iter.next();
            sb.append(StringUtility.escapeStringForJava(MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn)));
            sb.append(" = ");
            sb.append(MyBatis3FormattingUtilities.getParameterClause(introspectedColumn));
            if (iter.hasNext()) {
                sb.append(',');
            }
            sb.append("\",");
            method.addAnnotation(sb.toString());
            if (iter.hasNext()) {
                sb.setLength(0);
                OutputUtilities.javaIndent(sb, 1);
                sb.append("  \"");
            }
        }
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
        interfaze.addImportedType(new FullyQualifiedJavaType("org.apache.ibatis.annotations.Update"));
    }
}
