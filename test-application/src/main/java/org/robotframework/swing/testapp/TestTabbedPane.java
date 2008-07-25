package org.robotframework.swing.testapp;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;

public class TestTabbedPane extends JTabbedPane {
    public TestTabbedPane(String name) {
        setName(name);
        addTab("tab1", new JLabel("tab1"));
        addTab("tab2", new JLabel("tab2"));
        addTab("tab3", new JLabel("tab3"));
    }
}
