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

package org.robotframework.swing.keyword.togglebutton;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.button.AbstractButtonOperator;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.togglebutton.ToggleButtonOperatorFactory;

@RobotKeywords
public class ToggleButtonKeywords {
    private OperatorFactory<AbstractButtonOperator> operatorFactory = new ToggleButtonOperatorFactory();
    
    @RobotKeyword("Fails if togglebutton is not selected.\n\n"
        + "Example:\n"
        + "| Toggle Button Should Be Selected | _My Toggle Button_ |\n")
    public void toggleButtonShouldBeSelected(String identifier) {
        boolean isSelected = createOperator(identifier).isSelected();
        Assert.assertTrue("Toggle Button '" + identifier + "' is not selected.", isSelected);
    }
    
    @RobotKeyword("Fails if togglebutton is selected.\n\n"
        + "Example:\n"
        + "| Toggle Button Should Not Be Selected | _My Toggle Button_ |\n")
    public void toggleButtonShouldNotBeSelected(String identifier) {
        boolean isSelected = createOperator(identifier).isSelected();
        Assert.assertFalse("Toggle Button '" + identifier + "' is selected.", isSelected);
    }
    
    @RobotKeyword("Uses current context to search for a button and when found, pushes it.\n\n"
        + "Example:\n"
        + "| Push Toggle Button | _Activated_ |\n")
    public void pushToggleButton(String identifier) {
        createOperator(identifier).push();
    }

    private AbstractButtonOperator createOperator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }
}
