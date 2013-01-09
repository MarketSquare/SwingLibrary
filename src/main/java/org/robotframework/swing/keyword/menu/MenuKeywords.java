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

package org.robotframework.swing.keyword.menu;

import org.junit.Assert;

import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.javalib.util.KeywordNameNormalizer;
import org.robotframework.swing.arguments.ArgumentParser;
import org.robotframework.swing.menu.MenuSupport;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.ComponentHasChildrenResolver;
import org.robotframework.swing.util.IComponentConditionResolver;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@RobotKeywords
public class MenuKeywords extends MenuSupport {

    private static KeywordNameNormalizer textNormalizer = new KeywordNameNormalizer();

    @RobotKeyword("Selects an item from the menu of the currently selected window.\n\n"
        + "Example:\n"
        + "| Select Window    | _My Application_           |\n"
        + "| Select From Menu | _Tools|Testing|MyTestTool_ |\n")
    @ArgumentNames({"menuPath"})
    public void selectFromMenu(String menuPath) {
        JMenuItemOperator operator = showMenuItem(menuPath);
        Assert.assertTrue("Menu item '" + menuPath + "' is disabled.", operator.isEnabled());
        operator.pushNoBlock();
    }

    @RobotKeyword("Selects an item from the menu of the currently selected window "
        + "and waits for action to finish.\n"
        + "This keyword will not work, for example, if the menu item opens a dialog.\n\n"
        + "Example:\n"
        + "| Select Window             | _My Application_           |\n"
        + "| Select From Menu And Wait | _Tools|Testing|MyTestTool_ |\n")
    @ArgumentNames({"menuPath"})
    public void selectFromMenuAndWait(String menuPath) {
        JMenuItemOperator operator = showMenuItem(menuPath);
        Assert.assertTrue("Menu item '" + menuPath + "' is disabled.", operator.isEnabled());
        operator.push();
    }

    @RobotKeyword("Searches for an menu item from the menu of the currently selected window "
        + "and fails if it is disabled.\n\n"
        + "Example:\n"
        + "| Select Window               | _My Application_           |\n"
        + "| Menu Item Should Be Enabled | _Tools|Testing|MyTestTool_ |\n")
    @ArgumentNames({"menuPath"})
    public void menuItemShouldBeEnabled(String menuPath) {
        Assert.assertTrue("Menu item '" + menuPath + "' is disabled.", menuIsEnabled(menuPath));
    }

    @RobotKeyword("Searches for an menu item from the menu of the currently selected window "
        + "and fails if it is enabled.\n\n"
        + "Example:\n"
        + "| Select Window                   | _My Application_           |\n"
        + "| Menu Item Should Not Be Enabled | _Tools|Testing|MyTestTool_ |\n")
    @ArgumentNames({"menuPath"})
    public void menuItemShouldNotBeEnabled(String menuPath) {
        Assert.assertFalse("Menu item '" + menuPath + "' is enabled.", menuIsEnabled(menuPath));
    }

    @RobotKeyword("Alias for `Menu Item Should Not Be Enabled`\n")
    @ArgumentNames({"menuPath"})
    public void menuItemShouldBeDisabled(String menuPath) {
        menuItemShouldNotBeEnabled(menuPath);
    }

    @RobotKeyword("Fails if menu item doesn't exist.\n\n"
        + "Example:\n"
        + "| Menu Item Should Exist | _Tools|Testing|Test Tool_ |\n")
    @ArgumentNames({"menuPath"})
    public void menuItemShouldExist(String menuPath) {
        Assert.assertTrue("Menu item '" + menuPath + "' does not exist.", menuExists(menuPath));
    }

    @RobotKeyword("Fails if menu item exists.\n\n"
        + "Example:\n"
        + "| Menu Item Should Not Exist | _Tools|Testing|Test Tool_ |\n")
    @ArgumentNames({"menuPath"})
    public void menuItemShouldNotExist(String menuPath) {
        Assert.assertFalse("Menu item '" + menuPath + "' exists.", menuExists(menuPath));
    }

    @RobotKeyword("Fails if menu item does not have children.\n\n"
        +"Example:\n"
        +"| Menu Item Should Have Children | _Tools_ |\n")
    public void menuItemShouldHaveChildren(String menuPath) {
        Assert.assertTrue("Menu item '" + menuPath + "' does not have children.", menuHasChildren(menuPath));
    }

    @RobotKeyword("Fails if menu item does has children.\n\n"
            +"Example:\n"
            +"| Menu Item Should Not Have Children | _Tools_ |\n")
    public void menuItemShouldNotHaveChildren(String menuPath) {
        Assert.assertFalse("Menu item '" + menuPath + "' has children.", menuHasChildren(menuPath));
    }

    @RobotKeyword("Gets names of menus.\n\n"
        + "Example:\n"
        + "| @{menus} | Get Menu Item Names | _Tools|Testing_ |\n"
        + "| Should contain | ${menus} | _Test Tool_ |\n")
    @ArgumentNames({"menuPath"})
    public List<String> getMenuItemNames(String menuPath) {
        List<String> returnable = new ArrayList<String>();
        menuItemShouldExist(menuPath);
        menuItemShouldHaveChildren(menuPath);
        JMenuItemOperator menuItemOperator = this.menubarOperator().showMenuItem(menuPath);

        for (MenuElement e : menuItemOperator.getSubElements()[0].getSubElements()) {
            if(JMenuItem.class.isAssignableFrom(e.getClass())) {
                JMenuItemOperator op = new JMenuItemOperator((JMenuItem)e);
                returnable.add(op.getText());
            }
        }
        return returnable;
    }

    private interface MenuAction<T> {
        T doWithMenuItem();
    }

    private Boolean menuHasChildren(final String menuPath) {
        return getFromMenuItem(new MenuAction<Boolean>() {
            public Boolean doWithMenuItem() {
                if (showMenuItem(menuPath).getSubElements().length == 0){
                    return false;
                }
                return showMenuItem(menuPath).getSubElements()[0].getSubElements().length > 0;
            }
        });
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

    IComponentConditionResolver createHasChildrenResolver() {
        ArgumentParser<MenuElement[]> menuItemOperatorFactory = new ArgumentParser<MenuElement[]>() {
            public MenuElement[] parseArgument(String menuPath) {
                return getChildren(menuPath);
            }
        };
        return new ComponentHasChildrenResolver(menuItemOperatorFactory);
    }
}
