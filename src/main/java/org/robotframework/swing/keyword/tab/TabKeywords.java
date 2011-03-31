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

package org.robotframework.swing.keyword.tab;

import java.awt.Component;
import java.awt.Container;

import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.tab.TabOperator;
import org.robotframework.swing.tab.TabPaneOperatorFactory;
import org.robotframework.swing.tab.TabbedPaneOperator;

@RobotKeywords
public class TabKeywords extends IdentifierSupport {
    private TabPaneOperatorFactory paneOperatorFactory = new TabPaneOperatorFactory();

    @RobotKeyword("Selects a tab.\n"
        + "The optional tab pane identifier can be provided, otherwise the first matching tab is selected.\n\n"
        + "Example:\n"
        + "| Select Tab | _Customer Information_ |\n"
        + "| Select Tab | _Customer Information_ | _Customers_ |\n")
    @ArgumentNames({"tabIdentifier", "*tabPaneIdentifier"})
    public void selectTab(String tabIdentifier, String[] tabPaneIdentifier) {
        selectTheTab(tabIdentifier, tabPaneIdentifier);
    }

    private Component selectTheTab(String tabIdentifier, String[] tabPaneIdentifier) {
        if (notNullOrBlank(tabPaneIdentifier))
            selectTabPane(tabPaneIdentifier[0]);
        return selectTabPage(tabIdentifier);
    }

    private boolean notNullOrBlank(String[] tabPaneIdentifier) {
        return tabPaneIdentifier != null && tabPaneIdentifier.length > 0;
    }

    private Component selectTabPage(String tabIdentifier) {
        return createTabPane().selectPage(indexOfTab(tabIdentifier));
    }
    
    private int indexOfTab(String tabIdentifier) {
        if (isIndex(tabIdentifier))
            return Integer.valueOf(tabIdentifier);
        return createTabPane().indexOfTab(tabIdentifier);
    }
    
    @RobotKeyword("Selects a tab and sets it as the context.\n"
        + "The optional tab pane identifier can be provided, otherwise the first matching tab is selected.\n\n"
        + "Example:\n"
        + "| Select Tab | _Customer Information_ |\n"
        + "| Select Tab | _Customer Information_ | _Customers_ |\n")
    @ArgumentNames({"tabIdentifier", "*tabPaneIdentifier"})
    public void selectTabAsContext(String tabIdentifier, String[] tabPaneIdentifier) {
        try {
            Component container = selectTheTab(tabIdentifier, tabPaneIdentifier);
            setAsContext((Container)container);
        } catch(Exception e) {
            throw new RuntimeException("Can't select tab: "+tabIdentifier+" because it doesn't contain any container.");
        }
    }

    private void setAsContext(Container container) {
        TabOperator operator = new TabOperator(container);
        Context.setContext(operator);
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
    public void selectTabPane(String tabPaneIdentifier) {
        TabbedPaneOperator operator = paneOperatorFactory.createOperator(tabPaneIdentifier);
        Context.setContext(operator);
    }

    private JTabbedPaneOperator createTabPane() {
        return paneOperatorFactory.createOperatorFromContext();
    }
}
