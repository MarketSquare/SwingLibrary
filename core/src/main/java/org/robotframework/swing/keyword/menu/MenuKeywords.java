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

package org.robotframework.swing.keyword.menu;

import junit.framework.Assert;

import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.arguments.ArgumentParser;
import org.robotframework.swing.menu.MenuSupport;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class MenuKeywords extends MenuSupport {
    private EventTool eventTool = new EventTool();

    @RobotKeyword("Selects an item from the menu of the currently selected window.\n\n"
        + "Example:\n"
        + "| Select Window    | _My Application_           |\n"
        + "| Select From Menu | _Tools|Testing|MyTestTool_ |\n")
    public void selectFromMenu(String menuPath) {
        showMenuItem(menuPath).pushNoBlock();
    }

    @RobotKeyword("Selects an item from the menu of the currently selected window "
        + "and waits for action to finish.\n"
        + "This keyword will not work, for example, if the menu item opens a dialog.\n\n"
        + "Example:\n"
        + "| Select Window             | _My Application_           |\n"
        + "| Select From Menu And Wait | _Tools|Testing|MyTestTool_ |\n")
    public void selectFromMenuAndWait(String menuPath) {
        showMenuItem(menuPath).push();
    }

    @RobotKeyword("Fails if menu item doesn't exist in the first opened window.\n\n"
        + "Example:\n"
        + "| Main Menu Item Should Exist | _Tools|Testing|Test Tool_ |\n")
    public void mainMenuItemShouldExist(String menuPath) {
        IComponentConditionResolver existenceResolver = createMenuItemExistenceResolver();
        Assert.assertTrue("Menu item '" + menuPath + "' does not exist.", existenceResolver.satisfiesCondition(menuPath));
    }

    @RobotKeyword("Searches for an menu item from the menu of the currently selected window "
        + "and fails if it is disabled.\n\n"
        + "Example:\n"
        + "| Select Window               | _My Application_           |\n"
        + "| Menu Item Should Be Enabled | _Tools|Testing|MyTestTool_ |\n")
    public void menuItemShouldBeEnabled(String menuPath) {
        Assert.assertTrue("Menu item '" + menuPath + "' is disabled.", menuItemIsEnabled(menuPath));
    }

    @RobotKeyword("Searches for an menu item from the menu of the currently selected window "
        + "and fails if it is enabled.\n\n"
        + "Example:\n"
        + "| Select Window                   | _My Application_           |\n"
        + "| Menu Item Should Not Be Enabled | _Tools|Testing|MyTestTool_ |\n")
    public void menuItemShouldNotBeEnabled(String menuPath) {
        Assert.assertFalse("Menu item '" + menuPath + "' is enabled.", menuItemIsEnabled(menuPath));
    }

    @RobotKeyword("Alias for `Menu Item Should Not Be Enabled`\n")
    public void menuItemShouldBeDisabled(String menuPath) {
        menuItemShouldNotBeEnabled(menuPath);
    }

    private boolean menuItemIsEnabled(String menuPath) {
        try {
            return showMenuItem(menuPath).isEnabled();
        } finally {
            closeMenu();
        }
    }

    private void closeMenu() {
        menubarOperator().pressMouse();
    }

    JMenuItemOperator showMenuItem(final String path) {
        JMenuItemOperator menuItemOperator = menubarOperator().showMenuItem(path);
        eventTool.waitNoEvent(200);
        menuItemOperator.grabFocus();
        eventTool.waitNoEvent(200);
        return menuItemOperator;
    }

    IComponentConditionResolver createMenuItemExistenceResolver() {
        ArgumentParser<JMenuItemOperator> menuItemOperatorFactory = new ArgumentParser<JMenuItemOperator>() {
            public JMenuItemOperator parseArgument(String menuPath) {
                return menubarOperator().showMenuItem(menuPath);
            }
        };

        return new ComponentExistenceResolver(menuItemOperatorFactory);
    }
}
