package org.robotframework.swing.testapp;

import java.awt.Dimension;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;

@SuppressWarnings("serial")
public class TestSpinnerButton extends JSpinner {
    public TestSpinnerButton(String name) {
        setName(name);
        setPreferredSize(new Dimension(80, 20));
    }

    public TestSpinnerButton(String name, SpinnerModel model) {
    	this(name);
    	setModel(model);
    }
}
