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

package org.robotframework.swing.keyword.radiobutton;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.button.AbstractButtonOperator;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.radiobutton.RadioButtonOperatorFactory;

@RobotKeywords
public class RadioButtonKeywords {
    private OperatorFactory<AbstractButtonOperator> operatorFactory = new RadioButtonOperatorFactory();
    
    @RobotKeyword("Uses current context to search for a radiobutton and when found, pushes it.\n\n"
        + "Example:\n"
        + "| Select Radio Button | _My Radio Button_ |\n")
    public void pushRadioButton(String identifier) {
        createOperator(identifier).push();
    }
    
    @RobotKeyword("*DEPRECATED* Use keyword `Push Radio Button` instead.\n")
    public void selectRadioButton(String identifier) {
        pushRadioButton(identifier);
    }
    
    @RobotKeyword("Fails if radiobutton is not selected.\n\n"
        + "Example:\n"
        + "| Radio Button Should Be Selected | _My Radio Button_ |\n")
    public void radioButtonShouldBeSelected(String identifier) {
        boolean isSelected = createOperator(identifier).isSelected();
        Assert.assertTrue("Radio Button '" + identifier + "' is not selected.", isSelected);
    }
    
    @RobotKeyword("Fails if radiobutton is selected.\n\n"
        + "Example:\n"
        + "| Radio Button Should Not Be Selected | _My Radio Button_ |\n")
    public void radioButtonShouldNotBeSelected(String identifier) {
        boolean isSelected = createOperator(identifier).isSelected();
        Assert.assertFalse("Radio Button '" + identifier + "' is selected.", isSelected);
    }
    
    @RobotKeyword("Fails if radiobutton is disabled.\n\n"
        + "Example:\n"
        + "| Radio Button Should Be Enabled | _My Radio Button_ |\n")
    public void radioButtonShouldBeEnabled(String identifier) {
        boolean isEnabled = createOperator(identifier).isEnabled();
        Assert.assertTrue("Radio Button '" + identifier + "' is disabled.", isEnabled);
    }
    
    @RobotKeyword("Fails if radiobutton is enabled.\n\n"
        + "Example:\n"
        + "| Radio Button Should Be Disabled | _My Radio Button_ |\n")
    public void radioButtonShouldBeDisabled(String identifier) {
        boolean isEnabled = createOperator(identifier).isEnabled();
        Assert.assertFalse("Radio Button '" + identifier + "' is enabled.", isEnabled);
    }
    
    private AbstractButtonOperator createOperator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }
}
