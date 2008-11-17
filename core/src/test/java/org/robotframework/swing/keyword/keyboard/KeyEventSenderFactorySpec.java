package org.robotframework.swing.keyword.keyboard;

import static org.hamcrest.Matchers.is;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class KeyEventSenderFactorySpec extends Specification<KeyEventSenderFactory> {
    public class Any {
        public KeyEventSenderFactory create() {
            return new KeyEventSenderFactory();
        }
        
        public void createsKeyCodeSenderForKeyCodes() {
            List<String> keyCodes = getKeyCodes();
            for (String keyCode : keyCodes) {
                specify(context.createKeyEventSender(keyCode), is(KeyCodeSender.class));
            }
        }

        public void createsStringKeySenderForOthers() {
            specify(context.createKeyEventSender("CHAR_UNDEFINED"), is(StringKeySender.class));
        }
        
        private List<String> getKeyCodes() {
            Field[] fields = KeyEvent.class.getFields();
            List<String> keyCodes = new ArrayList<String>();
            for (Field field : fields) {
                if (field.getName().startsWith("VK_"))
                    keyCodes.add(field.getName());
            }
            return keyCodes;
        }
    }
}
