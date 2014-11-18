package org.robotframework.swing.testapp;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// The point of this class is to spam the event queue constantly. This prevents
// simply waiting forever for the queue to be empty.
public class TestFastUpdatingPane extends JScrollPane {

    public TestFastUpdatingPane() {
        super();
        setName("testFastUpdatingPane");
        setPreferredSize(new Dimension(40, 40));
        JPanel content = new JPanel(new GridLayout(0,1));
        // We need some content to replicate problem wait jemmy.EventTool.waitNoEvent(
        content.add(new JComboBox(new String[]{"filler", "b", "c"}));
        setViewportView(content);
        registerUpdater();
    }

    public void increaseTicker() {
        Color color = getBackground();
        if (!color.equals(Color.black))
            color = Color.black;
        else
            color = Color.white;
        setBackground(color);
        repaint();
    }

    public void registerUpdater() {
        ActionListener updateClockAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                increaseTicker();
            }
        };
        Timer t = new Timer(100, updateClockAction);
        t.start();
    }
}
