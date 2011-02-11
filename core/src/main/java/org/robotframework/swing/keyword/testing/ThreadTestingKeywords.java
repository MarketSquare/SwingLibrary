package org.robotframework.swing.keyword.testing;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class ThreadTestingKeywords {
    private static Thread originalThread = Thread.currentThread();
    private static boolean keywordWasRun = false;
    
    @RobotKeyword
    public Boolean shouldBeRunInSeparateThread() {
        keywordWasRun = true;
        Assert.assertNotSame(originalThread, Thread.currentThread());
        return Boolean.TRUE;
    }
    
    @RobotKeyword
    public void keywordWasRun() {
        Assert.assertTrue("Keyword was not run", keywordWasRun);
        keywordWasRun = false;
    }
}
