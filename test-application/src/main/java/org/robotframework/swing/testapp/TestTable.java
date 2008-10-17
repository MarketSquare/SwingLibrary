package org.robotframework.swing.testapp;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TestTable extends JScrollPane {
    private static Object[] COLUMN_HEADERS = new Object[] { "column one", "column two", "column three", "column four" };

    private static Object[][] TABLE_MODEL = new Object[][] {
        new Object[] { "one/one", "one/two", "one/three", "one/four" },
        new Object[] { "two/one", "two/two", "two/three", "two/four" },
        new Object[] { "three/one", "three/two", "three/three", "three/four" },
        new Object[] { "four/one", "four/two", "four/three", "four/four" }
    };

    public TestTable(String name) {
        super(createTable(name));
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 100);
    }

    private static JTable createTable(String name) {
        JTable table = new JTable(TABLE_MODEL, COLUMN_HEADERS);
        table.setName(name);
        return table;
    }
}
