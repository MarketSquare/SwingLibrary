package org.robotframework.swing.testapp;

import java.awt.Dimension;

import javax.swing.JTextField;

@WithPopup
public class TestTextField extends JTextField {
    public TestTextField() {
        super(System.getProperty("testApp.secret"));
        setName("testTextField");
        setPreferredSize(new Dimension(100, 30));
    }
}
