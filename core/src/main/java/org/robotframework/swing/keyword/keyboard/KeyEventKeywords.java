package org.robotframework.swing.keyword.keyboard;

import java.awt.event.KeyEvent;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class KeyEventKeywords {
    @RobotKeyword
    @ArgumentNames({"keyCode", "*modifiers"})
    public void sendKeyEvent(String keyCodeAsString, String[] modifiers) throws Exception {
        createEventSender(keyCodeAsString).sendEvent(0);
    }

    private KeyEventSender createEventSender(String keyCodeAsString) {
        try {
            return new KeyCodeSender(KeyEvent.class.getField(keyCodeAsString).getInt(null));
        } catch (NoSuchFieldException e) {
            return new StringKeySender(keyCodeAsString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
