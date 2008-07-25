package org.robotframework.swing.keyword.tree;

import javax.swing.tree.TreePath;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.keyword.tree.TreeNodeSelectionKeywords;
import org.robotframework.swing.tree.EnhancedTreeOperator;


@RunWith(JDaveRunner.class)
public class TreeNodeSelectionKeywordsSpec extends MockSupportSpecification<TreeNodeSelectionKeywords> {
    public class Any {
        public TreeNodeSelectionKeywords create() {
            return new TreeNodeSelectionKeywords();
        }

        public void hasTreeNodeShouldBeSelectedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("treeNodeShouldBeSelected")));
        }

        public void hasTreeNodeShouldNotBeSelectedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("treeNodeShouldNotBeSelected")));
        }
    }

    public class CheckingSelection {
        private EnhancedTreeOperator treeOperator;
        private String nodeIdentifier = "some|node";
        private TreePath treePath = dummy(TreePath.class);

        public TreeNodeSelectionKeywords create() {
            treeOperator = mock(EnhancedTreeOperator.class);
            Context.setContext(treeOperator);
            TreeNodeSelectionKeywords treeNodeSelectionKeywords = createTreeKeywordsWithContextVerifier();
            return treeNodeSelectionKeywords;
        }

        public void treeNodeShouldBeSelectedPassesIfTreeNodeIsSelectedByRowIndex() throws Throwable {
            setRowSelected(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeSelected("2");
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldBeSelectedPassesIfTreeNodeIsSelectedByNodePath() throws Throwable {
            setPathSelected(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeSelected(nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldBeSelectedFailsIfTreeNodeIsNotSelectedByRowIndex() throws Throwable {
            setRowSelected(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeSelected("2");
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '2' is not selected."));
        }

        public void treeNodeShouldBeSelectedFailsIfTreeNodeIsNotSelectedByNodePath() throws Throwable {
            setPathSelected(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeSelected(nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' is not selected."));
        }

        public void treeNodeShouldNotBeSelectedPassesIfTreeNodeIsNotSelectedByNodePath() throws Throwable {
            setPathSelected(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeSelected(nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldNotBeSelectedFailsIfTreeNodeIsSelectedByNodePath() throws Throwable {
            setPathSelected(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeSelected(nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' is selected."));
        }

        public void treeNodeShouldNotBeSelectedFailsIfTreeNodeIsSelectedByRow() throws Throwable {
            setRowSelected(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeSelected("2");
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '2' is selected."));
        }

        public void treeNodeShouldNotBeSelectedPassesIfTreeNodeIsNotSelectedByRow() throws Throwable {
            setRowSelected(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeSelected("2");
                }
            }, must.not().raise(Exception.class));
        }

        private void setPathSelected(final boolean isSelected) {
            checking(new Expectations() {{
                one(treeOperator).findPath(nodeIdentifier); will(returnValue(treePath));
                one(treeOperator).isPathSelected(treePath); will(returnValue(isSelected));
            }});
        }

        private void setRowSelected(final boolean isRowSelected) {
            checking(new Expectations() {{
                one(treeOperator).isRowSelected(2);
                will(returnValue(isRowSelected));
            }});
        }
    }

    private boolean verifyContextCalled = true;

    private TreeNodeSelectionKeywords createTreeKeywordsWithContextVerifier() {
        return new TreeNodeSelectionKeywords() {
            { verifyContextCalled = false; }

            @Override
            protected void verifyContext() {
                verifyContextCalled = true;
            }
        };
    }

    @Override
    public void verifyMocks() {
        super.verifyMocks();
        specify(verifyContextCalled);
    }
}
