package org.robotframework.swing.keyword.keyboard;

import abbot.tester.ComponentTester;

public class StringKeySender implements KeyEventSender {
    private final String keyCodeAsString;

    public StringKeySender(String keyCodeAsString) {
        this.keyCodeAsString = keyCodeAsString;
        
    }

    public void sendEvent(int modifiers) {
        new ComponentTester().actionKeyString(keyCodeAsString);
    }
}
