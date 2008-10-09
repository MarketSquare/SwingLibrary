package org.robotframework.swing.testapp;

import java.awt.Dimension;

import javax.swing.JSpinner;

public class TestSpinnerButton extends JSpinner {
    public TestSpinnerButton() {
        setName("testSpinner");
        setPreferredSize(new Dimension(40, 20));
    }
}
