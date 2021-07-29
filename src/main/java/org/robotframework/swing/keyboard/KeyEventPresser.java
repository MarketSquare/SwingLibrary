package org.robotframework.swing.keyboard;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * This class was created using methods from abbot.tester.Robot.
 * Due to a Java 8 update (u301), sun.awt.SunToolkit.InfiniteLoop
 * was removed entirely and using abbot's Robot would throw a
 * ClassNotFoundException.
 */

public class KeyEventPresser extends Robot {

    public KeyEventPresser() throws AWTException {
    }

    public void press(int keycode, int modifiers) {
        boolean isModifier = true;
        switch (keycode) {
            case KeyEvent.VK_ALT_GRAPH:
                modifiers |= InputEvent.ALT_GRAPH_MASK;
                break;
            case KeyEvent.VK_ALT:
                modifiers |= InputEvent.ALT_MASK;
                break;
            case KeyEvent.VK_SHIFT:
                modifiers |= InputEvent.SHIFT_MASK;
                break;
            case KeyEvent.VK_CONTROL:
                modifiers |= InputEvent.CTRL_MASK;
                break;
            case KeyEvent.VK_META:
                modifiers |= InputEvent.META_MASK;
                break;
            default:
                isModifier = false;
                break;
        }
        setModifiers(modifiers, true);
        if (!isModifier) {
            this.keyPress(keycode);
            this.keyRelease(keycode);
        }
        setModifiers(modifiers, false);
    }

    private void setModifiers(int modifiers, boolean press) {
        boolean altGraph = (modifiers & InputEvent.ALT_GRAPH_MASK) != 0;
        boolean shift = (modifiers & InputEvent.SHIFT_MASK) != 0;
        boolean alt = (modifiers & InputEvent.ALT_MASK) != 0;
        boolean ctrl = (modifiers & InputEvent.CTRL_MASK) != 0;
        boolean meta = (modifiers & InputEvent.META_MASK) != 0;
        if (press) {
            if (altGraph) this.keyPress(KeyEvent.VK_ALT_GRAPH);
            if (alt) this.keyPress(KeyEvent.VK_ALT);
            if (shift) this.keyPress(KeyEvent.VK_SHIFT);
            if (ctrl) this.keyPress(KeyEvent.VK_CONTROL);
            if (meta) this.keyPress(KeyEvent.VK_META);
        } else {
            // For consistency, release in the reverse order of press
            if (meta) this.keyRelease(KeyEvent.VK_META);
            if (ctrl) this.keyRelease(KeyEvent.VK_CONTROL);
            if (shift) this.keyRelease(KeyEvent.VK_SHIFT);
            if (alt) this.keyRelease(KeyEvent.VK_ALT);
            if (altGraph) this.keyRelease(KeyEvent.VK_ALT_GRAPH);
        }
    }
}
