package org.robotframework.swing.testapp;

import javax.swing.JComboBox;

public class TestComboBox extends JComboBox {
    private static final String[] ENTRIES = new String[] { "one", "two", "three" };

    public TestComboBox() {
        super(ENTRIES);
        setName("testComboBox");
        setEditable(true);
    }
}
