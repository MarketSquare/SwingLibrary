package org.robotframework.swing.testapp;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class TestNullPointerException extends JButton {
    private static final String INITIAL_TEXT = "Test NullPointerException";
    private static final String BUTTON_PUSHED_TEXT = "Button Was Pushed";

    public TestNullPointerException() {
        super(INITIAL_TEXT);
        setName("testNullPointer");
        setToolTipText("testToolTip");
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    setText(BUTTON_PUSHED_TEXT + e.getClickCount());
                    throw new NullPointerException("This is a test for null pointer exception");
            }
        });
    }
}
