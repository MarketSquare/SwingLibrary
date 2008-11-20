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


package org.robotframework.swing.keyword.internalframe;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.DefaultContextVerifier;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.internalframe.InternalFrameOperatorFactory;
import org.robotframework.swing.internalframe.DefaultInternalFrameOperator;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class InternalFrameKeywords {
    private IdentifierParsingOperatorFactory<DefaultInternalFrameOperator> operatorFactory = new InternalFrameOperatorFactory();
    private IComponentConditionResolver existenceResolver = new ComponentExistenceResolver(operatorFactory);
    private IContextVerifier contextVerifier = new DefaultContextVerifier();
    
    @RobotKeyword("Uses current context to search for an internal frame and closes it.\n\n"
        + "Example:\n"
        + "| Close Internal Frame  | _My Internal Frame_ |\n")
    public void closeInternalFrame(String identifier) {
        createOperator(identifier).close();
    }
    
    @RobotKeyword("Fails if the internal frame doesn't exist in the current context.\n\n"
        + "Example:\n"
        + "| Internal Frame Should Exist | _My Internal Frame_ |\n")
    public void internalFrameShouldExist(String identifier) {
        contextVerifier.verifyContext();
        Assert.assertTrue("Internal frame '" + identifier + "' doesn't exist.", existenceResolver.satisfiesCondition(identifier));
    }
    
    @RobotKeyword("Fails if the internal frame exists in the current context.\n\n"
        + "Example:\n"
        + "| Internal Frame Should Not Exist | _My Internal Frame_ |\n")
    public void internalFrameShouldNotExist(String identifier) {
        contextVerifier.verifyContext();
        Assert.assertFalse("Internal frame '" + identifier + "' exists.", existenceResolver.satisfiesCondition(identifier));
    }
    
    @RobotKeyword("Fails if the internal frame is *not* open.\n\n"
        + "Example:\n"
        + "| Internal Frame Should Be Open | _My Internal Frame_ |\n")
    public void internalFrameShouldBeOpen(String identifier) {
        Assert.assertTrue("Internal frame '" + identifier + "' is not open.", createOperator(identifier).isVisible());
    }
    
    @RobotKeyword("Fails if the internal frame *is* open.\n\n"
        + "Example:\n"
        + "| Internal Frame Should Not Be Open | _My Internal Frame_ |\n")
    public void internalFrameShouldNotBeOpen(String identifier) {
        Assert.assertFalse("Internal frame '" + identifier + "' is open.", createOperator(identifier).isVisible());
    }
    
    private DefaultInternalFrameOperator createOperator(String identifier) {
        contextVerifier.verifyContext();
        return operatorFactory.createOperator(identifier);
    }
}
