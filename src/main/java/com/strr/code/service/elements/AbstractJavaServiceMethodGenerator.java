package com.strr.code.service.elements;

import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.codegen.AbstractGenerator;

public abstract class AbstractJavaServiceMethodGenerator extends AbstractGenerator {
    public abstract void addInterfaceElements(Interface interfaze);
}
