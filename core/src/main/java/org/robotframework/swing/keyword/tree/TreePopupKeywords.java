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

package org.robotframework.swing.keyword.tree;

import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

import junit.framework.Assert;

import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.tree.ITreePopupMenuItemFinder;
import org.robotframework.swing.tree.TreeContextVerifier;
import org.robotframework.swing.tree.TreePopupMenuItemFinder;
import org.robotframework.swing.tree.TreePopupMenuOperatorFactory;

import abbot.tester.ComponentTester;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class TreePopupKeywords {
    private OperatorFactory<JPopupMenuOperator> operatorFactory = new TreePopupMenuOperatorFactory();
    private IContextVerifier contextVerifier = new TreeContextVerifier();
    private ITreePopupMenuItemFinder treePopupMenuItemFinder = new TreePopupMenuItemFinder();

    @RobotKeyword("Selects an item from the tree node's popup menu.\n"
        + "Assumes current context is a tree.\n\n"
        + "Examples:\n"
        + "| Select From Tree Node Popup Menu | _Root|Folder_ | _New Folder_ | ")
    public void selectFromTreeNodePopupMenu(String nodeIdentifier, String menuPath) {
        contextVerifier.verifyContext();
        operatorFactory.createOperator(nodeIdentifier).pushMenu(menuPath);
    }

    @RobotKeyword("Selects an item from the tree node's popup menu and does not wait for a result.\n"
        + "Needed if the action starts a process that stays running (e.g. opens a dialog) and the above keyword doesn't return.\n"
        + "Assumes current context is a tree. Separator for items is '|'.\n\n"
        + "Examples:\n"
        + "| Select From Tree Node Popup Menu In Separate Thread | _Root|Folder_ | _New Folder_ | ")
    public void selectFromTreeNodePopupMenuInSeparateThread(String nodeIdentifier, String menuPath) {
        contextVerifier.verifyContext();
        operatorFactory.createOperator(nodeIdentifier).pushMenuNoBlock(menuPath);
    }

    @RobotKeyword("Fails if given popup menu item is disabled.\n"
        + "Assumes current context is a tree.\n\n"
        + "Example:\n"
        + "| Tree Node Popup Menu Item Should Be Enabled | _Root|Folder_ | _New Folder_ |\n"
        + "| Tree Node Popup Menu Item Should Be Enabled | _1_ | _New Folder_ |\n")
    public void treeNodePopupMenuItemShouldBeEnabled(String nodeIdentifier, String menuPath) {
        contextVerifier.verifyContext();
        Assert.assertTrue("Menu item '" + menuPath + "' was disabled", menuItemIsEnabled(nodeIdentifier, menuPath));
    }

    @RobotKeyword("Fails if given popup menu item is enabled.\n"
        + "Assumes current context is a tree.\n\n"
        + "Example:\n"
        + "| Tree Node Popup Menu Item Should Be Disabled | _Root|Folder_ | _New Folder_ |\n"
        + "| Tree Node Popup Menu Item Should Be Disabled | _1_ | _New Folder_ |\n")
    public void treeNodePopupMenuItemShouldBeDisabled(String nodeIdentifier, String menuPath) {
        contextVerifier.verifyContext();
        Assert.assertFalse("Menu item '" + menuPath + "' was enabled", menuItemIsEnabled(nodeIdentifier, menuPath));
    }

    private boolean menuItemIsEnabled(String nodeIdentifier, String menuPath) {
        try {
            return createPopupMenuItem(nodeIdentifier, menuPath).isEnabled();
        } finally {
            closePopup();
        }
    }

    private void closePopup() {
        new ComponentTester().actionKeyStroke(KeyEvent.VK_ESCAPE);
    }

    private JMenuItem createPopupMenuItem(String nodeIdentifier, String menuPath) {
        return treePopupMenuItemFinder.findMenu(nodeIdentifier, menuPath);
    }
}
