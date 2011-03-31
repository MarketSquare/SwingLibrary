/*
 * Copyright 2008-2011 Nokia Siemens Networks Oyj
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

package org.robotframework.swing.keyboard;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import abbot.tester.ComponentTester;

public class KeyEventSender {
    private ComponentTester componentTester = new ComponentTester();

    public void sendEvent(String keyCodeAsString, String[] modifiersAsString) {
        int keyCode = toKeyCode(keyCodeAsString);
        int modifiers = toModifiers(modifiersAsString);
        sendEvent(keyCode, modifiers);
    }
    
    private int toKeyCode(String keyCodeAsString) {
        return getIntValueFromField(KeyEvent.class, keyCodeAsString);
    }
    
    private int getIntValueFromField(Class<?> target, String fieldName) {
        try {
            return target.getField(fieldName).getInt(null);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("'" + fieldName + "' is invalid. See " + target.getName() + " for valid fields.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    private int toModifiers(String[] modifiersAsString) {
        int modifiers = 0;
        for (String modifierAsString : modifiersAsString) {
            modifiers |= toModifier(modifierAsString);
        }
        return modifiers;
    }

    private int toModifier(String modifierAsString) {
        return getIntValueFromField(InputEvent.class, modifierAsString);
    }

    private void sendEvent(int keyCode, int modifiers) {
        componentTester.actionKeyStroke(keyCode, modifiers);
    }
}
