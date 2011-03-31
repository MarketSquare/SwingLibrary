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

import javax.swing.JCheckBoxMenuItem;

import junit.framework.Assert;

import org.netbeans.jemmy.operators.JCheckBoxMenuItemOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.keyword.window.WindowKeywords;
import org.robotframework.swing.menu.MenuSupport;

@RobotKeywords
public class MenuCheckboxKeywords extends MenuSupport {
    private WindowKeywords windowKeywords = new WindowKeywords();
    
    @RobotKeyword("Fails if checkbox menu item is not checked in the opened window that was opened first.\n\n"
        + "Example:\n"
        + "| Main Menu Item Should Be Checked | _Tools|My Checkbox_ |\n")
    public void mainMenuItemShouldBeChecked(String menuPath) {
        windowKeywords.selectMainWindow();
        menuItemShouldBeChecked(menuPath);
    }
    
    @RobotKeyword("Fails if checkbox menu item is checked in the opened window that was opened first.\n\n"
        + "Example:\n"
        + "| Main Menu Item Should Not Be Checked | _Tools|My Checkbox_ |\n")
    public void mainMenuItemShouldNotBeChecked(String menuPath) {
        windowKeywords.selectMainWindow();
        menuItemShouldNotBeChecked(menuPath);
    }
    
    @RobotKeyword("Searches for an checkbox menu item from the menu of the currently selected window "
        + "and fails if it is not checked.\n\n"
        + "Example:\n"
        + "| Menu Item Should Be Checked | _Tools|My Checkbox_ |\n")
    public void menuItemShouldBeChecked(String menuPath) {
        Assert.assertTrue("Menu item '" + menuPath + "' is not selected.", showMenuItem(menuPath).isSelected());
    }
    
    @RobotKeyword("Searches for an checkbox menu item from the menu of the currently selected window "
        + "and fails if it is checked.\n\n"
        + "Example:\n"
        + "| Menu Item Should Not Be Checked | _Tools|My Checkbox_ |\n")
    public void menuItemShouldNotBeChecked(String menuPath) {
        Assert.assertFalse("Menu item '" + menuPath + "' is selected.", showMenuItem(menuPath).isSelected());
    }
    
    @Override
    protected JCheckBoxMenuItemOperator showMenuItem(String path) {
        JMenuItemOperator menuItemOperator = super.showMenuItem(path);
        return new JCheckBoxMenuItemOperator((JCheckBoxMenuItem) menuItemOperator.getSource());
    }
}
