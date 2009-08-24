package org.robotframework.swing.testapp;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class TreeWithoutTreeNode extends JTree {
    private static Node root =
        new Node("root",
            new Node("node1"), 
            new Node("node2"),
            new Node("node3",
                new Node("node4"),
                new Node("node5",
                    new Node("node6")
                )
            ),
            new Node("node7")
        );
    
    public TreeWithoutTreeNode() {
        super(new MyTreeModel(root));
        setName("otherTree");
    }
}

class MyTreeModel implements TreeModel {
    private final Node root;
    
    MyTreeModel(Node root) {
        this.root = root;
    }
    
    public Object getChild(Object parent, int index) {
        return ((Node)parent).getChild(index);
    }

    public int getChildCount(Object parent) {
        return ((Node)parent).getChildCount();
    }

    public int getIndexOfChild(Object parent, Object child) {
        return ((Node)parent).indexOf(child);
    }

    public Object getRoot() {
        return root;
    }

    public boolean isLeaf(Object node) {
        return ((Node)node).isLeaf();
    }

    public void addTreeModelListener(TreeModelListener l) {}
    public void removeTreeModelListener(TreeModelListener l) {}
    public void valueForPathChanged(TreePath path, Object newValue) {}
}

class Node {
    private List<Node> childNodes = new ArrayList<Node>();
    private final String name;
    
    public Node(String name) {
        this.name = name;
    }
    
    public Node(String name, Node... children) {
        this(name);
        for (Node node : children) {
            childNodes.add(node);
        }
    }
    
    public Node getChild(int index) {
        return childNodes.get(index);
    }
    
    public int getChildCount() {
        return childNodes.size();
    }
    
    public int indexOf(Object child) {
        return childNodes.indexOf(child);
    }
    
    public boolean isLeaf() {
        return childNodes.isEmpty();
    }
    
    @Override
    public String toString() {
        return name;
    }
}