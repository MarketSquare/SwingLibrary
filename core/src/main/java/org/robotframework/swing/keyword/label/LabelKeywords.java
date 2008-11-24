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

package org.robotframework.swing.keyword.label;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.operator.label.DefaultLabelOperator;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class LabelKeywords {
    private IdentifierParsingOperatorFactory<DefaultLabelOperator> operatorFactory = new LabelOperatorFactory();
    private IComponentConditionResolver labelExistenceResolver = new ComponentExistenceResolver(operatorFactory);

    @RobotKeyword("Returns the text displayed on a label.\n"
        + "Assumes that the label exists in the current context.\n\n"
        + "Example:\n"
        + "| ${labelText}=   | Get Label Content | _addressLabel_    |\n"
        + "| Should Be Equal | _Address:_        | _${labelText}_ |\n")
    public String getLabelContent(String identifier) {
        return operatorFactory.createOperator(identifier).getText();
    }

    @RobotKeyword("Fails if label does not exist within current context.\n\n"
        + "Example:\n"
        + "| Label Should Exist | _myLabel_ |\n")
    public void labelShouldExist(String identifier) {
        Assert.assertTrue("Label '" + identifier + "' doesn't exist", labelExistenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Fails if label exists within current context.\n"
        + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`.\n\n"
        + "Example:\n"
        + "| Set Jemmy Timeouts     | _1_ |\n"
        + "| Label Should Not Exist | _myLabel_ |\n")
    public void labelShouldNotExist(String identifier) {
        Assert.assertFalse("Label '" + identifier + "' exists", labelExistenceResolver.satisfiesCondition(identifier));
    }
}
