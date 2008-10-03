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

import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.DefaultContextVerifier;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.factory.OperatorFactory;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class CheckBoxKeywords {
    private OperatorFactory<JCheckBoxOperator> operatorFactory = new CheckBoxOperatorFactory();
    private IContextVerifier contextVerifier = new DefaultContextVerifier();

    @RobotKeyword("Uses current context to search for a checkbox and when found, checks it.\n\n"
        + "Example:\n"
        + "| Check Checkbox | _My Checkbox_ |\n")
    public void checkCheckBox(String identifier) {
        contextVerifier.verifyContext();
        operatorFactory.createOperator(identifier).changeSelection(true);
    }

    @RobotKeyword("Uses current context to search for a checkbox and when found, unchecks it.\n\n"
        + "Example:\n"
        + "| Uncheck Checkbox | _My Checkbox_ |\n")
    public void uncheckCheckBox(String identifier) {
        contextVerifier.verifyContext();
        operatorFactory.createOperator(identifier).changeSelection(false);
    }

    @RobotKeyword("Fails if checkbox is not checked.\n\n"
        + "Example:\n"
        + "| Check Box Should Be Checked | _My Checkbox_ |\n")
    public void checkBoxShouldBeChecked(String identifier) {
        contextVerifier.verifyContext();
        Assert.assertTrue(operatorFactory.createOperator(identifier).isSelected());
    }

    @RobotKeyword("Fails if checkbox is checked.\n\n"
        + "Example:\n"
        + "| Check Box Should Be Un Checked | _My Checkbox_ |\n")
    public void checkBoxShouldBeUnChecked(String identifier) {
        contextVerifier.verifyContext();
        Assert.assertFalse(operatorFactory.createOperator(identifier).isSelected());
    }

    @RobotKeyword("Alias for `Check Box Should Be Un Checked` keyword.\n")
    public void checkBoxShouldNotBeChecked(String identifier) {
        checkBoxShouldBeUnChecked(identifier);
    }
}
