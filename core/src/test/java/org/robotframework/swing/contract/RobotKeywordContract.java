package org.robotframework.swing.contract;

import java.lang.reflect.Method;

import jdave.ExpectationFailedException;
import jdave.IContract;

import org.robotframework.javalib.annotation.RobotKeyword;

public class RobotKeywordContract implements IContract {
    private final String methodName;

    public RobotKeywordContract(String methodName) {
        this.methodName = methodName;
    }

    public void isSatisfied(Object obj) throws ExpectationFailedException {
        if (!findMethod(obj).isAnnotationPresent(RobotKeyword.class)) {
            throw new ExpectationFailedException(methodName + " is not annotated with @RobotKeyword");
        }
    }

    private Method findMethod(Object obj) {
        for (Method method : obj.getClass().getMethods()) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new ExpectationFailedException(methodName + " could not be found from " + obj.getClass().getName());
    }
}
