package org.robotframework.swing.util;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public class SwingInvoker {

    public void invokeAndWait(final AWTEvent event) {
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    Toolkit.getDefaultToolkit().getSystemEventQueue()
                            .postEvent(event);
                }
            });
        } catch (InvocationTargetException e) {
        } catch (InterruptedException e) {
        }
    }
}
