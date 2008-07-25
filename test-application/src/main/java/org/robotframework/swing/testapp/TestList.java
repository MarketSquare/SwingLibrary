package org.robotframework.swing.testapp;

import java.awt.Dimension;

import javax.swing.JList;

public class TestList extends JList {
    private static Object[] items = new Object[] { "one", "two", "three" };

    public TestList() {
        super(items);
        setName("testList");
        setPreferredSize(new Dimension(50, 50));
    }
}
