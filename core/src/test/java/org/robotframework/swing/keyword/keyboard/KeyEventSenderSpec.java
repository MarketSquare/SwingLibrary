package org.robotframework.swing.keyword.keyboard;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import jdave.Block;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.keyword.keyboard.KeyEventSender;

import abbot.tester.ComponentTester;

@RunWith(JDaveRunner.class)
public class KeyEventSenderSpec extends MockSupportSpecification<KeyEventSender> {
    public class SendingKeyEvents {
        private ComponentTester componentTester;

        public KeyEventSender create() {
            KeyEventSender keyEventSender = new KeyEventSender();
            componentTester = injectMockTo(keyEventSender, ComponentTester.class);
            return keyEventSender;
        }
        
        public void sendsKeyStrokes() {
            checking(new Expectations() {{
                one(componentTester).actionKeyStroke(KeyEvent.VK_X, 0);
            }});
            
            context.sendEvent("VK_X", new String[0]);
        }
        
        public void sendsKeyStrokesWithModifier() {
            checking(new Expectations() {{
                one(componentTester).actionKeyStroke(KeyEvent.VK_TAB, InputEvent.SHIFT_DOWN_MASK);
            }});
            
            context.sendEvent("VK_TAB", new String[] { "SHIFT_DOWN_MASK" });
        }
        
        public void sendsKeyStrokesWithMultipleModifiers() {
            checking(new Expectations() {{
                one(componentTester).actionKeyStroke(KeyEvent.VK_Y, InputEvent.SHIFT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK);
            }});
            
            context.sendEvent("VK_Y", new String[] { "SHIFT_DOWN_MASK", "CTRL_DOWN_MASK" });
        }
        
        public void failsWhenKeyCodeIsNotFound() throws Throwable {
            String expectedMessage = "'not_a_keycode' is invalid. See java.awt.event.KeyEvent for valid fields.";
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.sendEvent("not_a_keycode", new String[0]);     
                }
            }, must.raiseExactly(IllegalArgumentException.class, expectedMessage));
        }
        
        public void failsWhenModifierIsNotFound() throws Throwable {
            String expectedMessage = "'not_a_modifier' is invalid. See java.awt.event.InputEvent for valid fields.";
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.sendEvent("VK_X", new String[] { "not_a_modifier" });     
                }
            }, must.raiseExactly(IllegalArgumentException.class, expectedMessage));
        }
    }
}
