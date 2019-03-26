package org.robotframework.swing.testapp;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

import javax.swing.JButton;

public class TestButton extends JButton {
    private static final String INITIAL_TEXT = "Test Button";
    private static final String BUTTON_PUSHED_TEXT = "Button Was Pushed";
    private static final String RIGHT_CLICKED_TEXT = "Right Clicked";
    private static final String CLICK_SHIFT_TEXT = "Click With Shift";

    public TestButton() {
        super(INITIAL_TEXT);
        setName("testButton");
        setToolTipText("testToolTip");
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1)
                    setText(BUTTON_PUSHED_TEXT + e.getClickCount());
                if(e.getButton() == MouseEvent.BUTTON1 && e.isShiftDown())
                    setText(CLICK_SHIFT_TEXT);
                if (e.getButton() == MouseEvent.BUTTON3)
                    setText(RIGHT_CLICKED_TEXT);
            }
        });
    }
}
