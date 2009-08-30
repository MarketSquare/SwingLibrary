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

package org.robotframework.swing.keyword.component;

import junit.framework.Assert;

import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.component.ComponentOperator;
import org.robotframework.swing.component.ComponentOperatorFactory;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;

@RobotKeywords
public class ComponentKeywords {
    private IdentifierParsingOperatorFactory<ComponentOperator> operatorFactory = new ComponentOperatorFactory();
    private IComponentConditionResolver componentExistenceResolver = new ComponentExistenceResolver(operatorFactory);

    @RobotKeyword("Fails if component exists within current context.\n"
        + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`\n\n"
        + "Example:\n"
        + "| Component Should Not Exist | _myPanel_ |\n")
    public void componentShouldNotExist(String identifier) {
        Assert.assertFalse("Component '" + identifier + "' exists", componentExistenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Fails if component does not exist within current context.\n"
        + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`\n\n"
        + "Example:\n"
        + "| Component Should Not Exist | _myPanel_ |\n")
    public void componentShouldExist(String identifier) {
        Assert.assertTrue("Component '" + identifier + "' does not exist", componentExistenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Clicks on a component.\n"
        + "The number of clicks can be given as second argument.\n\n"
        + "Example:\n"
        + "| Click On Component | _myComponent_ |   | |\n"
        + "| Click On Component | _myComponent_ | 2 | # double click |\n")
    @ArgumentNames({"identifier", "times=1"})
    public void clickOnComponent(String identifier, String[] times) {
        operator(identifier).clickMouse(getTimes(times));
    }
    
    @RobotKeyword("Returns the component's tooltip text.\n\n"
        + "Example:\n"
        + "| ${tooltip}= | Get Tooltip Text | _saveButton_ |\n"
        + "| Should Be Equal    | _Save_ | _${tooltip}_ |\n")
    public String getTooltipText(String identifier) {
        return operator(identifier).getToolTipText();
    }
    
    @RobotKeyword("Sets focus to the component.\n"
        + "Useful for example when sending keyboard events to a component.\n\n"
        + "Example:\n"
        + "| Set Focus To Component | _myTextField_ |           | |\n"
        + "| Send Keyboard Event    | VK_C          | CTRL_MASK | # paste from clipboard |\n")
    public void focusToComponent(String identifier) {
        operator(identifier).getFocus();
    }

    @RobotKeyword("Selects an item from the components context popup menu.\n"
        + "Does a right click on the component and selects the specified menu item from the popup menu.\n\n"
        + "Example:\n"
        + "| Select From Popup Menu | _myComponent_ | _Actions|Do something_ |\n")
    public void selectFromPopupMenu(String identifier, String menuPath) {
        JPopupMenuOperator popup = operator(identifier).invokePopup();
        popup.pushMenuNoBlock(menuPath, new EqualsStringComparator());
    }
    
    private ComponentOperator operator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }

    private int getTimes(String[] times) {
        return times.length == 1 ? Integer.parseInt(times[0]) : 1;
    }
}
