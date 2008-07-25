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

package org.robotframework.swing.keyword.window;

import java.awt.Window;

import javax.swing.JFrame;

import org.netbeans.jemmy.operators.JFrameOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.ContextVerifier;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class WindowKeywords extends ContextVerifier {
    private IdentifierParsingOperatorFactory<JFrameOperator> operatorFactory = new JFrameOperatorFactory();

    public WindowKeywords() {
        super("To use this keyword you must first select a window as context using the 'Select Window'-keyword.");
    }

    @RobotKeyword("Selects the window that was opened first as current context.\n\n"
        + "Example:\n"
        + "| Select Main Window |\n")
    public void selectMainWindow() {
        Context.setContext(operatorFactory.createOperatorByIndex(0));
    }

    @RobotKeyword("Selects a window was as current context.\n\n"
        + "Example:\n"
        + "| Select Window | _Help_ |\n")
    public void selectWindow(String identifier) {
        Context.setContext(operatorFactory.createOperator(identifier));
    }

    @RobotKeyword("Closes a window.\n\n"
        + "Example:\n"
        + "| Close Window | _Help_ |\n")
    public void closeWindow(String identifier) {
        JFrameOperator frameOperator = operatorFactory.createOperator(identifier);
        frameOperator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameOperator.close();
    }

    @RobotKeyword("Returns the title of the selected window.\n"
        + "Assumes current context is window.\n\n"
        + "Example:\n"
        + "| ${title}=     | Get Selected Window Title |            |\n"
        + "| Should Be Equal | _Help Contents_           | _${title}_ |\n")
    public String getSelectedWindowTitle() {
        return frameOperator().getTitle();
    }

    @Override
    protected Class[] getExpectedClasses() {
        return new Class[] { Window.class };
    }

    private JFrameOperator frameOperator() {
        verifyContext();
        return (JFrameOperator) Context.getContext();
    }
}
