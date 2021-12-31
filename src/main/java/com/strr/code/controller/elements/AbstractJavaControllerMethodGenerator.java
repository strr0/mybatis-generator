package com.strr.code.controller.elements;

import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractGenerator;

public abstract class AbstractJavaControllerMethodGenerator extends AbstractGenerator {
    public abstract void addClassElements(TopLevelClass topLevelClass);
}
