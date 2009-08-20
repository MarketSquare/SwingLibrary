package org.robotframework.swing.testapp;

import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class OtherTree extends JTree {
    public OtherTree() {
        super(new TreeModel() {
            public void addTreeModelListener(TreeModelListener l) {
                
            }

            public Object getChild(Object parent, int index) {
                return null;
            }

            public int getChildCount(Object parent) {
                return 0;
            }

            public int getIndexOfChild(Object parent, Object child) {
                return 0;
            }

            public Object getRoot() {
                return "root";
            }

            public boolean isLeaf(Object node) {
                return true;
            }

            public void removeTreeModelListener(TreeModelListener l) {
                
            }

            public void valueForPathChanged(TreePath path, Object newValue) {
                
            }
            
        });
        setName("otherTree");
    }
}
