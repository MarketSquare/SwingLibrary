package org.robotframework.swing.keyword.tree;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;

@RunWith(JDaveRunner.class)
public class TreeNodeVisibilityKeywordsSpec extends TreeSpecification<TreeNodeVisibilityKeywords> {
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
            return new TreeNodeVisibilityKeywords();
        }

        public void treeNodeShouldBeVisibleWithRowIndexFails() {
            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeVisible(treeIdentifier, "2");
                }
            }, must.raiseExactly(IllegalArgumentException.class, "Node's visibility cannot be checked by it's index."));
        }

        public void treeNodeShouldNotBeVisibleWithRowIndexFails() {
            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeVisible(treeIdentifier, "2");
                }
            }, must.raiseExactly(IllegalArgumentException.class, "Node's visibility cannot be checked by it's index."));
        }
    }

    public class CheckingVisibilityWithNodePath {
        private String nodeIdentifier = "some|node";

        public TreeNodeVisibilityKeywords create() {
            return populateWithMockOperatorFactory(new TreeNodeVisibilityKeywords());
        }

        public void treeNodeShouldBeVisibleWithNodePathPassesIfTreeNodeIsVisible() throws Throwable {
            setIsVisible(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeVisible(treeIdentifier, nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldBeVisibleWithNodePathFailsIfTreeNodeIsNotVisible() throws Throwable {
            setIsVisible(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeVisible(treeIdentifier, nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' is not visible."));
        }

        public void treeNodeShouldNotBeVisibleWithNodePathPassesIfTreeNodeIsNotVisible() throws Throwable {
            setIsVisible(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeVisible(treeIdentifier, nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldBeVisibleWithNodePathFailsIfTreeNodeIsVisible() throws Throwable {
            setIsVisible(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeVisible(treeIdentifier, nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' is visible."));
        }

        private void setIsVisible(final boolean isVisible) {
            checking(new Expectations() {{
                one(treeOperator).isVisible(nodeIdentifier); will(returnValue(isVisible));
            }});
        }
    }
}
