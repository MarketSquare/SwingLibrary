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

package org.robotframework.swing.keyword.checkbox;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.checkbox.CheckBoxOperator;
import org.robotframework.swing.checkbox.CheckBoxOperatorFactory;
import org.robotframework.swing.factory.OperatorFactory;

@RobotKeywords
public class CheckBoxKeywords {
    private OperatorFactory<CheckBoxOperator> operatorFactory = new CheckBoxOperatorFactory();

    @RobotKeyword("Uses current context to search for a checkbox and when found, checks it.\n\n"
        + "Example:\n"
        + "| Check Checkbox | _My Checkbox_ |\n")
    public void checkCheckBox(String identifier) {
        createOperator(identifier).changeSelection(true);
    }

    @RobotKeyword("Uses current context to search for a checkbox and when found, unchecks it.\n\n"
        + "Example:\n"
        + "| Uncheck Checkbox | _My Checkbox_ |\n")
    public void uncheckCheckBox(String identifier) {
        createOperator(identifier).changeSelection(false);
    }

    @RobotKeyword("Fails if checkbox is not checked.\n\n"
        + "Example:\n"
        + "| Check Box Should Be Checked | _My Checkbox_ |\n")
    public void checkBoxShouldBeChecked(String identifier) {
        Assert.assertTrue(createOperator(identifier).isSelected());
    }

    @RobotKeyword("Fails if checkbox is checked.\n\n"
        + "Example:\n"
        + "| Check Box Should Be Unchecked | _My Checkbox_ |\n")
    public void checkBoxShouldBeUnchecked(String identifier) {
        Assert.assertFalse(createOperator(identifier).isSelected());
    }
    
    @RobotKeyword("Fails if checkbox is disabled.\n\n"
        + "Example:\n"
        + "| Check Box Should Be Enabled | _My Checkbox_ |\n")
    public void checkBoxShouldBeEnabled(String identifier) {
        Assert.assertTrue("Checkbox '" + identifier + "' is disabled.", createOperator(identifier).isEnabled());
    }

    @RobotKeyword("Fails if checkbox is enabled.\n\n"
        + "Example:\n"
        + "| Check Box Should Be Disabled | _My Checkbox_ |\n")
    public void checkBoxShouldBeDisabled(String identifier) {
        Assert.assertFalse("Checkbox '" + identifier + "' is enabled.", createOperator(identifier).isEnabled());
    }

    @RobotKeyword("Alias for `Check Box Should Be Unchecked` keyword.\n")
    public void checkBoxShouldNotBeChecked(String identifier) {
        checkBoxShouldBeUnchecked(identifier);
    }
    
    private CheckBoxOperator createOperator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }
}
