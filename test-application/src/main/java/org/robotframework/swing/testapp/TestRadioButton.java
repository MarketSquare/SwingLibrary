package org.robotframework.swing.testapp;

import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class TestRadioButton extends JPanel {
    public TestRadioButton() {
        ButtonGroup buttonGroup = new ButtonGroup() {{
            add(new JRadioButton("One") {{ setName("one"); }});
            add(new JRadioButton("Two") {{ setName("two"); }});
            add(new JRadioButton("Three") {{ setName("three"); }});
        }};
        
        Enumeration<AbstractButton> elements = buttonGroup.getElements();
        while (elements.hasMoreElements())
            add(elements.nextElement());
    }
}
