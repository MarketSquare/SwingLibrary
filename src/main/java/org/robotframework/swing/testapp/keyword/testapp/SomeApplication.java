package org.robotframework.swing.testapp.keyword.testapp;

import org.junit.Assert;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.testapp.keyword.testapp.SomeApplication;

@RobotKeywords
public class SomeApplication {
    public static String[] args;
    public static boolean wasCalled = false;

    public static String[] getLastUsedArguments() {
        return args;
    }

    public static void main(String[] args) {
        wasCalled = true;
        SomeApplication.args = args;
    }

    public void assertApplicationWasCalled() {
        Assert.assertTrue(wasCalled);
    }

    public String[] getReceivedArguments() {
        return args;
    }
}
