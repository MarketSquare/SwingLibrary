package org.robotframework.swing.keyword.tree;

import jdave.Block;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.jdave.contract.RobotKeywordContract;


@RunWith(JDaveRunner.class)
public class TreeNodeSelectionKeywordsSpec extends TreeSpecification<TreeNodeSelectionKeywords> {
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
        private String nodeIdentifier = "some|node";

        public TreeNodeSelectionKeywords create() {
            return populateWithMockOperatorFactory(new TreeNodeSelectionKeywords());
        }

        public void treeNodeShouldBeSelectedPassesIfTreeNodeIsSelected() throws Throwable {
            setPathSelected(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeSelected(treeIdentifier, nodeIdentifier, "None");
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldBeSelectedFailsIfTreeNodeIsNotSelected() throws Throwable {
            setPathSelected(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldBeSelected(treeIdentifier, nodeIdentifier, "None");
                }
            }, must.raiseExactly(AssertionError.class, "Tree node '" + nodeIdentifier + "' is not selected."));
        }

        public void treeNodeShouldNotBeSelectedPassesIfTreeNodeIsNotSelected() throws Throwable {
            setPathSelected(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeSelected(treeIdentifier, nodeIdentifier, "None", "None");
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldNotBeSelectedFailsIfTreeNodeIsSelected() throws Throwable {
            setPathSelected(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotBeSelected(treeIdentifier, nodeIdentifier, "None", "None");
                }
            }, must.raiseExactly(AssertionError.class, "Tree node '" + nodeIdentifier + "' is selected."));
        }

        private void setPathSelected(final boolean isSelected) {
            checking(new Expectations() {{
                one(treeOperator).isPathSelected(nodeIdentifier); will(returnValue(isSelected));
            }});
        }
    }
}
