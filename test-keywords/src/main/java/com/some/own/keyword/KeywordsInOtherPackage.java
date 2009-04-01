package com.some.own.keyword;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;


@RobotKeywords
public class KeywordsInOtherPackage {
    @RobotKeyword
    @ArgumentNames({"arg"})
    public String keywordInOtherPackage(String arg) {
        return arg;
    }
    
    @RobotKeyword
    public String keywordwithoutArgumentNames(String arg) {
        return arg;
    }
}
