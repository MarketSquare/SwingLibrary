package org.robotframework.swing.testapp;

import java.awt.Dimension;

import javax.swing.JTextArea;

public class TestTextArea extends JTextArea {
    public TestTextArea() {
        setName("testTextArea");
        setPreferredSize(new Dimension(100, 50));
    }
}
