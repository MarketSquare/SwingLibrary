package org.robotframework.somepackage;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class KeywordsOutsidePathPattern {
    @RobotKeyword
    public void keywordThatShouldNotBeRegistered() { }
}
