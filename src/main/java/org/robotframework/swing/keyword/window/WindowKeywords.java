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

package org.robotframework.swing.keyword.window;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.AbstractContextVerifier;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.window.FrameOperator;
import org.robotframework.swing.window.FrameOperatorFactory;

import abbot.tester.WindowTester;

@RobotKeywords
public class WindowKeywords extends AbstractContextVerifier {
    private final IdentifierParsingOperatorFactory<FrameOperator> operatorFactory = new FrameOperatorFactory();

    public WindowKeywords() {
        super(
                "To use this keyword you must first select a window as context using the 'Select Window'-keyword.");
    }

    @RobotKeyword("Selects the window that was opened first as current context.\n\n"
            + "Example:\n" + "| `Select Main Window` |\n")
    public void selectMainWindow() {
        setContext(operatorFactory.createOperatorByIndex(0));
    }

    @RobotKeyword("Gets list of open window titles.\n\n"
            +"Logs the window titles and names in parenthesis.\n\n"
            + "Example:\n"
            + "| `List Windows` |\n")
    public List<String> listWindows() {
        List<String> result = new ArrayList<String>();
        for (Frame frame: Frame.getFrames()) {
            result.add(frame.getTitle());
            System.out.println( frame.getTitle() + " (" + frame.getName() +")");
        }
        return result;
    }

    @RobotKeyword("Selects a window as current context and sets focus to it.\n\n"
            + "*N.B.* Regular expression can be used to select the window by prefixing the identifier with ``regexp=``.\n"
            + "See more details in `Regular expressions` section.\n\n"
            + "Examples:\n"
            + "| `Select Window` | Help |\n"
            + "| `Select Window` | regexp=^H.* | Selects a window starting with letter H. |\n")
    @ArgumentNames({ "identifier" })
    public void selectWindow(String identifier) {
        FrameOperator operator = operatorFactory.createOperator(identifier);
        setContext(operator);
    }

    @RobotKeyword("Sets a window size.\n\n"
            + "Examples:\n"
            + "| `Set Window Size` | Help | 800 | 600 | # Re-sizes the Help window to 800 px width and 600 px height. |\n")
    @ArgumentNames({ "identifier", "width", "height" })
    public void setWindowSize(String identifier, String width, String height) {
        FrameOperator operator = operatorFactory.createOperator(identifier);
        setContext(operator);
        operator.resize(Integer.parseInt(width), Integer.parseInt(height));
    }

    @RobotKeyword("Returns a list containing the width and the height of the window.\n\n"
            + "Examples:\n"
            + "| ${size} | `Get Window Size` | Help | # Gets the size of the Help window. |\n"
            + "| `Should Be Equal As Integers` | ${size[0]} | 800 |\n"
            + "| `Should Be Equal As Integers` | ${size[1]} | 600 |\n")
    @ArgumentNames({ "identifier" })
    public List<String> getWindowSize(String identifier) {
        FrameOperator operator = operatorFactory.createOperator(identifier);
        setContext(operator);
        List<String> result = new ArrayList<String>();
        result.add(String.valueOf(operator.getWidth()));
        result.add(String.valueOf(operator.getHeight()));
        return result;
    }

    @RobotKeyword("Closes a window.\n\n"
            + "*N.B.* Regular expression can be used to close the window by prefixing the identifier with ``regexp=``.\n"
            + "See more details in `Regular expressions` section.\n\n"
            + "Examples:\n"
            + "| `Close Window` | Help |\n"
            + "| `Close Window` | regexp=^H.* | Closes a window starting with letter H. |\n")
    @ArgumentNames({ "identifier" })
    public void closeWindow(String identifier) {
        FrameOperator operator = operatorFactory.createOperator(identifier);
        operator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        new WindowTester().actionClose(operator.getSource());
    }

    @RobotKeyword("Returns the title of the selected window.\n"
            + "Assumes current context is window.\n\n" + "Example:\n"
            + "| ${title}=         | `Get Selected Window Title` |          |\n"
            + "| `Should Be Equal` | Help Contents               | ${title} |\n")
    public String getSelectedWindowTitle() {
        return frameOperator().getTitle();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Class<? extends Component>[] getExpectedClasses() {
        return new Class[] { Window.class };
    }

    private void setContext(FrameOperator frameOperator) {
        frameOperator.getFocus();
        Context.setContext(frameOperator);
    }

    private FrameOperator frameOperator() {
        verifyContext();
        return (FrameOperator) Context.getContext();
    }
}
