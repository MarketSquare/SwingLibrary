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

package org.robotframework.swing.keyword.combobox;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.DefaultContextVerifier;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.operator.combobox.MyComboBoxOperator;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class ComboBoxKeywords {
    private OperatorFactory<MyComboBoxOperator> operatorFactory = new ComboBoxOperatorFactory();
    private IContextVerifier contextVerifier = new DefaultContextVerifier();

    @RobotKeyword("Selects an item from a combobox.\n\n"
        + "Example:\n"
        + "| Select From Combo Box | _myComboBox_ | _myItem_ | # Selects _'myItem'_ from combobox |\n"
        + "| Select From Combo Box | _myComboBox_ | _0_      | # Selects the first item from combobox |\n")
    public void selectFromComboBox(String comboBoxIdentifier, String comboItemIdentifier) {
        contextVerifier.verifyContext();
        MyComboBoxOperator comboBoxOperator = operatorFactory.createOperator(comboBoxIdentifier);
        comboBoxOperator.pushComboButton();
        comboBoxOperator.selectItem(comboItemIdentifier);
    }

    @RobotKeyword("Alias for `Select From Combobox` keyword.\n")
    public void selectFromDropdownMenu(String menuIdentifier, String menuItemIdentifier) {
        selectFromComboBox(menuIdentifier, menuItemIdentifier);
    }

    @RobotKeyword("Returns an item from a combobox.\n\n"
        + "Example:\n"
        + "| ${selectedItem}= | Get Selected Item From Combobox | _myComboBox_      |\n"
        + "| Should Be Equal  | _item three_                    | _${selectedItem}_ |\n")
    public Object getSelectedItemFromComboBox(String identifier) {
        contextVerifier.verifyContext();
        return operatorFactory.createOperator(identifier).getSelectedItem();
    }

    @RobotKeyword("Alias for `Get Selected Item From Combobox` keyword.\n")
    public Object getSelectedItemFromDropdownMenu(String identifier) {
        return getSelectedItemFromComboBox(identifier);
    }
}
