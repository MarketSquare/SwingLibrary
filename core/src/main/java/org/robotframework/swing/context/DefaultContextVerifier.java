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

package org.robotframework.swing.context;

import java.awt.Panel;
import java.awt.Window;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

/**
 * @author Heikki Hulkko
 */
public class DefaultContextVerifier extends ContextVerifier {
    static final String ERROR_MESSAGE = "To use this keyword you must first select a correct context. Please see e.g. \"Select Dialog\" -keyword.";

    public DefaultContextVerifier() {
        super(ERROR_MESSAGE);
    }

    @Override
    protected Class[] getExpectedClasses() {
        return new Class[] { Window.class, JPanel.class, Panel.class, JInternalFrame.class };
    }
}
