package com.strr.code;

import com.strr.code.config.CustomGenerator;

public class Application {
    public static void main(String[] args) {
        CustomGenerator generator = new CustomGenerator();
        generator.initialize();
        generator.generate();
    }
}
