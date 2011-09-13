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

package org.robotframework.swing.keyword.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.netbeans.jemmy.drivers.DriverManager;
import org.robotframework.swing.button.ButtonOperator;

public class ButtonOperatorWrapper {

    private final ButtonOperator buttonOperator;
    private final ClickListener clickListener = new ClickListener();

    public ButtonOperatorWrapper(ButtonOperator buttonOperator) {
        buttonOperator.addActionListener(clickListener);
        this.buttonOperator = buttonOperator;
    }

    public void push() {
        // This is a workaround for behavior described in
        // http://code.google.com/p/robotframework-swinglibrary/issues/detail?id=191
        waitButtonIsEnabled();
        clickIfNecessary();
    }

    private void waitButtonIsEnabled() {
        buttonOperator.makeComponentVisible();
        try {
            buttonOperator.waitComponentEnabled();
        } catch (InterruptedException e) {
            throw new RuntimeException("Wait for buttion interrupted", e);
        }
    }

    private void clickIfNecessary() {
        if (!clickListener.clicked)
            DriverManager.getButtonDriver(buttonOperator).push(buttonOperator);
    }
}

class ClickListener implements ActionListener {
    public boolean clicked = false;

    public void actionPerformed(ActionEvent e) {
        clicked = true;
    }
}