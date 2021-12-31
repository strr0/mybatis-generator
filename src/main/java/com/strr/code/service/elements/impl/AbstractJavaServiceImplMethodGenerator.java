package com.strr.code.service.elements.impl;

import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.codegen.AbstractGenerator;

public abstract class AbstractJavaServiceImplMethodGenerator extends AbstractGenerator {
    public abstract void addClassElements(TopLevelClass topLevelClass);
}
