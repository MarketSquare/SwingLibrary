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

package org.robotframework.swing.keyword.textcomponent;

import org.junit.Assert;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.textcomponent.TextComponentOperator;
import org.robotframework.swing.textcomponent.TextComponentOperatorFactory;

@RobotKeywords
public class TextComponentKeywords {
    private OperatorFactory<TextComponentOperator> operatorFactory = new TextComponentOperatorFactory();

    @RobotKeyword("Inserts text into a text component (e.g. text field, password field, text area).\n\n"
        + "See `Locating components` for details.\n\n"
        + "Examples:\n"
        + "| `Insert Into Textfield` | nameTextField | John Doe |\n"
        + "| `Insert Into Textfield` | awt=streetaddress | Karaportti 3 |\n")
    @ArgumentNames({"identifier", "text"})
    public void insertIntoTextField(String identifier, String text) {
        TextComponentOperator operator = createOperator(identifier);
        if (notEditable(operator))
            throw new RuntimeException("Text field '" + identifier + "' is not editable.");
        operator.setText(text);
    }

    private boolean notEditable(TextComponentOperator operator) {
        return ! operator.isEditable() || ! operator.isEnabled();
    }

    @RobotKeyword("Returns the value of a text component (e.g. text field, password field, text area).\n\n"
        + "See `Locating components` for details.\n\n"
        + "Example:\n"
        + "| ${textFieldValue}= | `Get Textfield Value` | nameTextField     |\n"
        + "| `Should Be Equal`  | John Doe              | ${textFieldValue} |\n")
    @ArgumentNames({"identifier"})
    public String getTextFieldValue(String identifier) {
        return createOperator(identifier).getText();
    }

    @RobotKeyword("Types text into a text component (e.g. text field, password field, text area).\n"
        + "Same as `Insert Into Textfield` but sends real key events when setting text field contents.\n"
        + "Useful if application expects real keyboard events instead of only setting the text of the textfield.\n\n"
        + "See `Locating components` for details.\n\n"
        + "Examples:\n"
        + "| `Type Into Textfield` | nameTextField | John Doe |\n"
        + "| `Type Into Textfield` | awt=streetaddress | Karaportti 3 |\n")
    @ArgumentNames({"identifier", "text"})
    public void typeIntoTextField(String identifier, String text) {
        createOperator(identifier).typeText(text);
    }

    @RobotKeyword("Clears contents of text component (e.g. text field, password field, text area).\n\n"
        + "See `Locating components` for details.\n\n"
        + "Examples:\n"
        + "| `Clear Textfield` | nameTextField |\n"
        + "| `Clear Textfield` | awt=streetaddress |\n")
    @ArgumentNames({"identifier"})
    public void clearTextField(String identifier) {
        createOperator(identifier).clearText();
    }

    @RobotKeyword("Fails if text component (e.g. text field, password field, text area) is disabled.\n\n"
        + "See `Locating components` for details.\n\n"
        + "Example:\n"
        + "| `Textfield Should Be Enabled` | nameTextField |\n")
    @ArgumentNames({"identifier"})
    public void textFieldShouldBeEnabled(String identifier) {
        Assert.assertTrue("Textfield '" + identifier + "' is disabled.", createOperator(identifier).isEnabled());
    }

    @RobotKeyword("Fails if text component (e.g. text field, password field, text area) is enabled.\n\n"
        + "See `Locating components` for details.\n\n"
        + "Example:\n"
        + "| `Textfield Should Be Disabled` | nameTextField |\n")
    @ArgumentNames({"identifier"})
    public void textFieldShouldBeDisabled(String identifier) {
        Assert.assertFalse("Textfield '" + identifier + "' is enabled.", createOperator(identifier).isEnabled());
    }

    private TextComponentOperator createOperator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }
}
