package org.robotframework.swing.testapp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class TestTabbedPane extends JTabbedPane {
    public TestTabbedPane(String name, TestTextField textField) {
        setName(name);
        for (int i = 0 ; i < 3 ; i++)
            addTab("tab"+(i+1), createPanel(name, textField, i));
    }

    private JPanel createPanel(String name, TestTextField textField, int i) {
        JPanel panel = new JPanel();
        panel.add(createButton(name+".button" + (i+1), textField));
        panel.setName(name+".jpanel"+i);
        return panel;
    }
    
    private JButton createButton(final String buttonName, final TestTextField textField) {
        JButton button = new JButton("ButtonInTab") {{
            addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    textField.setText(buttonName);
                }
            });
        }};
        button.setName(buttonName);
        return button;
    }
}
