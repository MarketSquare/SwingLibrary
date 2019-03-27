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

package org.robotframework.swing.component;

import javax.swing.JComponent;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.QueueTool;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JComponentOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.swing.popup.PopupMenuOperatorFactory;

import java.awt.event.InputEvent;

public class ComponentOperator extends JComponentOperator {
    private PopupMenuOperatorFactory popupMenuOperatorFactory = new PopupMenuOperatorFactory();
    
    public ComponentOperator(ContainerOperator cont, ComponentChooser chooser) {
        super(cont, chooser);
    }
    
    public ComponentOperator(ContainerOperator container, int index) {
        super(container, index);
    }
    
    ComponentOperator(JComponent source) {
        super(source);
    }

    public JPopupMenuOperator invokePopup() {
        return popupMenuOperatorFactory.createPopupOperator(this);
    }

    public void clickOnComponent(final String clickCount, final String mouseButton, final String[] keyModifiers) {
        this.getQueueTool().invokeSmoothly(new QueueTool.QueueAction("Choice expanding") {
            public Object launch() {
                ComponentOperator.this.clickMouse(ComponentOperator.this.getCenterXForClick(), ComponentOperator.this.getCenterYForClick(),
                        Integer.parseInt(clickCount), toInputEventMask(mouseButton), toCombinedInputEventMasks(keyModifiers));
                return null;
            }
        });
    }

    private int toInputEventMask(String inputEventFieldName) {
        try {
            return InputEvent.class.getField(inputEventFieldName).getInt(null);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("No field name '"
                    + inputEventFieldName + "' in class "
                    + InputEvent.class.getName() + ".");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int toCombinedInputEventMasks(String[] modifierStrings) {
        int combinedInputEventMask = 0;
        if (modifierStrings.length > 0)
            for (String modifierAsString : modifierStrings)
                combinedInputEventMask |= toInputEventMask(modifierAsString);
        return combinedInputEventMask;
    }
}
