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

package org.robotframework.swing.testapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import org.robotframework.javalib.util.KeywordNameNormalizer;

public class MenuItemWithCommand extends JMenuItem implements ActionListener {
    public MenuItemWithCommand(String text) {
        super(text);
        setName(new KeywordNameNormalizer().normalize(text));
        addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not implemented.");
    }
}
