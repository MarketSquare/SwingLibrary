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
import org.netbeans.jemmy.operators.JRadioButtonMenuItemOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.keyword.window.WindowKeywords;
import org.robotframework.swing.menu.MenuSupport;

import javax.swing.JRadioButtonMenuItem;

@RobotKeywords
public class MenuRadioButtonKeywords extends MenuSupport {
    private WindowKeywords windowKeywords = new WindowKeywords();

    @RobotKeyword("Searches for a radio menu item from the menu of the currently selected window "
            + "and fails if it is not checked.\n\n"
            + "Example:\n"
            + "| `Menu Item Should Be Selected` | Tools|My RadioItem |\n")
    @ArgumentNames({"menuPath"})
    public void radioMenuItemShouldBeSelected(String menuPath) {
        Assert.assertTrue("Menu item '" + menuPath + "' is not selected.", showRadioMenuItem(menuPath).isSelected());
    }

    @RobotKeyword("Searches for a radio menu item from the menu of the currently selected window "
            + "and fails if it is selected.\n\n"
            + "Example:\n"
            + "| `Menu Item Should Not Be Selected` | Tools|My RadioItem |\n")
    @ArgumentNames({"menuPath"})
    public void radioMenuItemShouldNotBeSelected(String menuPath) {
        Assert.assertFalse("Menu item '" + menuPath + "' is selected.", showRadioMenuItem(menuPath).isSelected());
    }

    private JRadioButtonMenuItemOperator showRadioMenuItem(String path){
        JMenuItemOperator menuItemOperator = super.showMenuItem(path);
        return new JRadioButtonMenuItemOperator((JRadioButtonMenuItem) menuItemOperator.getSource());
    }
}
