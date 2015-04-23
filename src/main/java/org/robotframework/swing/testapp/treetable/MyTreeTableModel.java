package org.robotframework.swing.testapp.treetable;

import java.util.*;

public class MyTreeTableModel extends AbstractTreeTableModel {

    // Names of the columns.
    static protected String[] cNames = {"Column 1", "Column 2",
            "Column 3", "Column 4"};
    // Types of the columns.
    static protected Class[]  cTypes = { TreeTableModel.class, String.class,
            String.class, String.class };
    static Entry rootEntry;

    /******** Entry class represents the parent and leaf nodes **********/
    static class Entry {
        private String name;
        private boolean isLeaf;
        private Vector children = new Vector();

        public Entry(String name, boolean isLeaf) {
            this.name = name;
            this.isLeaf = isLeaf;
        }

        public Vector getChildren() {
            return children;
        }

        public boolean isLeaf() {
            return isLeaf;
        }

        public String getName() {
            return name;
        }

        public String toString() {
            return name;
        }
    }

    static {
        rootEntry = new Entry("rootentry", false);
        rootEntry.getChildren().addElement(new Entry("test1", true));
        rootEntry.getChildren().addElement(new Entry("test2", true));

        Entry subEntry1 = new Entry("subentry1", false);
        subEntry1.getChildren().addElement(new Entry("test3", true));
        subEntry1.getChildren().addElement(new Entry("test4", true));
        Entry subEntry2 = new Entry("subentry2", false);
        subEntry2.getChildren().addElement(new Entry("test5", true));
        subEntry2.getChildren().addElement(new Entry("test6", true));
        rootEntry.getChildren().addElement(subEntry1);
        rootEntry.getChildren().addElement(subEntry2);
    }
    /*************************************/

    public MyTreeTableModel() {
        super(rootEntry);
    }

    public int getChildCount(Object node) {
        if (!((Entry) node).isLeaf()) {
            return ((Entry) node).getChildren().size();
        }
        return 0;
    }

    public Object getChild(Object node, int i) {
        return ((Entry) node).getChildren().elementAt(i);
    }

    public boolean isLeaf(Object node) {
        return ((Entry) node).isLeaf();
    }

    public int getColumnCount() {
        return cNames.length;
    }

    public String getColumnName(int column) {
        return cNames[column];
    }

    public Class getColumnClass(int column) {
        return cTypes[column];
    }

    public Object getValueAt(Object node, int column) {
        switch(column) {
            case 0:
                return node;
            case 1:
                return "value1";
            case 2:
                return "value2";
            case 3:
                return "value3";
        }

        return null;
    }
}