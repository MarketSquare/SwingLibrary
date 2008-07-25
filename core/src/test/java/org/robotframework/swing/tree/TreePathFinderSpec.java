package org.robotframework.swing.tree;

import java.awt.Component;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JTreeOperator;
import org.netbeans.jemmy.operators.Operator;
import org.robotframework.swing.tree.TreePathFinder;


@RunWith(JDaveRunner.class)
public class TreePathFinderSpec extends Specification<TreePathFinder> {
    public class Any {
        private TreeNode root = mock(TreeNode.class, "root");
        private TreeNode node1 = mock(TreeNode.class, "some");
        private TreeNode node2 = mock(TreeNode.class, "node");
        private TreeNode node3 = mock(TreeNode.class, "somewhere");
        private Enumeration children;
        private JTreeOperator treeOperator;
        private String[] treePathAsArray = new String[] {"root", "some", "node", "somewhere"};

        public TreePathFinder create() {
            children = new Vector() {{ add(node1); add(node2); add(node3); }}.elements();
            treeOperator = mock(JTreeOperator.class);

            checking(new Expectations() {{
                one(treeOperator).getRoot(); will(returnValue(root));

                allowing(root).children(); will(returnValue(children));
                allowing(node1).children(); will(returnValue(children));
                allowing(node2).children(); will(returnValue(children));
                allowing(node3).children(); will(returnValue(children));
            }});

            return new TreePathFinder(treeOperator);
        }

        public void findsPathWhenRootIsVisible() {
            final String pathAsString = "root|some|node|somewhere";

            checking(new Expectations() {{
                one(treeOperator).parseString(pathAsString);
                String[] parsePath = parsePath(pathAsString);
                will(returnValue(parsePath));

                one(treeOperator).isRootVisible(); will(returnValue(true));
            }});

            specifyPathsEqual(context.findPath(pathAsString), treePathAsArray);
        }


        public void findsPathWhenRootIsNotVisible() {
            final String pathAsString = "some|node|somewhere";

            checking(new Expectations() {{
                one(treeOperator).parseString(pathAsString);
                String[] parsePath = parsePath(pathAsString);
                will(returnValue(parsePath));

                one(treeOperator).isRootVisible(); will(returnValue(false));
            }});

            specifyPathsEqual(context.findPath(pathAsString), treePathAsArray);
        }

        private void specifyPathsEqual(TreePath findPath, String[] expected) {
            Object[] path = findPath.getPath();
            for (int i = 0; i < path.length; i++) {
                specify(path[i].toString().equals(expected[i]));
            }
        }

        private String[] parsePath(String pathAsString) {
            return new Operator() {
                public Component getSource() {
                    throw new UnsupportedOperationException("Unsupported operation");
                }
            }.parseString(pathAsString);
        }
    }
}
