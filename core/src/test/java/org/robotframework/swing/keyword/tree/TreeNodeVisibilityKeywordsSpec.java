package org.robotframework.swing.keyword.tree;

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
import org.robotframework.swing.keyword.tree.TreeNodeVisibilityKeywords;
import org.robotframework.swing.tree.EnhancedTreeOperator;


@RunWith(JDaveRunner.class)
public class TreeNodeVisibilityKeywordsSpec extends MockSupportSpecification<TreeNodeVisibilityKeywords> {
    public class Any {
        public TreeNodeVisibilityKeywords create() {
            return new TreeNodeVisibilityKeywords();
        }

        public void isRobotKeywordAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasTreeNodeShouldBeVisibleKeyword() {
            specify(context, satisfies(new RobotKeywordContract("treeNodeShouldBeVisible")));
        }

        public void hasTreeNodeShouldNotBeVisibleKeyword() {
            specify(context, satisfies(new RobotKeywordContract("treeNodeShouldNotBeVisible")));
        }
    }

    public class CheckingVisibilityWithRowIndex {
        public TreeNodeVisibilityKeywords create() {
            return createTreeKeywordsWithContextVerifier();
        }

        public void treeNodeShouldBeVisibleWithRowIndexFails() {
            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeVisible("2");
                }
            }, must.raiseExactly(IllegalArgumentException.class, "Node's visibility cannot be checked by it's index by it's index"));
        }

        public void treeNodeShouldNotBeVisibleWithRowIndexFails() {
            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeVisible("2");
                }
            }, must.raiseExactly(IllegalArgumentException.class, "Node's visibility cannot be checked by it's index by it's index"));
        }
    }

    public class CheckingVisibilityWithNodePath {
        private String nodePathAsString = "some|node";
        private TreePath nodePath = dummy(TreePath.class);
        private EnhancedTreeOperator treeOperator;

        public TreeNodeVisibilityKeywords create() {
            treeOperator = mock(EnhancedTreeOperator.class);
            Context.setContext(treeOperator);

            checking(new Expectations() {{
                one(treeOperator).findPath(nodePathAsString); will(returnValue(nodePath));
            }});

            return createTreeKeywordsWithContextVerifier();
        }

        public void treeNodeShouldBeVisibleWithNodePathPassesIfTreeNodeIsVisible() throws Throwable {
            setIsVisible(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeVisible(nodePathAsString);
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldBeVisibleWithNodePathFailsIfTreeNodeIsNotVisible() throws Throwable {
            setIsVisible(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeVisible(nodePathAsString);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodePathAsString + "' is not visible."));
        }

        public void treeNodeShouldNotBeVisibleWithNodePathPassesIfTreeNodeIsNotVisible() throws Throwable {
            setIsVisible(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeVisible(nodePathAsString);
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldBeVisibleWithNodePathFailsIfTreeNodeIsVisible() throws Throwable {
            setIsVisible(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeVisible(nodePathAsString);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodePathAsString + "' is visible."));
        }

        private void setIsVisible(final boolean isVisible) {
            checking(new Expectations() {{
                one(treeOperator).isVisible(nodePath); will(returnValue(isVisible));
            }});
        }
    }

    private boolean verifyContextCalled = true;

    private TreeNodeVisibilityKeywords createTreeKeywordsWithContextVerifier() {
        return new TreeNodeVisibilityKeywords() {
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
