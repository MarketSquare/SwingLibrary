package org.robotframework.swing.testapp;

import java.awt.Dimension;

import javax.swing.JTextField;

public class TestTextField extends JTextField {
    public TestTextField() {
        super("Test Text Field");
        setName("testTextField");
        setPreferredSize(new Dimension(100, 30));
    }
}
