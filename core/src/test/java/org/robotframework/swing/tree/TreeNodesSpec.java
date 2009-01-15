package org.robotframework.swing.tree;

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.keyword.MockSupportSpecification;

@RunWith(JDaveRunner.class)
public class TreeNodesSpec extends MockSupportSpecification<Void> {
    public class Any {
        private TreeNode root;
        private TreeNode node1;
        private TreeNode node2;
        private TreeNode node3;

        public void create() {
            root = mock(TreeNode.class, "root");
            node1 = mock(TreeNode.class, "some");
            node2 = mock(TreeNode.class, "tree");
            node3 = mock(TreeNode.class, "node");
            
            createTree(node1, node2, node3);
        }

        public void extractsTreePath() {
            TreeNodes treeNodes = new TreeNodes(root, true); 
            
            specify(treeNodes.extractTreePath("root|some|tree|node"), must.equal(expectedTreePath()));
        }

        
        public void extractsTreePathWithInvisibleRoot() {
            TreeNodes treeNodes = new TreeNodes(root, false);
            
            specify(treeNodes.extractTreePath("some|tree|node"), must.equal(expectedTreePath()));   
        }
        
        private void createTree(final TreeNode... nodes) {
            final Enumeration<TreeNode> treeElems = new Vector<TreeNode>() {{
                for (TreeNode node : nodes) {
                    add(node);
                }
            }}.elements();
            
            checking(new Expectations() {{
                one(root).children(); will(returnValue(treeElems));
                one(node1).children(); will(returnValue(treeElems));
                one(node2).children(); will(returnValue(treeElems));
                one(node3).children(); will(returnValue(treeElems));
            }});
        }
        
        private TreePath expectedTreePath() {
            return new TreePath(new TreeNode[] {root, node1, node2, node3});
        }
    }
}
