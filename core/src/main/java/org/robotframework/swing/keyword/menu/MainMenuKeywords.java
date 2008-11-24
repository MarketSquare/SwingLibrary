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

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.keyword.window.WindowKeywords;
import org.robotframework.swing.menu.MenuSupport;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class MainMenuKeywords extends MenuSupport {
    private WindowKeywords windowKeywords = new WindowKeywords();
    private MenuKeywords menuKeywords = new MenuKeywords();

    @RobotKeyword("Selects an item from the menu of the window that was opened first.\n"
        + "Shortcut for:\n"
        + "| Select Window    | _0_ |\n"
        + "| Select From Menu | _Tools|Testing|MyTestTool_ |\n\n"
        + "Example:\n"
        + "| Select From Main Menu | _Tools|Testing|MyTestTool_ |\n")
    public void selectFromMainMenu(final String menuPath) {
        windowKeywords.selectMainWindow();
        menuKeywords.selectFromMenu(menuPath);
    }

    @RobotKeyword("Selects an item from the menu of the window that was opened first "
        + "and waits for action to finish.\n"
        + "Shortcut for:\n"
        + "| Select Window             | _0_                        |\n"
        + "| Select From Menu And Wait | _Tools|Testing|MyTestTool_ |\n\n"
        + "This keyword will not work, for example, if the menu item opens a dialog.\n\n"
        + "Example:\n"
        + "| Select From Main Menu And Wait | _Tools|Testing|MyTestTool_ |\n")
    public void selectFromMainMenuAndWait(String menuPath) {
        windowKeywords.selectMainWindow();
        menuKeywords.selectFromMenuAndWait(menuPath);
    }


    @RobotKeyword("Selects an item from the menu of the window that was opened first and returns it's name.\n\n"
        + "Example:\n"
        + "| ${mainMenuItemName}= | Get Main Menu Item Name | _0_                   |\n"
        + "| Should Be Equal      | _File_                  | _${mainMenuItemName}_ |\n")
    public String getMainMenuItemName(String index) {
        if (!isIndex(index)) {
            throw new UnsupportedOperationException("The argument must be an index.");
        }

        return menubarOperator().getMenu(asIndex(index)).getText();
    }
}
