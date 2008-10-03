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

package org.robotframework.swing.keyword.textcomponent;

import org.netbeans.jemmy.operators.JTextComponentOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.DefaultContextVerifier;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.factory.OperatorFactory;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class TextComponentKeywords {
    private OperatorFactory<JTextComponentOperator> operatorFactory = new TextComponentOperatorFactory();
    private IContextVerifier contextVerifier = new DefaultContextVerifier();

    @RobotKeyword("Inserts text into a text component.\n\n"
        + "Example:\n"
        + "| Insert Into Textfield | _nameTextField_ | _John Doe_ |\n")
    public void insertIntoTextField(String identifier, String text) {
        contextVerifier.verifyContext();
        JTextComponentOperator textFieldOperator = operatorFactory.createOperator(identifier);
        textFieldOperator.setText(text);
    }

    @RobotKeyword("Returns the value of a text component.\n\n"
        + "Example:\n"
        + "| ${textFieldValue}= | Get Textfield Value | _nameTextField_       |\n"
        + "| Should Be Equal    | _John Doe_          | _${textFieldValue}_ |\n")
    public String getTextFieldValue(String identifier) {
        contextVerifier.verifyContext();
        return operatorFactory.createOperator(identifier).getText();
    }

    @RobotKeyword("Types text into a text component.\n"
        + "Same as `Insert Into Textfield` but sends real key events when setting text field contents.\n"
        + "Useful if application expects real keyboard events instead of only setting the text of the textfield.\n\n"
        + "Example:\n"
        + "| Type Into Textfield | _nameTextField_ | _John Doe_ |\n")
    public void typeIntoTextField(String identifier, String text) {
        contextVerifier.verifyContext();
        operatorFactory.createOperator(identifier).typeText(text);
    }

    @RobotKeyword("Clears text field contents.\n\n"
        + "Example:\n"
        + "| Clear Textfield | _nameTextField_ |\n")
    public void clearTextField(String identifier) {
        contextVerifier.verifyContext();
        operatorFactory.createOperator(identifier).clearText();
    }
}
