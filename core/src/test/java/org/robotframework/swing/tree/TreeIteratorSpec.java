package org.robotframework.swing.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JTreeOperator;

@RunWith(JDaveRunner.class)
public class TreeIteratorSpec extends Specification<TreeIterator> {
    public class Any {
        private JTreeOperator tree;
        private TreePath root;

        public TreeIterator create() {
            tree = mock(JTreeOperator.class);
            root = mock(TreePath.class, "rootPath");
            return new TreeIterator(tree) {
                TreePath root() {
                    return root;
                }
            };
        }
        
        public void actsOnAllNodes() {
            final TreePath childPath = mock(TreePath.class, "otherPath");
            final TreeNode someNode = mock(TreeNode.class);
            final TreeNode otherNode = dummy(TreeNode.class, "otherNode");
            final Enumeration children = new Vector() {{
                add(otherNode);
            }}.elements();
            
            checking(new Expectations() {{
                one(root).getLastPathComponent(); will(returnValue(someNode));
                one(someNode).children(); will(returnValue(children));
                
                one(root).pathByAddingChild(otherNode); will(returnValue(childPath));
                one(childPath).getLastPathComponent(); will(returnValue(otherNode));
            }});
            
            final List<String> paths = new ArrayList<String>();
            context.operateOnAllNodes(new TreePathAction() {
                public void operate(TreePath node) {
                    paths.add(node.toString());
                }
            });
            
            specify(paths, containsInOrder("otherPath", "rootPath"));
        }
    }
}
