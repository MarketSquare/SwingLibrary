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

import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.arguments.ArgumentParser;
import org.robotframework.swing.menu.MenuSupport;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;

@RobotKeywords
public class MenuKeywords extends MenuSupport {
    @RobotKeyword("Selects an item from the menu of the currently selected window.\n\n"
        + "Example:\n"
        + "| Select Window    | _My Application_           |\n"
        + "| Select From Menu | _Tools|Testing|MyTestTool_ |\n")
    public void selectFromMenu(String menuPath) {
        menuItemShouldBeEnabled(menuPath);
        showMenuItem(menuPath).pushNoBlock();
    }

    @RobotKeyword("Selects an item from the menu of the currently selected window "
        + "and waits for action to finish.\n"
        + "This keyword will not work, for example, if the menu item opens a dialog.\n\n"
        + "Example:\n"
        + "| Select Window             | _My Application_           |\n"
        + "| Select From Menu And Wait | _Tools|Testing|MyTestTool_ |\n")
    public void selectFromMenuAndWait(String menuPath) {
        menuItemShouldBeEnabled(menuPath);
        showMenuItem(menuPath).push();
    }

    @RobotKeyword("Searches for an menu item from the menu of the currently selected window "
        + "and fails if it is disabled.\n\n"
        + "Example:\n"
        + "| Select Window               | _My Application_           |\n"
        + "| Menu Item Should Be Enabled | _Tools|Testing|MyTestTool_ |\n")
    public void menuItemShouldBeEnabled(String menuPath) {
        Assert.assertTrue("Menu item '" + menuPath + "' is disabled.", menuIsEnabled(menuPath));
    }

    @RobotKeyword("Searches for an menu item from the menu of the currently selected window "
        + "and fails if it is enabled.\n\n"
        + "Example:\n"
        + "| Select Window                   | _My Application_           |\n"
        + "| Menu Item Should Not Be Enabled | _Tools|Testing|MyTestTool_ |\n")
    public void menuItemShouldNotBeEnabled(String menuPath) {
        Assert.assertFalse("Menu item '" + menuPath + "' is enabled.", menuIsEnabled(menuPath));
    }

    @RobotKeyword("Alias for `Menu Item Should Not Be Enabled`\n")
    public void menuItemShouldBeDisabled(String menuPath) {
        menuItemShouldNotBeEnabled(menuPath);
    }

    @RobotKeyword("Fails if menu item doesn't exist.\n\n"
        + "Example:\n"
        + "| Menu Item Should Exist | _Tools|Testing|Test Tool_ |\n")
    public void menuItemShouldExist(String menuPath) {
        Assert.assertTrue("Menu item '" + menuPath + "' does not exist.", menuExists(menuPath));
    }
    
    @RobotKeyword("Fails if menu item exists.\n\n"
        + "Example:\n"
        + "| Menu Item Should Not Exist | _Tools|Testing|Test Tool_ |\n")
    public void menuItemShouldNotExist(String menuPath) {
        Assert.assertFalse("Menu item '" + menuPath + "' exists.", menuExists(menuPath));
    }
    
    private interface MenuAction<T> {
        T doWithMenuItem();
    }

    private Boolean menuExists(final String menuPath) {
        return getFromMenuItem(new MenuAction<Boolean>() {
            public Boolean doWithMenuItem() {
                IComponentConditionResolver existenceResolver = createMenuItemExistenceResolver();
                return existenceResolver.satisfiesCondition(menuPath);
            }
        });
    }
    
    private Boolean menuIsEnabled(final String menuPath) {
        return getFromMenuItem(new MenuAction<Boolean>() {
            public Boolean doWithMenuItem() {
                return showMenuItem(menuPath).isEnabled();
            }
        });
    }
    
    private <T> T getFromMenuItem(MenuAction<T> action) {
        try {
            return action.doWithMenuItem();
        } finally {
            closeMenu();
        }
    }
    
    private void closeMenu() {
        menubarOperator().pressMouse();
    }

    IComponentConditionResolver createMenuItemExistenceResolver() {
        ArgumentParser<JMenuItemOperator> menuItemOperatorFactory = new ArgumentParser<JMenuItemOperator>() {
            public JMenuItemOperator parseArgument(String menuPath) {
                return showMenuItem(menuPath);
            }
        };

        return new ComponentExistenceResolver(menuItemOperatorFactory);
    }
}
