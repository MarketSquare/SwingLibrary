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

package org.robotframework.swing.context;

import junit.framework.Assert;

/**
 * @author Heikki Hulkko
 */
public abstract class ContextVerifier implements IContextVerifier {
    private final String errorMessage;

    public ContextVerifier(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void verifyContext() {
        Class[] expectedClasses = getExpectedClasses();
        for (Class expectedClass : expectedClasses) {
            if (expectedClass.isAssignableFrom(contextClass())) {
                return;
            }
        }
        Assert.fail(errorMessage + " Current context is " + contextClass().getName());
    }

    protected abstract Class[] getExpectedClasses();
    
    private Class contextClass() {
        return Context.getContext().getSource().getClass();
    }
}
