/*
 * Copyright 2008 Nokia Siemens Networks Oyj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.robotframework.swing.keyword.keyboard;

import java.awt.event.KeyEvent;
import java.lang.reflect.Field;

/**
 * @author Heikki Hulkko
 */
public class KeyEventSenderFactory {
    public KeyEventSender createKeyEventSender(String keyCodeAsString) {
        if (isKeyCode(keyCodeAsString))
            return new KeyCodeSender(getKeyCode(keyCodeAsString));
        return new StringKeySender(keyCodeAsString);
    }

    private boolean isKeyCode(String keyCodeAsString) {
        try {
            Field field = getKeyEventField(keyCodeAsString);
            if (field.getName().startsWith("VK_"))
                return true;
        } catch (Exception e) { 
            // IGNORED
        }
        return false;
    }
    
    private int getKeyCode(String keyCodeAsString) {
        try {
            return getKeyEventField(keyCodeAsString).getInt(null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Field getKeyEventField(String keyCodeAsString) throws NoSuchFieldException {
        return KeyEvent.class.getField(keyCodeAsString);
    }
}
