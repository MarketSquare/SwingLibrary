package org.robotframework.swing.testapp;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

public class SystemExitButton extends JButton {
    public SystemExitButton() {
        super("systemExitButton");
        setName("systemExitButton");
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
    }
}
