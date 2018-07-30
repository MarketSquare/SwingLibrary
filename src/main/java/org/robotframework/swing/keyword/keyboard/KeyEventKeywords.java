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

package org.robotframework.swing.keyword.keyboard;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.keyboard.KeyEventSender;

@RobotKeywords
public class KeyEventKeywords {
    private KeyEventSender keyEventSender = new KeyEventSender();
    
    @RobotKeyword("Sends keystrokes to the currently selected component.\n"
    	+ "Keystroke codes must be one of the mappings in ``abbot.tester.KeyStrokeMap`` (https://sourceforge.net/p/abbot/svn/1214/tree/trunk/abbot/src/abbot/tester/KeyStrokeMap.java#l110).\n"
    	+ "Constants from ``java.awt.event.InputEvent`` can be used as optional modifiers.\n\n"
        + "Examples with textfield:\n"
        + "| `Focus To Component`    | myTextfield          |                      |\n"
        + "| `Send Keyboard Event`   | VK_S                 |                      |\n"
        + "| `Send Keyboard Event`   | VK_O                 |                      |\n"
        + "| `Send Keyboard Event`   | VK_M                 |                      |\n"
        + "| `Send Keyboard Event`   | VK_E                 |                      |\n"
        + "| `Send Keyboard Event`   | VK_T                 | SHIFT_MASK           |\n"
        + "| `Send Keyboard Event`   | VK_E                 |                      |\n"
        + "| `Send Keyboard Event`   | VK_X                 |                      |\n"
        + "| `Send Keyboard Event`   | VK_T                 |                      |\n"
        + "| ${textFieldContents}=   | `Get Text Field Value` | myTextfield        |\n"
        + "| `Should Be Equal`       | someText             | ${textFieldContents} |\n\n"
        + "Examples with table:\n"
        + "| `SelectTableCell`               | myTable | 0          | 0 |\n"
        + "| `Send Keyboard Event`           | VK_TAB  |            |   |\n"
        + "| `Table Cell Should Be Selected` | myTable | 0          | 1 |\n"
        + "| `Send Keyboard Event`           | VK_TAB  | SHIFT_MASK |   |\n"
        + "| `Table Cell Should Be Selected` | myTable | 0          | 0 |\n")
    @ArgumentNames({"keyCode", "*modifiers"})
    public void sendKeyboardEvent(String keyCode, String[] modifiers) {
        keyEventSender.sendEvent(keyCode, modifiers);
    }
}
