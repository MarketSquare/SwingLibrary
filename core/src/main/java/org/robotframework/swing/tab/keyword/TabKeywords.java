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

package org.robotframework.swing.tab.keyword;

import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.context.Context;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class TabKeywords extends IdentifierSupport {
    private TabPaneOperatorFactory operatorFactory = new TabPaneOperatorFactory();

    @RobotKeyword("Selects a tab.\n"
        + "Expects that only one tab pane exists in the current context.\n"
        + "If you need to operate on a different tab pane use `Select Tab Pane` keyword first.\n\n"
        + "Example:\n"
        + "| Select Tab | _Customer Information_ |\n")
    public void selectTab(String tabIdentifier) {
        if (isIndex(tabIdentifier)) {
            createTabPane().selectPage(asIndex(tabIdentifier));
        } else {
            createTabPane().selectPage(tabIdentifier);
        }
    }

    @RobotKeyword("Returns the label of the tab that is currenctly selected.\n"
        + "Expects that only one tab pane exists in the current context.\n"
        + "If you need to operate on a different tab pane use `Select Tab Pane` keyword first.\n\n"
        + "Example:\n"
        + "| ${currentTab}= | Get Selected Tab Label |\n"
        + "| Should Be Equal | _Customer Information_ | ${currentTab} |\n")
    public String getSelectedTabLabel() {
        JTabbedPaneOperator paneOperator = createTabPane();
        return paneOperator.getTitleAt(paneOperator.getSelectedIndex());
    }

    @RobotKeyword("Sets a tab pane as the current context.\n"
        + "Useful if you have several tab panes in the window.\n\n"
        + "Example:\n"
        + "| Select Tab Pane | _Other Tab Pane_ |\n"
        + "| Select Tab | _Customer Information_ |\n")
    public void selectTabPane(String identifier) {
        Context.setContext(operatorFactory.createOperator(identifier));
    }

    private JTabbedPaneOperator createTabPane() {
        return operatorFactory.createOperatorFromContext();
    }
}
