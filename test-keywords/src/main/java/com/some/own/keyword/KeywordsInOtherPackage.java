package com.some.own.keyword;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;


@RobotKeywords
public class KeywordsInOtherPackage {
    @RobotKeyword
    public String keywordInOtherPackage(String arg) {
        return arg;
    }

}
