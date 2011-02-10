package org.robotframework.examplesut.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Bind {
    private ButtonListener listener;
    private Bind(ButtonListener listener) {
        this.listener = listener;
    }
    
    public static Bind a(ButtonListener listener) {
        return new Bind(listener);
    }
    
    public void to(JButton button) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                listener.onButtonPush();
            }
        });
    }
}
