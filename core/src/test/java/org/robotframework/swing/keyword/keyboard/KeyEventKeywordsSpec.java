package org.robotframework.swing.keyword.keyboard;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.keyboard.KeyEventSender;
import org.robotframework.swing.keyword.MockSupportSpecification;

@RunWith(JDaveRunner.class)
public class KeyEventKeywordsSpec extends MockSupportSpecification<KeyEventKeywords> {
    public class Any {
        public KeyEventKeywords create () {
            return new KeyEventKeywords();
        }
        
        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }
        
        public void hasSendKeyboardEventKeyword() {
            specify(context, satisfies(new RobotKeywordContract("sendKeyboardEvent")));
        }
        
        public void hasKeyEventSender() {
            specify(context, satisfies(new FieldIsNotNullContract("keyEventSender")));
        }
    }
    
    public class SendingKeyEvents {
        private KeyEventSender keyEventSender;

        public KeyEventKeywords create() {
            KeyEventKeywords keyEventKeywords = new KeyEventKeywords();
            keyEventSender = injectMockTo(keyEventKeywords, KeyEventSender.class);
            return keyEventKeywords;
        }
        
        public void sendsKeyEvents() {
            final String keyCode = "VK_TAB";
            final String[] modifiers = new String[] { "SHIFT_DOWN_MASK" };
            
            checking(new Expectations() {{
                one(keyEventSender).sendEvent(keyCode, modifiers);
            }});
            
            context.sendKeyboardEvent(keyCode, modifiers);
        }
    }
}
