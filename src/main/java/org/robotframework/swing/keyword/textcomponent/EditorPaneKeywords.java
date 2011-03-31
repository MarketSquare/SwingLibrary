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

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.textcomponent.EditorPaneOperator;
import org.robotframework.swing.textcomponent.EditorPaneOperatorFactory;

@RobotKeywords
public class EditorPaneKeywords {
    private OperatorFactory<EditorPaneOperator> operatorFactory = new EditorPaneOperatorFactory();
    
    @RobotKeyword("Clicks on a hyperlink in a JEditorPane.\n"
        + "Fails if the link is not found.\n\n"
        + "Example:\n"
        + "| Click Hyperlink | _myEditor_ | _Network Elements_ |\n")
    public void clickHyperLink(String identifier, String linkText) {
        operatorFactory.createOperator(identifier).activateHyperLink(linkText);
    }
}
