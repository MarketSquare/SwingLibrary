package org.robotframework.swing.testapp;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class TestButton extends JButton {
    private static final String INITIAL_TEXT = "Test Button";
    private static final String BUTTON_PUSHED_TEXT = "Button Was Pushed";

    public TestButton() {
        super(INITIAL_TEXT);
        setName("testButton");
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (INITIAL_TEXT.equals(getText())) {
                    setText(BUTTON_PUSHED_TEXT);
                } else {
                    setText(INITIAL_TEXT);
                }
            }
        });
    }
}
