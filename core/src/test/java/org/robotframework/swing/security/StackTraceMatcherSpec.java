package org.robotframework.swing.security;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class StackTraceMatcherSpec extends Specification<StackTraceMatcher> {
    private String className = "java.lang.Runtime";
    private String methodName = "exit";
    private StackTraceElement traceElement = new StackTraceElement(className, methodName, null, 0);
    
    public class WithMatchingStackTrace {
        public StackTraceMatcher create() {
            return new StackTraceMatcher(className, methodName) {
                StackTraceElement[] getStackTrace() {
                    return new StackTraceElement[] { traceElement }; 
                }
            };
        }
        
        public void returnsTrue() {
            specify(context.checkCalled());
        }
    }
    
    public class WithNonMatchingStackTrace {
        public StackTraceMatcher create() {
            return new StackTraceMatcher(className, methodName);
        }
        
        public void returnsFalse() {
            specify(!context.checkCalled());
        }
    }
}
