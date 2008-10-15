/*
 * Copyright 2008 Nokia Siemens Networks Oyj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.robotframework.swing.security;

public class StackTraceMatcher {
    private final String className;
    private final String methodName;
    
    public StackTraceMatcher(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    public boolean checkCalled() {
        StackTraceElement[] stackTrace = getStackTrace();
        for (StackTraceElement traceElement : stackTrace) {
            if (traceElementMatches(traceElement))
                return true;
        }
        return false;
    }

    StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    private boolean traceElementMatches(StackTraceElement traceElement) {
        return className.equals(traceElement.getClassName()) && methodName.equals(traceElement.getMethodName());
    }
}
