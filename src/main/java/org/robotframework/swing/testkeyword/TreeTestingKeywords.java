package org.robotframework.swing.testkeyword;

import javax.swing.tree.TreePath;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.javalib.util.ArrayUtil;
import org.robotframework.swing.testapp.TestTreeResults;
import org.robotframework.swing.tree.TreeOperator;
import org.robotframework.swing.tree.TreePathAction;
import org.robotframework.swing.tree.TreeSupport;

@RobotKeywords
public class TreeTestingKeywords extends TreeSupport {
    @RobotKeyword
    public void clearSavedNodes() {
        TestTreeResults.nodes.clear();
    }
    
    @RobotKeyword
    public void savedNodesShouldBe(String[] expectedNodes) {
        ArrayUtil.assertArraysContainSame(expectedNodes, TestTreeResults.nodes.toArray(new String[0]));
    }
    
    @RobotKeyword
    public void clickedNodesShouldBe(String[] expectedNodes) {
        savedNodesShouldBe(expectedNodes);
    }
    
    @RobotKeyword
    public void clickCountShouldBe(String expectedCount) {
        Assert.assertEquals(Integer.parseInt(expectedCount), TestTreeResults.clickCount);
    }
    
    @RobotKeyword
    public void allTreeNodesShouldBeExpanded(String identifier) {
        final TreeOperator treeOperator = treeOperator(identifier);
        treeOperator.operateOnAllNodes(new TreePathAction() {
            public void operate(TreePath path) {
                Assert.assertTrue(treeOperator.isLeaf(path) || treeOperator.isExpanded(path));
            }
        });
    }
    
    @RobotKeyword
    public void allTreeNodesShouldBeCollapsed(String identifier) {
        final TreeOperator treeOperator = treeOperator(identifier);
        treeOperator.operateOnAllNodes(new TreePathAction() {
            public void operate(TreePath path) {
                Assert.assertTrue(treeOperator.isCollapsed(path));
            }
        });
    }
}
