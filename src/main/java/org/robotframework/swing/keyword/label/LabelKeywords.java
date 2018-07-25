/*
 * Copyright 2008-2011 Nokia Siemens Networks Oyj
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

import org.junit.Assert;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.label.LabelOperator;
import org.robotframework.swing.label.LabelOperatorFactory;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;

@RobotKeywords
public class LabelKeywords {
    private IdentifierParsingOperatorFactory<LabelOperator> operatorFactory = new LabelOperatorFactory();
    private IComponentConditionResolver labelExistenceResolver = new ComponentExistenceResolver(operatorFactory);

    @RobotKeyword("Returns the text displayed on a label.\n"
        + "Assumes that the label exists in the current context.\n\n"
        + "Example:\n"
        + "| ${labelText}=     | `Get Label Content` | addressLabel |\n"
        + "| `Should Be Equal` | Address:            | ${labelText} |\n")
    @ArgumentNames({"identifier"})
    public String getLabelContent(String identifier) {
        return createOperator(identifier).getText();
    }

    @RobotKeyword("Fails if label does not exist within current context.\n\n"
        + "Example:\n"
        + "| `Label Should Exist` | myLabel |\n")
    @ArgumentNames({"identifier"})
    public void labelShouldExist(String identifier) {
        Assert.assertTrue("Label '" + identifier + "' doesn't exist", labelExistenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Fails if label exists within current context.\n"
        + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`.\n\n"
        + "Example:\n"
        + "| `Set Jemmy Timeouts`     | 1 |\n"
        + "| `Label Should Not Exist` | myLabel |\n")
    @ArgumentNames({"identifier"})
    public void labelShouldNotExist(String identifier) {
        Assert.assertFalse("Label '" + identifier + "' exists", labelExistenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Checks the equality of given text and the text displayed on a label.\n"
        + "Assumes that the label exists in the current context.\n\n"
        + "Example:\n"
        + "| `Label Text Should Be` | addressLabel | Address: |\n")
    @ArgumentNames({"identifier", "expected"})
    public void labelTextShouldBe(String identifier, String expected) {
        String actual = createOperator(identifier).getText();
        Assert.assertTrue("Expected label '" + identifier + "' value to be '" + expected + "', but was '" + actual + "'", expected.equals(actual));
    }

    private LabelOperator createOperator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }
}
