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

import java.awt.Component;
import java.awt.event.KeyEvent;

import javax.swing.JMenuItem;

import junit.framework.Assert;

import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.tree.ITreePopupMenuItemFinder;
import org.robotframework.swing.tree.TreePopupMenuItemFinder;
import org.robotframework.swing.tree.TreeSupport;

import abbot.tester.ComponentTester;

@RobotKeywords
public class TreeNodePopupKeywords extends TreeSupport {
    @RobotKeyword("Selects an item from the tree node's popup menu.\n\n"
        + "Example:\n"
        + "| Select From Tree Node Popup Menu | _myTree_ | _Root|Folder_ | _New Folder_ | ")
    public void selectFromTreeNodePopupMenu(String identifier, String nodeIdentifier, String menuPath) {
        JPopupMenuOperator popupOperator = createTreeOperator(identifier).createPopupOperator(nodeIdentifier);
        popupOperator.pushMenu(menuPath, new EqualsStringComparator());
    }

    @RobotKeyword("Selects an item from the tree node's popup menu and does not wait for a result.\n"
        + "Needed if the action starts a process that stays running (e.g. opens a dialog) and the above keyword doesn't return.\n"
        + "Separator for items is '|'.\n\n"
        + "Example:\n"
        + "| Select From Tree Node Popup Menu In Separate Thread | _myTree_ | _Root|Folder_ | _New Folder_ | ")
    public void selectFromTreeNodePopupMenuInSeparateThread(String identifier, String nodeIdentifier, String menuPath) {
        JPopupMenuOperator popupOperator = createTreeOperator(identifier).createPopupOperator(nodeIdentifier);
        popupOperator.pushMenuNoBlock(menuPath, new EqualsStringComparator());
    }

    @RobotKeyword("Invokes a menu action on all the selected tree nodes.\n"
    	+ "Does not wait for a result.\n"
        + "Separator for items is '|'.\n\n"
        + "Examples:\n"
        + "| Select Tree Node | _myTree_ | _Root|Folder1_ |\n"
        + "| Select Tree Node | _myTree_ | _Root|Folder2_ |\n"
        + "| Select From Popup Menu On Selected Tree Nodes | _myTree_ | _Remove_ | ")
    public void selectFromPopupMenuOnSelectedTreeNodes(String identifier, String menuPath) {
        JPopupMenuOperator popupOperator = createTreeOperator(identifier).createPopupOperatorOnSelectedNodes();
        popupOperator.pushMenuNoBlock(menuPath, new EqualsStringComparator());
        waitToAvoidInstability();
    }
    
    @RobotKeyword("Fails if given popup menu item is disabled.\n\n"
        + "Examples:\n"
        + "| Tree Node Popup Menu Item Should Be Enabled | _myTree_ | _Root|Folder_ | _New Folder_ |\n"
        + "| Tree Node Popup Menu Item Should Be Enabled | _0_ | _1_ | _New Folder_ |\n")
    public void treeNodePopupMenuItemShouldBeEnabled(String identifier, String nodeIdentifier, String menuPath) {
        boolean menuItemIsEnabled = menuItemIsEnabled(identifier, nodeIdentifier, menuPath);
        Assert.assertTrue("Menu item '" + menuPath + "' was disabled", menuItemIsEnabled);
    }

    @RobotKeyword("Fails if given popup menu item is enabled.\n\n"
        + "Examples:\n"
        + "| Tree Node Popup Menu Item Should Be Disabled | _myTree_ | _Root|Folder_ | _New Folder_ |\n"
        + "| Tree Node Popup Menu Item Should Be Disabled | _0_      | _1_ | _New Folder_ |\n")
    public void treeNodePopupMenuItemShouldBeDisabled(String identifier, String nodeIdentifier, String menuPath) {
        boolean menuItemIsEnabled = menuItemIsEnabled(identifier, nodeIdentifier, menuPath);
        Assert.assertFalse("Menu item '" + menuPath + "' was enabled", menuItemIsEnabled);
    }
    
    private boolean menuItemIsEnabled(String identifier, String nodeIdentifier, String menuPath) {
        try {
            return createPopupMenuItem(identifier, nodeIdentifier, menuPath).isEnabled();
        } finally {
            closePopup();
        }
    }

    private void closePopup() {
        new ComponentTester().actionKeyStroke(KeyEvent.VK_ESCAPE);
    }

    private JMenuItem createPopupMenuItem(String identifier, String nodeIdentifier, String menuPath) {
        Component source = createTreeOperator(identifier).getSource();
        return createPopupMenuItemFinder(source).findMenu(nodeIdentifier, menuPath);
    }
    
    ITreePopupMenuItemFinder createPopupMenuItemFinder(Component source) {
        return new TreePopupMenuItemFinder(source);
    }
    
    void waitToAvoidInstability() {
        new EventTool().waitNoEvent(300);   
    }
}
