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

package org.robotframework.swing.keyword.timeout;

import org.netbeans.jemmy.JemmyProperties;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.common.TimeoutName;

@RobotKeywords
public class TimeoutKeywords {
    public final static String[] JEMMY_TIMEOUTS = new String[] {
        TimeoutName.DIALOG_WAITER_WAIT_DIALOG_TIMEOUT,
        TimeoutName.FRAME_WAITER_WAIT_FRAME_TIMEOUT,
        TimeoutName.WINDOW_WAITER_WAIT_WINDOW_TIMEOUT,
        TimeoutName.COMPONENT_OPERATOR_WAIT_COMPONENT_TIMEOUT,
        TimeoutName.COMPONENT_OPERATOR_WAIT_COMPONENT_ENABLED_TIMEOUT,
        TimeoutName.J_MENU_OPERATOR_WAIT_POPUP_TIMEOUT,
        TimeoutName.J_TREE_OPERATOR_WAIT_NODE_EXPANDED_TIMEOUT,
        TimeoutName.J_TREE_OPERATOR_WAIT_NEXT_NODE_TIMEOUT,
        TimeoutName.J_TREE_OPERATOR_WAIT_NODE_VISIBLE_TIMEOUT,
        TimeoutName.COMPONENT_OPERATOR_WAIT_STATE_TIMEOUT,
        TimeoutName.J_COMBO_BOX_OPERATOR_WAIT_LIST_TIMEOUT,
        TimeoutName.J_COMBOBOX_OPERATOR_WAIT_GET_SELECTED_ITEM_TIMEOUT,
        TimeoutName.J_LIST_OPERATOR_WAIT_FIND_ITEM_INDEX_TIMEOUT };

    @RobotKeyword("Sets the jemmy timeout used for waiting a component to appear.\n"
        + "Timeout names are listed here: http://jemmy.java.net/OperatorsEnvironment.html#timeouts\n"
        + "Returns the old timeout setting value.\n\n"
        + "This keyword by default takes timeout value is seconds, "
        + "you can however suffix it with ``ms`` to provide it in milliseconds.\n"
        + "Return value will always be in units you used for the argument.\n\n"
        + "Example:\n"
        + "| `Set Jemmy Timeout` | DialogWaiter.WaitDialogTimeout | 3 |\n"
        + "| ${oldSetting}= | `Set Jemmy Timeout` | DialogWaiter.WaitDialogTimeout | 3 |\n")
    @ArgumentNames({"timeoutName", "timeout"})
    public long setJemmyTimeout(String timeoutName, String timeout) {
        long multiplier = 1000;
        if (timeout.endsWith("ms")) {
            timeout = timeout.replace("ms", "");
            timeout = timeout.trim();
            multiplier = 1;
        }
        long oldTimeout = JemmyProperties.getCurrentTimeout(timeoutName);
        JemmyProperties.setCurrentTimeout(timeoutName, parseMillis(timeout, multiplier));
        return (oldTimeout/multiplier);
    }

    @RobotKeyword("Sets all relevant jemmy timeouts. \n"
        + "By default they are all set to 5 seconds.\n\n"
        + "This keyword by default takes timeout value is seconds, "
        + "you can however suffix it with ``ms`` to provide it in milliseconds.\n\n"
        + "List of all the timeouts this keywords sets:\n"
        + "| *Timeout Name* | *Description* |\n"
        + "| "+TimeoutName.DIALOG_WAITER_WAIT_DIALOG_TIMEOUT+" | Time to wait dialog displayed |\n"
        + "| "+TimeoutName.FRAME_WAITER_WAIT_FRAME_TIMEOUT+" | Time to wait frame displayed |\n"
        + "| "+TimeoutName.WINDOW_WAITER_WAIT_WINDOW_TIMEOUT+" | Time to wait window displayed |\n"
        + "| "+TimeoutName.COMPONENT_OPERATOR_WAIT_COMPONENT_TIMEOUT+" | Time to wait component displayed |\n"
        + "| "+TimeoutName.COMPONENT_OPERATOR_WAIT_COMPONENT_ENABLED_TIMEOUT+" | Time to wait component enabled |\n"
        + "| "+TimeoutName.COMPONENT_OPERATOR_WAIT_STATE_TIMEOUT+" | Used for example in combobox selection |\n"
        + "| "+TimeoutName.J_COMBOBOX_OPERATOR_WAIT_GET_SELECTED_ITEM_TIMEOUT+" | Time to wait for combobox selected item fetching |\n"
        + "| "+TimeoutName.J_COMBO_BOX_OPERATOR_WAIT_LIST_TIMEOUT+" | Time to wait list opened | \n"
        + "| "+TimeoutName.J_MENU_OPERATOR_WAIT_POPUP_TIMEOUT+" | Time to wait popup displayed |\n"
        + "| "+TimeoutName.J_TREE_OPERATOR_WAIT_NODE_EXPANDED_TIMEOUT+" | Time to wait node expanded |\n"
        + "| "+TimeoutName.J_TREE_OPERATOR_WAIT_NEXT_NODE_TIMEOUT+" | Time to wait next node displayed |\n"
        + "| "+TimeoutName.J_TREE_OPERATOR_WAIT_NODE_VISIBLE_TIMEOUT+" | Time to wait node visible |\n"
        + "| "+TimeoutName.J_LIST_OPERATOR_WAIT_FIND_ITEM_INDEX_TIMEOUT+" | Time to wait for list item to appear |\n\n"
        + "Example:\n"
        + "| `Set Jemmy Timeouts` | 3 |\n")
    @ArgumentNames({"timeout"})
    public void setJemmyTimeouts(String timeout) {
        long multiplier = 1000;
        if (timeout.endsWith("ms")) {
            timeout = timeout.replace("ms", "");
            timeout = timeout.trim();
            multiplier = 1;
        }
        for (String timeoutType : JEMMY_TIMEOUTS) {
            JemmyProperties.setCurrentTimeout(timeoutType, parseMillis(timeout, multiplier));
        }
    }

    private long parseMillis(String timeout, long multiplier) {
        return Long.parseLong(timeout) * multiplier;
    }
}
