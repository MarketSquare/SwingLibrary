package org.robotframework.swing.keyword.testapp;

import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class SomeApplication {
    private static String[] args;
    private static boolean wasCalled = false;

    public static String[] getLastUsedArguments() {
        return args;
    }

    public static void main(String[] args) {
        wasCalled = true;
        SomeApplication.args = args;
    }

    @RobotKeyword
    public void assertApplicationWasCalled() {
        Assert.assertTrue(true);
        if (!wasCalled) {
            throw new AssertionFailedError("Application was not called");
        }
    }

    @RobotKeyword
    public String[] getReceivedArguments() {
        return args;
    }
}
