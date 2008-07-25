package org.robotframework.swing.contract;

import jdave.ExpectationFailedException;
import jdave.IContract;

import org.robotframework.javalib.annotation.RobotKeywords;

public class RobotKeywordsContract implements IContract {
    public void isSatisfied(Object obj) throws ExpectationFailedException {
        Class<?> clss = obj.getClass();
        if (!clss.isAnnotationPresent(RobotKeywords.class)) {
            throw new ExpectationFailedException(clss.getName() + " is not annotated with @RobotKeywords");
        }
    }
}
