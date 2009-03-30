package org.robotframework.swing.keyword.testing;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class ThreadTestingKeywords {
    private static Thread originalThread = Thread.currentThread();
    private static boolean keywordWasRun = false;
    
    @RobotKeyword
    public void shouldBeRunInSeparateThread() {
        keywordWasRun = true;
        Assert.assertNotSame(originalThread, Thread.currentThread());
    }
    
    @RobotKeyword
    public void keywordWasRun() {
        Assert.assertTrue("Keyword was not run", keywordWasRun);
    }
    
    @RobotKeyword
    public void resetKeywordWasRunCheck() {
        keywordWasRun = false;
    }
}
