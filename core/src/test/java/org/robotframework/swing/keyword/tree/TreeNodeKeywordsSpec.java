package org.robotframework.swing.keyword.tree;

import javax.swing.tree.TreePath;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;


@RunWith(JDaveRunner.class)
public class TreeNodeKeywordsSpec extends TreeSpecification<TreeNodeKeywords> {
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
        private TreePath treePath = mock(TreePath.class);
        private String nodeIdentifier = "path|to|node";
        
        public TreeNodeKeywords create() {
            return populateWithMockOperatingFactoryAndContextVerifier(new TreeNodeKeywords());
        }
        
        public void collapsesTreeNode() {
            checking(new Expectations() {{
                one(treeOperator).collapse(nodeIdentifier);
            }});
            
            context.collapseTreeNode(treeIdentifier, nodeIdentifier);
        }
        
        public void expandsTreeNode() {
            checking(new Expectations() {{
                one(treeOperator).expand(nodeIdentifier);
            }});
            
            context.expandTreeNode(treeIdentifier, nodeIdentifier);
        }
        
        public void selectsTreeNode() {
            checking(new Expectations() {{
                one(treeOperator).addSelection(nodeIdentifier);
            }});
            
            context.selectTreeNode(treeIdentifier, nodeIdentifier);
        }
        
        public void unselectsTreeNode() {
            checking(new Expectations() {{
                one(treeOperator).removeSelection(nodeIdentifier);
            }});
            
            context.unselectTreeNode(treeIdentifier, nodeIdentifier);
        }
        
        public void treeNodeShouldBeExpandedPassesIfTreeNodeIsExpanded() throws Throwable {
            checking(new Expectations() {{
                one(treeOperator).isExpanded(nodeIdentifier); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeExpanded(treeIdentifier, nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }
        
        public void treeNodeShouldBeExpandedFailsIfTreeNodeIsNotExpanded() throws Throwable {
            checking(new Expectations() {{
                one(treeOperator).isExpanded(nodeIdentifier); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeExpanded(treeIdentifier, nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' is not expanded."));
        }
        
        public void treeNodeShouldBeCollapsedPassesIfTreeNodeIsCollapsed() throws Throwable {
            checking(new Expectations() {{
                one(treeOperator).isCollapsed(nodeIdentifier); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeCollapsed(treeIdentifier, nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }
        
        public void treeNodeShouldBeCollapsedFailsIfTreeNodeIsNotCollapsed() throws Throwable {
            checking(new Expectations() {{
                one(treeOperator).isCollapsed(nodeIdentifier); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeCollapsed(treeIdentifier, nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' is not collapsed."));
        }
        
        public void treeNodeShouldBeLeafPassesIfNodeIsLeaf() throws Throwable {
            checking(new Expectations() {{
                one(treeOperator).isLeaf(nodeIdentifier); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeLeaf(treeIdentifier, nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }
        
        public void treeNodeShouldBeLeafFailsIfNodeIsNotLeaf() throws Throwable {
            checking(new Expectations() {{
                one(treeOperator).isLeaf(nodeIdentifier); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeLeaf(treeIdentifier, nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' is not leaf."));
        }
        
        public void treeNodeShouldNotBeLeafPassesIfNodeIsNotLeaf() throws Throwable {
            checking(new Expectations() {{
                one(treeOperator).isLeaf(nodeIdentifier); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeLeaf(treeIdentifier, nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }
        
        public void treeNodeShouldNotBeLeafFailsIfNodeIsLeaf() throws Throwable {
            checking(new Expectations() {{
                one(treeOperator).isLeaf(nodeIdentifier); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeLeaf(treeIdentifier, nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' is leaf."));
        }
    }
}

