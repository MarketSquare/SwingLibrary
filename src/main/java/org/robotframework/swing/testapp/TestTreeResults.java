package org.robotframework.swing.testapp;

import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreePath;

public class TestTreeResults {
    public static List<String> nodes = new ArrayList<String>();
    public static int clickCount;
    public static void saveNodes(TreePath[] selectionPaths) {
        nodes.clear();
        for (TreePath treePath : selectionPaths) {
            nodes.add(treePath.getLastPathComponent().toString().toLowerCase());
        }
    }
}
