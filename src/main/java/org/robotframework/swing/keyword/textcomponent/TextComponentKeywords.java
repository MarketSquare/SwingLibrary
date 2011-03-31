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

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.textcomponent.TextComponentOperator;
import org.robotframework.swing.textcomponent.TextComponentOperatorFactory;

@RobotKeywords
public class TextComponentKeywords {
    private OperatorFactory<TextComponentOperator> operatorFactory = new TextComponentOperatorFactory();

    @RobotKeyword("Inserts text into a text component (e.g. text field, password field, text area).\n\n"
        + "Example:\n"
        + "| Insert Into Textfield | _nameTextField_ | _John Doe_ |\n")
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
        + "Example:\n"
        + "| ${textFieldValue}= | Get Textfield Value | _nameTextField_       |\n"
        + "| Should Be Equal    | _John Doe_          | _${textFieldValue}_ |\n")
    public String getTextFieldValue(String identifier) {
        return createOperator(identifier).getText();
    }

    @RobotKeyword("Types text into a text component (e.g. text field, password field, text area).\n"
        + "Same as `Insert Into Textfield` but sends real key events when setting text field contents.\n"
        + "Useful if application expects real keyboard events instead of only setting the text of the textfield.\n\n"
        + "Example:\n"
        + "| Type Into Textfield | _nameTextField_ | _John Doe_ |\n")
    public void typeIntoTextField(String identifier, String text) {
        createOperator(identifier).typeText(text);
    }

    @RobotKeyword("Clears contents of text component (e.g. text field, password field, text area).\n\n"
        + "Example:\n"
        + "| Clear Textfield | _nameTextField_ |\n")
    public void clearTextField(String identifier) {
        createOperator(identifier).clearText();
    }

    @RobotKeyword("Fails if text component (e.g. text field, password field, text area) is disabled.\n\n"
        + "Example:\n"
        + "| Textfield Should Be Enabled | _nameTextField_ |\n")
    public void textFieldShouldBeEnabled(String identifier) {
        Assert.assertTrue("Textfield '" + identifier + "' is disabled.", createOperator(identifier).isEnabled());
    }
    
    @RobotKeyword("Fails if text component (e.g. text field, password field, text area) is enabled.\n\n"
        + "Example:\n"
        + "| Textfield Should Be Disabled | _nameTextField_ |\n")
    public void textFieldShouldBeDisabled(String identifier) {
        Assert.assertFalse("Textfield '" + identifier + "' is enabled.", createOperator(identifier).isEnabled());
    }

    private TextComponentOperator createOperator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }
}
