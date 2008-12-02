package org.robotframework.swing.testapp;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

public class TestList extends JList {
    private static Object[] items = new Object[] { "one", "two", "three" };

    public TestList() {
        super(items);
        setName("testList");
        setPreferredSize(new Dimension(50, 50));
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int index = locationToIndex(e.getPoint());
                Object selectedElement = getModel().getElementAt(index);
                TestListResults.selectedElement = selectedElement;
                TestListResults.clickCount = e.getClickCount();
            }
        });
    }
}
