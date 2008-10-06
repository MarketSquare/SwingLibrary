package org.robotframework.swing.keyword.tree;

import javax.swing.JTree;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.keyword.tree.TreeNodeKeywords;
import org.robotframework.swing.tree.EnhancedTreeOperator;
import org.robotframework.swing.tree.TreePathFactory;


@RunWith(JDaveRunner.class)
public class TreeNodeKeywordsSpec extends MockSupportSpecification<TreeNodeKeywords> {
    public class Any {
        public TreeNodeKeywords create() {
            return new TreeNodeKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasCollapseTreeNodeKeyword() {
            specify(context, satisfies(new RobotKeywordContract("collapseTreeNode")));
        }

        public void hasExpandTreeNodeKeyword() {
            specify(context, satisfies(new RobotKeywordContract("expandTreeNode")));
        }

        public void hasSelectTreeNodeKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectTreeNode")));
        }

        public void hasTreeNodeShouldBeExpandedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("treeNodeShouldBeExpanded")));
        }

        public void hasTreeNodeShouldBeCollapsedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("treeNodeShouldBeCollapsed")));
        }

        public void hasUnselectTreeNodeKeyword() {
            specify(context, satisfies(new RobotKeywordContract("unselectTreeNode")));
        }

        public void hasTreeNodeShouldBeLeafKeyword() {
            specify(context, satisfies(new RobotKeywordContract("treeNodeShouldBeLeaf")));
        }

        public void hasTreeNodeShouldNotBeLeafKeyword() {
            specify(context, satisfies(new RobotKeywordContract("treeNodeShouldNotBeLeaf")));
        }
    }

    public class OperatingOnTree {
        private EnhancedTreeOperator treeOperator;
        private String nodeIdentifier = "path|to|node";
        private TreePath treePath = mock(TreePath.class);

        public TreeNodeKeywords create() {
            treeOperator = mock(EnhancedTreeOperator.class);
            Context.setContext(treeOperator);

            TreeNodeKeywords treeKeywords = new TreeNodeKeywords();

            final TreePathFactory treePathFactory = injectMockTo(treeKeywords, TreePathFactory.class);
            checking(new Expectations() {{
                one(treePathFactory).createTreePath(nodeIdentifier);
                will(returnValue(treePath));

                allowing(treeOperator).getSource(); will(returnValue(dummy(JTree.class)));
            }});

            return treeKeywords;
        }

        public void destroy() {
            Context.setContext(null);
        }

        public void collapsesTreeNode() {
            checking(new Expectations() {{
                one(treeOperator).collapsePath(treePath);
            }});

            context.collapseTreeNode(nodeIdentifier);
        }

        public void expandsTreeNode() {
            checking(new Expectations() {{
                one(treeOperator).expandPath(treePath);
            }});

            context.expandTreeNode(nodeIdentifier);
        }

        public void selectsTreeNode() {
            checking(new Expectations() {{
                one(treeOperator).addSelectionPath(treePath);
            }});

            context.selectTreeNode(nodeIdentifier);
        }

        public void unselectsTreeNode() {
            checking(new Expectations() {{
                one(treeOperator).removeSelectionPath(treePath);
            }});

            context.unselectTreeNode(nodeIdentifier);
        }

        public void treeNodeShouldBeExpandedPassesIfTreeNodeIsExpanded() throws Throwable {
            checking(new Expectations() {{
                one(treeOperator).isExpanded(treePath); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeExpanded(nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldBeExpandedFailsIfTreeNodeIsNotExpanded() throws Throwable {
            checking(new Expectations() {{
                one(treeOperator).isExpanded(treePath); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeExpanded(nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' is not expanded."));
        }

        public void treeNodeShouldBeCollapsedPassesIfTreeNodeIsCollapsed() throws Throwable {
            checking(new Expectations() {{
                one(treeOperator).isCollapsed(treePath); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeCollapsed(nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldBeCollapsedFailsIfTreeNodeIsNotCollapsed() throws Throwable {
            checking(new Expectations() {{
                one(treeOperator).isCollapsed(treePath); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeCollapsed(nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' is not collapsed."));
        }

        public void treeNodeShouldBeLeafPassesIfNodeIsLeaf() throws Throwable {
            setNodeIsLeaf(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeLeaf(nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldBeLeafFailsIfNodeIsNotLeaf() throws Throwable {
            setNodeIsLeaf(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeLeaf(nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' is not leaf."));
        }

        public void treeNodeShouldNotBeLeafPassesIfNodeIsNotLeaf() throws Throwable {
            setNodeIsLeaf(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeLeaf(nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldNotBeLeafFailsIfNodeIsLeaf() throws Throwable {
            setNodeIsLeaf(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeLeaf(nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' is leaf."));
        }

        private void setNodeIsLeaf(final boolean isLeaf) {
            final TreeNode treeNode = mock(TreeNode.class);
            checking(new Expectations() {{
                one(treePath).getLastPathComponent(); will(returnValue(treeNode));
                one(treeNode).isLeaf(); will(returnValue(isLeaf));
            }});
        }
    }
}

