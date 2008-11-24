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

package org.robotframework.swing.timeout.keyword;

import org.netbeans.jemmy.JemmyProperties;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class TimeoutKeywords {
    public final static String[] JEMMY_TIMEOUTS = new String[] { "DialogWaiter.WaitDialogTimeout",
        "FrameWaiter.WaitFrameTimeout", "WindowWaiter.WaitWindowTimeout", "ComponentOperator.WaitComponentTimeout",
        "JMenuOperator.WaitPopupTimeout", "JTreeOperator.WaitNodeExpandedTimeout", "JTreeOperator.WaitNextNodeTimeout",
        "JTreeOperator.WaitNodeVisibleTimeout", "ComponentOperator.WaitStateTimeout"};

    @RobotKeyword("Sets the jemmy timeout used for waiting a component to appear.\n"
        + "Timeout names are listed here: http://jemmy.netbeans.org/OperatorsEnvironment.html#timeouts.\n\n"
        + "Example:\n"
        + "| Set Jemmy Timeout | DialogWaiter.WaitDialogTimeout | 3 |\n")
    public void setJemmyTimeout(String timeoutName, String timeoutInSeconds) {
        JemmyProperties.setCurrentTimeout(timeoutName, parseMillis(timeoutInSeconds));
    }

    @RobotKeyword("Sets all relevant jemmy timeouts. \n"
        + "By default they are all set to 5 seconds.\n\n"
        + "List of all the timeouts this keywords sets:\n"
        + "| *Timeout Name* | *Description* |\n"
        + "| DialogWaiter.WaitDialogTimeout | Time to wait dialog displayed |\n"
        + "| FrameWaiter.WaitFrameTimeout | Time to wait frame displayed |\n"
        + "| WindowWaiter.WaitWindowTimeout | Time to wait window displayed |\n"
        + "| ComponentOperator.WaitComponentTimeout | Time to wait component displayed |\n"
        + "| ComponentOperator.WaitStateTimeout | Used for example in combobox selection |\n"
        + "| JMenuOperator.WaitPopupTimeout | Time to wait popup displayed |\n"
        + "| JTreeOperator.WaitNodeExpandedTimeout | Time to wait node expanded |\n"
        + "| JTreeOperator.WaitNextNodeTimeout | Time to wait next node displayed |\n"
        + "| JTreeOperator.WaitNodeVisibleTimeout | Time to wait node visible |\n\n"
        + "Example:\n"
        + "| Set Jemmy Timeouts | 3 |\n")
    public void setJemmyTimeouts(String timeoutInSeconds) {
        for (String timeout : JEMMY_TIMEOUTS) {
            JemmyProperties.setCurrentTimeout(timeout, parseMillis(timeoutInSeconds));
        }
    }

    private long parseMillis(String timeoutInSeconds) {
        return Long.parseLong(timeoutInSeconds) * 1000;
    }
}
