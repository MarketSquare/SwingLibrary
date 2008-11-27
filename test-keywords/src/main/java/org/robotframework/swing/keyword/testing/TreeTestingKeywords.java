package org.robotframework.swing.keyword.testing;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.testapp.TestTreeResults;
import org.robotframework.swing.tree.TreeOperator;
import org.robotframework.swing.tree.TreeOperatorFactory;

@RobotKeywords
public class TreeTestingKeywords {
    @RobotKeyword("Adds the specified amount of children to the selected node, fails if no nodes are selected.\n.")
    public void addChildren(String childCount) {
        TreeOperator treeOperator = new TreeOperatorFactory().createOperatorByIndex(0);
        DefaultMutableTreeNode lastPathComponent = (DefaultMutableTreeNode) treeOperator.getSelectionPath()
            .getLastPathComponent();
        
        for (int i = 0; i < Integer.parseInt(childCount); i++) {
            lastPathComponent.add(new DefaultMutableTreeNode("child" + i));
        }
        ((DefaultTreeModel) treeOperator.getModel()).nodeStructureChanged(lastPathComponent);
    }
    
    @RobotKeyword("For testing 'SelectFromPopupMenuOnSelectedTreeNodes'.\n")
    public List<String> getSavedNodes() {
        return TestTreeResults.nodes;
    }
    
    @RobotKeyword
    public void clearSavedNodes() {
        TestTreeResults.nodes.clear();
    }
}
