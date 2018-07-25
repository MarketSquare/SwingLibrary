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

import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.keyword.window.WindowKeywords;
import org.robotframework.swing.menu.MenuSupport;

import java.util.ArrayList;
import java.util.List;

@RobotKeywords
public class MainMenuKeywords extends MenuSupport {
    private WindowKeywords windowKeywords = new WindowKeywords();
    private MenuKeywords menuKeywords = new MenuKeywords();

    @RobotKeyword("Selects an item from the menu of the window that was opened first.\n"
        + "Shortcut for:\n"
        + "| `Select Window`    | 0 |\n"
        + "| `Select From Menu` | Tools|Testing|MyTestTool |\n\n"
        + "Example:\n"
        + "| `Select From Main Menu` | Tools|Testing|MyTestTool |\n")
    @ArgumentNames({"menuPath"})
    public void selectFromMainMenu(final String menuPath) {
        windowKeywords.selectMainWindow();
        menuKeywords.selectFromMenu(menuPath);
    }

    @RobotKeyword("Selects an item from the menu of the window that was opened first "
        + "and waits for action to finish.\n"
        + "Shortcut for:\n"
        + "| `Select Window`             | 0                        |\n"
        + "| `Select From Menu And Wait` | Tools|Testing|MyTestTool |\n\n"
        + "This keyword will not work, for example, if the menu item opens a dialog.\n\n"
        + "Example:\n"
        + "| `Select From Main Menu And Wait` | Tools|Testing|MyTestTool |\n")
    @ArgumentNames({"menuPath"})
    public void selectFromMainMenuAndWait(String menuPath) {
        windowKeywords.selectMainWindow();
        menuKeywords.selectFromMenuAndWait(menuPath);
    }


    @RobotKeyword("Selects an item from the menu of the window that was opened first and returns it's name.\n\n"
        + "Example:\n"
        + "| ${mainMenuItemName}= | `Get Main Menu Item Name` | 0                   |\n"
        + "| `Should Be Equal`    | File                      | ${mainMenuItemName} |\n")
    @ArgumentNames({"index"})
    public String getMainMenuItemName(String index) {
        if (!isIndex(index)) {
            throw new UnsupportedOperationException("The argument must be an index.");
        }

        return menubarOperator().getMenu(asIndex(index)).getText();
    }


    @RobotKeyword("Selects all items from the menu of the window that was opened first and returns their names.\n\n"
        + "Example:\n"
        + "| @{mainMenuItemNames}= | `Get Main Menu Item Names` |\n"
        + "| `Should Contain` | @{mainMenuItemNames} | File |\n")
    public List<String> getMainMenuItemNames() {
        JMenuBarOperator mbo = menubarOperator();
        List<String> returnable = new ArrayList<String>();
        for (int index = 0; index < mbo.getMenuCount(); index++) {
            returnable.add(mbo.getMenu(index).getText());
        }
        return returnable;
    }

    @RobotKeyword("Fails if menu item doesn't exist in the window that was opened first.\n"
        + "Shortcut for:\n"
        + "| `Select Window`    | 0 |\n"
        + "| `Menu Item Should Exist` | Tools|Testing|MyTestTool |\n\n"
        + "Example:\n"
        + "| `Main Menu Item Should Exist` | Tools|Testing|Test Tool |\n")
    @ArgumentNames({"menuPath"})
    public void mainMenuItemShouldExist(String menuPath) {
        windowKeywords.selectMainWindow();
        menuKeywords.menuItemShouldExist(menuPath);
    }

    @RobotKeyword("Fails if menu item exists in the window that was opened first.\n"
        + "Shortcut for:\n"
        + "| `Select Window`    | 0 |\n"
        + "| `Menu Item Should Not Exist` | Tools|Testing|MyTestTool |\n\n"
        + "Example:\n"
        + "| `Main Menu Item Should Not Exist` | Tools|Testing|Test Tool |\n")
    @ArgumentNames({"menuPath"})
    public void mainMenuItemShouldNotExist(String menuPath) {
        windowKeywords.selectMainWindow();
        menuKeywords.menuItemShouldNotExist(menuPath);
    }
}
