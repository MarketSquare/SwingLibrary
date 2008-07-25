package org.robotframework.swing.testapp;

import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TestTable extends JScrollPane {
    private static Object[] COLUMN_HEADERS = new Object[] { "column one", "column two", "column three" };

    private static Object[][] TABLE_MODEL = new Object[][] {
        new Object[] { "one/one", "one/two", "one/three" },
        new Object[] { "two/one", "two/two", "two/three" },
        new Object[] { "three/one", "three/two", "three/three" }
    };

    public TestTable() {
        super(createTable());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 100);
    }

    private static JTable createTable() {
        JTable table = new JTable(TABLE_MODEL, COLUMN_HEADERS);
        table.setName("testTable");
        return table;
    }
}
