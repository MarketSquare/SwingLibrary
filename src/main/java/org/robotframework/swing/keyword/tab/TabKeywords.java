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
import org.netbeans.jemmy.util.RegExComparator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.common.Identifier;
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
        + "*N.B.* Regular expression can be used to select the tab pane or/and page by prefixing the identifiers with ``regexp=``.\n"
        + "See more details in `Regular expressions` section.\n\n"
        + "Examples:\n"
        + "| `Select Tab` | Customer Information |\n"
        + "| `Select Tab` | Customer Information | Customers |\n"
        + "| `Select Tab` | regexp=^A.* | regexp=^B.* | Selects a tab page starting with 'A' from a tab pane starting with 'B' | \n")
    @ArgumentNames({"tabIdentifier", "tabPaneIdentifier="})
    public void selectTab(String tabIdentifier, String tabPaneIdentifier) {
        selectTheTab(tabIdentifier, tabPaneIdentifier);
    }

    @RobotKeywordOverload
    public void selectTab(String tabIdentifier) {
        selectTab(tabIdentifier, "");
    }

    private Component selectTheTab(String tabIdentifier, String tabPaneIdentifier) {
        if (notNullOrBlank(tabPaneIdentifier))
            selectTabPane(tabPaneIdentifier);
        return selectTabPage(tabIdentifier);
    }

    private boolean notNullOrBlank(String tabPaneIdentifier) {
        return tabPaneIdentifier != null && !"".equals(tabPaneIdentifier);
    }

    private Component selectTabPage(String tabIdentifier) {
        Identifier id = new Identifier(tabIdentifier);
        if (id.isRegExp()) {
            return createTabPane().selectPage(id.asString(), new RegExComparator());
        }
        return createTabPane().selectPage(indexOfTab(tabIdentifier));
    }

    private int indexOfTab(String tabIdentifier) {
        if (isIndex(tabIdentifier))
            return Integer.valueOf(tabIdentifier);
        return createTabPane().indexOfTab(tabIdentifier);
    }

    @RobotKeyword("Selects a tab and sets it as the context.\n"
        + "The optional tab pane identifier can be provided, otherwise the first matching tab is selected.\n\n"
        + "*N.B.* Regular expression can be used to select the tab pane or/and page by prefixing the identifiers with ``regexp=``.\n"
        + "See more details in `Regular expressions` section.\n\n"
        + "Examples:\n"
        + "| `Select Tab As Context` | Customer Information |\n"
        + "| `Select Tab As Context` | Customer Information | Customers |\n"
        + "| `Select Tab As Context` | regexp=^A.* | regexp=^B.* | Selects a tab page starting with 'A' from a tab pane starting with 'B' | \n")

    @ArgumentNames({"tabIdentifier", "tabPaneIdentifier="})
    public void selectTabAsContext(String tabIdentifier, String tabPaneIdentifier) {
        try {
            Component container = selectTheTab(tabIdentifier, tabPaneIdentifier);
            setAsContext((Container)container);
        } catch(Exception e) {
            throw new RuntimeException("Can't select tab: "+tabIdentifier+" because it doesn't contain any container.");
        }
    }

    @RobotKeywordOverload
    public void selectTabAsContext(String tabIdentifier) {
        selectTabAsContext(tabIdentifier, "");
    }

    private void setAsContext(Container container) {
        TabOperator operator = new TabOperator(container);
        Context.setContext(operator);
    }

    @RobotKeyword("Returns the label of the tab that is currenctly selected.\n"
        + "Expects that only one tab pane exists in the current context.\n"
        + "If you need to operate on a different tab pane use `Select Tab Pane` keyword first.\n\n"
        + "Example:\n"
        + "| ${currentTab}= | `Get Selected Tab Label` |\n"
        + "| `Should Be Equal` | Customer Information | ${currentTab} |\n")
    public String getSelectedTabLabel() {
        JTabbedPaneOperator paneOperator = createTabPane();
        return paneOperator.getTitleAt(paneOperator.getSelectedIndex());
    }

    @RobotKeyword("Sets a tab pane as the current context.\n"
        + "Useful if you have several tab panes in the window.\n\n"
        + "*N.B.* Regular expression can be used to select the tab pane by prefixing the identifiers with ``regexp=``.\n"
        + "See more details in `Regular expressions` section.\n\n"
        + "Examples:\n"
        + "| `Select Tab Pane` | Other Tab Pane |\n"
        + "| `Select Tab Pane` | Customer Information |\n"
        + "| `Select Tab Pane` | regexp=^A.* | Selects a tab pane starting with 'A' |\n")
    @ArgumentNames({"tabPaneIdentifier"})
    public void selectTabPane(String tabPaneIdentifier) {
        TabbedPaneOperator operator = paneOperatorFactory.createOperator(tabPaneIdentifier);
        Context.setContext(operator);
    }

    private JTabbedPaneOperator createTabPane() {
        return paneOperatorFactory.createOperatorFromContext();
    }
}
