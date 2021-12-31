package com.strr.code.controller;

import com.strr.code.config.CustomIntrospectedTable;
import com.strr.code.common.EntityUtil;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import org.mybatis.generator.internal.util.messages.Messages;

import java.util.ArrayList;
import java.util.List;

public class JavaControllerResultGenerator extends AbstractJavaGenerator {
    public JavaControllerResultGenerator(String project) {
        super(project);
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        String result = ((CustomIntrospectedTable) this.introspectedTable).getControllerResult();
        this.progressCallback.startTask(Messages.getString("Progress.result", this.introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = this.context.getCommentGenerator();
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(result);
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        topLevelClass.addTypeParameter(new TypeParameter("T"));
        commentGenerator.addJavaFileComment(topLevelClass);
        this.addBasic(topLevelClass);
        this.addGetSuccess(topLevelClass);
        this.addSetSuccess(topLevelClass);
        this.addGetMsg(topLevelClass);
        this.addSetMsg(topLevelClass);
        this.addGetData(topLevelClass);
        this.addSetData(topLevelClass);
        this.addSuccess(topLevelClass, result);
        this.addMsg(topLevelClass, result);
        this.addData(topLevelClass, result);
        this.addError(topLevelClass);
        this.addOk(topLevelClass);
        this.addOkWithData(topLevelClass);
        List<CompilationUnit> answer = new ArrayList();
        answer.add(topLevelClass);
        return answer;
    }

    protected void addBasic(TopLevelClass topLevelClass) {
        Field success = new Field("success", new FullyQualifiedJavaType("Boolean"));
        success.setVisibility(JavaVisibility.PRIVATE);
        Field msg = new Field("msg", new FullyQualifiedJavaType("String"));
        msg.setVisibility(JavaVisibility.PRIVATE);
        Field data = new Field("data", new FullyQualifiedJavaType("T"));
        data.setVisibility(JavaVisibility.PRIVATE);
        topLevelClass.addField(success);
        topLevelClass.addField(msg);
        topLevelClass.addField(data);
    }

    protected void addGetSuccess(TopLevelClass topLevelClass) {
        Method getSuccess = new Method("getSuccess");
        getSuccess.setVisibility(JavaVisibility.PUBLIC);
        getSuccess.setReturnType(new FullyQualifiedJavaType("Boolean"));
        getSuccess.addBodyLine("return success;");
        topLevelClass.addMethod(getSuccess);
    }

    protected void addSetSuccess(TopLevelClass topLevelClass) {
        Method setSuccess = new Method("setSuccess");
        setSuccess.setVisibility(JavaVisibility.PUBLIC);
        setSuccess.addParameter(new Parameter(new FullyQualifiedJavaType("Boolean"), "success"));
        setSuccess.addBodyLine("this.success = success;");
        topLevelClass.addMethod(setSuccess);
    }

    protected void addGetMsg(TopLevelClass topLevelClass) {
        Method getMsg = new Method("getMsg");
        getMsg.setVisibility(JavaVisibility.PUBLIC);
        getMsg.setReturnType(new FullyQualifiedJavaType("String"));
        getMsg.addBodyLine("return msg;");
        topLevelClass.addMethod(getMsg);
    }

    protected void addSetMsg(TopLevelClass topLevelClass) {
        Method setMsg = new Method("setMsg");
        setMsg.setVisibility(JavaVisibility.PUBLIC);
        setMsg.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "msg"));
        setMsg.addBodyLine("this.msg = msg;");
        topLevelClass.addMethod(setMsg);
    }

    protected void addGetData(TopLevelClass topLevelClass) {
        Method getData = new Method("getData");
        getData.setVisibility(JavaVisibility.PUBLIC);
        getData.setReturnType(new FullyQualifiedJavaType("T"));
        getData.addBodyLine("return data;");
        topLevelClass.addMethod(getData);
    }

    protected void addSetData(TopLevelClass topLevelClass) {
        Method setData = new Method("setData");
        setData.setVisibility(JavaVisibility.PUBLIC);
        setData.addParameter(new Parameter(new FullyQualifiedJavaType("T"), "data"));
        setData.addBodyLine("this.data = data;");
        topLevelClass.addMethod(setData);
    }

    protected void addSuccess(TopLevelClass topLevelClass, String type) {
        Method success = new Method("success");
        success.setVisibility(JavaVisibility.PUBLIC);
        success.addParameter(new Parameter(new FullyQualifiedJavaType("Boolean"), "success"));
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(type);
        returnType.addTypeArgument(new FullyQualifiedJavaType("T"));
        success.setReturnType(returnType);
        success.addBodyLine("this.success = success;");
        success.addBodyLine("return this;");
        topLevelClass.addMethod(success);
    }

    protected void addMsg(TopLevelClass topLevelClass, String type) {
        Method msg = new Method("msg");
        msg.setVisibility(JavaVisibility.PUBLIC);
        msg.addParameter(new Parameter(new FullyQualifiedJavaType("String"), "msg"));
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(type);
        returnType.addTypeArgument(new FullyQualifiedJavaType("T"));
        msg.setReturnType(returnType);
        msg.addBodyLine("this.msg = msg;");
        msg.addBodyLine("return this;");
        topLevelClass.addMethod(msg);
    }

    protected void addData(TopLevelClass topLevelClass, String type) {
        Method data = new Method("data");
        data.setVisibility(JavaVisibility.PUBLIC);
        data.addParameter(new Parameter(new FullyQualifiedJavaType("T"), "data"));
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType(type);
        returnType.addTypeArgument(new FullyQualifiedJavaType("T"));
        data.setReturnType(returnType);
        data.addBodyLine("this.data = data;");
        data.addBodyLine("return this;");
        topLevelClass.addMethod(data);
    }

    protected void addError(TopLevelClass topLevelClass) {
        Method error = new Method("error");
        error.setVisibility(JavaVisibility.PUBLIC);
        error.setStatic(true);
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("Result");
        EntityUtil.setValue(returnType, "baseShortName", "<T> Result<T>");
        error.setReturnType(returnType);
        error.addBodyLine("return new Result<T>().success(false);");
        topLevelClass.addMethod(error);
    }

    protected void addOk(TopLevelClass topLevelClass) {
        Method ok = new Method("ok");
        ok.setVisibility(JavaVisibility.PUBLIC);
        ok.setStatic(true);
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("Result");
        EntityUtil.setValue(returnType, "baseShortName", "<T> Result<T>");
        ok.setReturnType(returnType);
        ok.addBodyLine("return new Result<T>().success(true);");
        topLevelClass.addMethod(ok);
    }

    protected void addOkWithData(TopLevelClass topLevelClass) {
        Method ok = new Method("ok");
        ok.setVisibility(JavaVisibility.PUBLIC);
        ok.setStatic(true);
        ok.addParameter(new Parameter(new FullyQualifiedJavaType("T"), "data"));
        FullyQualifiedJavaType returnType = new FullyQualifiedJavaType("Result");
        EntityUtil.setValue(returnType, "baseShortName", "<T> Result<T>");
        ok.setReturnType(returnType);
        ok.addBodyLine("return new Result<T>().success(true).data(data);");
        topLevelClass.addMethod(ok);
    }
}
