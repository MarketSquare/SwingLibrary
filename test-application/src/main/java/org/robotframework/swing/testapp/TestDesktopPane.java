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

import java.awt.Dimension;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class TestDesktopPane extends JDesktopPane {
    public static final TestDesktopPane INSTANCE = new TestDesktopPane();
    private JInternalFrame internalFrame = new JInternalFrame("Test Internal Frame",
        true, true, true, true) {{
            setSize(300, 150);
            setName("testInternalFrame");
            setDefaultCloseOperation(HIDE_ON_CLOSE);
        }};

    private TestDesktopPane() {
        setName("testDesktopPane");
        add(internalFrame);
        showInternalFrame();
    }
    
    public void showInternalFrame() {
        internalFrame.setVisible(true);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 300);
    }
}
