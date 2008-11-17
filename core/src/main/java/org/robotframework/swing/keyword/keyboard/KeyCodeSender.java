package org.robotframework.swing.keyword.keyboard;

import abbot.tester.ComponentTester;

public class KeyCodeSender implements KeyEventSender {
    private final int keyCode;

    public KeyCodeSender(int keyCode) {
        this.keyCode = keyCode;
        
    }

    public void sendEvent(int modifiers) {
        new ComponentTester().actionKeyStroke(keyCode, 0);
    }
}
