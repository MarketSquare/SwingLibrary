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

package org.robotframework.swing.keyword.button;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.DefaultContextVerifier;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.operator.button.DefaultButtonOperator;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class ButtonKeywords {
    private IdentifierParsingOperatorFactory<DefaultButtonOperator> operatorFactory = new ButtonOperatorFactory();
    private IComponentConditionResolver buttonExistenceResolver = new ComponentExistenceResolver(operatorFactory);
    private IContextVerifier contextVerifier = new DefaultContextVerifier();

    @RobotKeyword("Uses current context to search for a button and when found, pushes it.\n\n"
        + "Example:\n"
        + "| Push Button | _OK_ |\n")
    public void pushButton(String identifier) {
        createOperator(identifier).push();
    }

    @RobotKeyword("Uses current context to search for a button and when found, "
        + "returns the text it is labeled with.\n\n"
        + "Example:\n"
        + "| ${buttonText}=  | Get Button Text | _myButton_    |\n"
        + "| Should Be Equal | _My Button_     | _${buttonText}_ |\n")
    public String getButtonText(String identifier) {
        return createOperator(identifier).getText();
    }

    @RobotKeyword("Fails if button does not exist within current context.\n\n"
        + "Example:\n"
        + "| Button Should Exist | _OK_ |\n")
    public void buttonShouldExist(String identifier) {
        Assert.assertTrue("Button '" + identifier + "' doesn't exist", buttonExists(identifier));
    }

    @RobotKeyword("Fails if button exists within current context.\n"
        + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`.\n\n"
        + "Example:\n"
        + "| Set Jemmy Timeouts      | _1_  |\n"
        + "| Button Should Not Exist | _OK_ |\n")
    public void buttonShouldNotExist(String identifier) {
        Assert.assertFalse("Button '" + identifier + "' exists", buttonExists(identifier));
    }

    @RobotKeyword("Fails if button is disabled\n\n"
        + "Example:\n"
        + "| Button Should Be Enabled | _OK_ |\n")
    public void buttonShouldBeEnabled(String identifier) {
        Assert.assertTrue("Button was disabled.", createOperator(identifier).isEnabled());
    }

    @RobotKeyword("Fails if button is enabled\n\n"
        + "Example:\n"
        + "| Button Should Be Disabled | _OK_ |\n")
    public void buttonShouldBeDisabled(String identifier) {
        Assert.assertFalse("Button was enabled.", createOperator(identifier).isEnabled());
    }
    
    private DefaultButtonOperator createOperator(String identifier) {
        contextVerifier.verifyContext();
        return operatorFactory.createOperator(identifier);
    }
    
    private boolean buttonExists(String identifier) {
        contextVerifier.verifyContext();
        return buttonExistenceResolver.satisfiesCondition(identifier);
    }
}
