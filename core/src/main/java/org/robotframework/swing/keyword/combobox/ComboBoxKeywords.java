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

import junit.framework.Assert;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.combobox.ComboBoxOperator;
import org.robotframework.swing.combobox.ComboBoxOperatorFactory;
import org.robotframework.swing.factory.OperatorFactory;

@RobotKeywords
public class ComboBoxKeywords {
    private OperatorFactory<ComboBoxOperator> operatorFactory = new ComboBoxOperatorFactory();

    @RobotKeyword("Selects an item from a combobox.\n"
        + "The item selection is verified by default and can be disabled using the optional _verifySelection_ parameter with any value.\n\n"
    	+ "*N.B* If the _comboItemIdentifier_ is a numerical value it vill be interpreted as index.\n\n"
        + "Example:\n"
        + "| Select From Combo Box | _myComboBox_ | _myItem_ | # Selects _'myItem'_ from combobox |\n"
        + "| Select From Combo Box | _myComboBox_ | _0_      | # Selects the first item from combobox |\n"
        + "| Select From Combo Box | _myComboBox_ | _myItem_      | _don't verify_ | # Selects _'myItem'_ from combobox and doesn't verify that myItem is selected |\n")
    @ArgumentNames({"identifier", "comboItemIdentifier", "*verifySelection"})
    public void selectFromComboBox(String identifier, String comboItemIdentifier, String[] verifySelection) {
        createOperator(identifier, verifySelection).selectItem(comboItemIdentifier);
    }

    private ComboBoxOperator createOperator(String identifier, String[] verifySelection) {
        if (isVerificationDisabled(verifySelection))
            return createNonVerifyingOperator(identifier);
        return createOperator(identifier);
    }

    private ComboBoxOperator createOperator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }
    
    private boolean isVerificationDisabled(String[] verifySelection) {
        return verifySelection != null && verifySelection.length > 0 
            && verifySelection[0] != null && verifySelection[0].length() > 0;
    }
    
    @RobotKeyword("Alias for `Select From Combobox` keyword.\n")
    @ArgumentNames({"menuIdentifier", "menuItemIdentifier", "*verificationDisabled"})
    public void selectFromDropdownMenu(String menuIdentifier, String menuItemIdentifier, String[] verificationDisabled) {
        selectFromComboBox(menuIdentifier, menuItemIdentifier, verificationDisabled);
    }

    @RobotKeyword("Returns an item from a combobox.\n\n"
        + "Example:\n"
        + "| ${selectedItem}= | Get Selected Item From Combobox | _myComboBox_      |\n"
        + "| Should Be Equal  | _item three_                    | _${selectedItem}_ |\n")
    public Object getSelectedItemFromComboBox(String identifier) {
        return createOperator(identifier).getSelectedItem();
    }

    @RobotKeyword("Alias for `Get Selected Item From Combobox` keyword.\n")
    public Object getSelectedItemFromDropdownMenu(String identifier) {
        return getSelectedItemFromComboBox(identifier);
    }
    
    @RobotKeyword("Fails if combobox is disabled\n\n"
        + "Example:\n"
        + "| Combobox Should Be Enabled | _OK_ |\n")
    public void comboBoxShouldBeEnabled(String identifier) {
        Assert.assertTrue("Combobox '" + identifier + "' was disabled.", createOperator(identifier).isEnabled());
    }
    
    @RobotKeyword("Fails if combobox is enabled\n\n"
        + "Example:\n"
        + "| Combobox Should Be Disabled | _OK_ |\n")
    public void comboBoxShouldBeDisabled(String identifier) {
        Assert.assertFalse("Combobox '" + identifier + "' was enabled.", createOperator(identifier).isEnabled());
    }
    
    @RobotKeyword("Types text into a combobox.\n"
        + "Example:\n"
        + "| Type Into Combobox | _myCombobox_ | _someValue_ |\n")
    public void typeIntoCombobox(String identifier, String text) {
        createOperator(identifier).typeText(text);
    }
    
    @RobotKeyword("Returns a list containing all the values of a combobox.\n\n"
    	+ "*N.B* This keyword will return the values contained by _javax.swing.ComboBoxModel_,\n"
    	+ "this is not necessarily what is displayed on the GUI.\n\n"
        + "Example:\n"
        + "| _${comboboxValues}=_ | Get Combobox Values | _myCombobox_ |\n"
        + "| Should Contain  | _${expectedValue}_ | _${comboboxValues}_ |\n")
    public Object[] getComboboxValues(String identifier) {
        return createOperator(identifier).getValues();
    }

    private ComboBoxOperator createNonVerifyingOperator(String identifier) {
        ComboBoxOperator operator = operatorFactory.createOperator(identifier);
        operator.disableVerification();
        return operator;
    }
}

