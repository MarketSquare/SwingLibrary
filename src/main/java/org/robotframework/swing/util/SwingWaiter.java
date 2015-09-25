package org.robotframework.swing.util;

import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.TimeoutExpiredException;

/**
 * This class provides an interface for waiting between swing library events.
 * Originally we used EventTool.waitNoEvent from jemmy without any event filter.
 * This is bad(tm) because some applications cause events continuously.
 *
 * Now trying to wait for maximum 600ms second (or given time).
 */
public class SwingWaiter {

    private static EventTool eventTool = new EventTool();

    public static void waitToAvoidInstability(int time) {
        try {
            eventTool.getTimeouts().setTimeout("EventTool.WaitNoEventTimeout", Math.max(600, time));
            eventTool.waitNoEvent(time);
        } catch (TimeoutExpiredException e) {
            // Ignore
        }
    }
}
