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

import java.awt.Container;
import java.util.List;

import junit.framework.Assert;

import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.checkbox.CheckBoxListOperatorFactory;
import org.robotframework.swing.checkbox.CheckBoxOperator;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.ContextVerifier;
import org.robotframework.swing.context.DefaultContextVerifier;
import org.robotframework.swing.factory.OperatorListFactory;

@RobotKeywords
public class CheckBoxListKeywords {
    private ContextVerifier contextVerifier = new DefaultContextVerifier();
    private OperatorListFactory<CheckBoxOperator> operatorListFactory = new CheckBoxListOperatorFactory();

    @RobotKeyword("Checks all checkboxes in current context.\n\n"
        + "Example:\n"
        + "| Select Context | _My Checkbox Panel_ |\n"
        + "| Check All Checkboxes | |\n")
    public void checkAllCheckboxes() {
        foreachCheckBox(new CheckBoxAction() {
            public void operateOnCheckBox(JCheckBoxOperator operator) {
                operator.changeSelection(true);
            }
        });
    }

    @RobotKeyword("Unchecks all checkboxes in current context.\n\n"
        + "Example:\n"
        + "| Select Context | _My Checkbox Panel_ |\n"
        + "| Uncheck All Checkboxes | |\n")
    public void uncheckAllCheckboxes() {
        foreachCheckBox(new CheckBoxAction() {
            public void operateOnCheckBox(JCheckBoxOperator operator) {
                operator.changeSelection(false);
            }
        });
    }

    @RobotKeyword("Fails if any checkbox in current context is not checked.\n\n"
        + "Example:\n"
        + "| Select Context | _My Checkbox Panel_ |\n"
        + "| All Checkboxes Should Be Checked | |\n")
    public void allCheckboxesShouldBeChecked() {
        foreachCheckBox(new CheckBoxAction() {
            public void operateOnCheckBox(JCheckBoxOperator operator) {
                Assert.assertTrue("Checkbox '" + operator.getText() + "' is not checked.", operator.isSelected());
            }
        });
    }

    @RobotKeyword("Fails if any checkbox in current context is checked.\n\n"
        + "Example:\n"
        + "| Select Context | _My Checkbox Panel_ |\n"
        + "| All Checkboxes Should Be Unchecked | |\n")
    public void allCheckboxesShouldBeUnchecked() {
        foreachCheckBox(new CheckBoxAction() {
            public void operateOnCheckBox(JCheckBoxOperator operator) {
                Assert.assertFalse("Checkbox '" + operator.getText() + "' is checked.", operator.isSelected());
            }
        });
    }

    private List<CheckBoxOperator> createOperators() {
        contextVerifier.verifyContext();
        return operatorListFactory.createOperators((Container) Context.getContext().getSource());
    }

    private void foreachCheckBox(CheckBoxAction action) {
        List<CheckBoxOperator> operators = createOperators();
        for (CheckBoxOperator checkBoxOperator : operators) {
            action.operateOnCheckBox(checkBoxOperator);
        }
    }

    private interface CheckBoxAction {
        void operateOnCheckBox(JCheckBoxOperator operator);
    }
}
