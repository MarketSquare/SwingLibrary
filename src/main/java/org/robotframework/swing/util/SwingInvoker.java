package org.robotframework.swing.util;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public class SwingInvoker {

    public static void postEvent(final AWTEvent event) {
        invokeAndWait(new Runnable() {
            @Override
            public void run() {
                Toolkit.getDefaultToolkit().getSystemEventQueue()
                        .postEvent(event);
            }
        });
    }

    public static void invokeAndWait(final Runnable runnable) {
        try {
            SwingUtilities.invokeAndWait(runnable);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            throw new RuntimeException(cause != null ? cause.getMessage() : e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
