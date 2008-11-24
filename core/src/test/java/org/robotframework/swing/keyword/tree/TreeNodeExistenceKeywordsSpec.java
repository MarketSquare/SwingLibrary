package org.robotframework.swing.keyword.tree;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.tree.TreeNodeExistenceResolver;
import org.robotframework.swing.tree.keyword.TreeNodeExistenceKeywords;


@RunWith(JDaveRunner.class)
public class TreeNodeExistenceKeywordsSpec extends MockSupportSpecification<TreeNodeExistenceKeywords> {
    public class Any {
        public TreeNodeExistenceKeywords create() {
            return new TreeNodeExistenceKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasTreeNodeShouldExistKeywords() {
            specify(context, satisfies(new RobotKeywordContract("treeNodeShouldExist")));
        }

        public void hasTreeNodeShouldNotExistKeywords() {
            specify(context, satisfies(new RobotKeywordContract("treeNodeShouldNotExist")));
        }
    }

    public class ResolvingExistence {
        private String nodeIdentifier = "some|node";
        private String treeIdentifier = "someTree";
        private TreeNodeExistenceResolver nodeExistenceResolver;

        public TreeNodeExistenceKeywords create() {
            nodeExistenceResolver = mock(TreeNodeExistenceResolver.class);
            
            return new TreeNodeExistenceKeywords() {
                TreeNodeExistenceResolver createExistenceResolver(String treeIdentifier) {
                    return nodeExistenceResolver;
                }
            };
        }

        public void treeNodeShouldExistPassesIfTreeNodeExists() throws Throwable {
            setNodeFound(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldExist(treeIdentifier, nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void treeNodeShouldExistFailsIfTreeNodeDoesntExist() throws Throwable {
            setNodeFound(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldExist(treeIdentifier, nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' doesn't exist."));
        }

        public void treeNodeShouldNotExistFailsIfTreeNodeExists() throws Throwable {
            setNodeFound(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotExist(treeIdentifier, nodeIdentifier);
                }
            }, must.not().raise(Exception.class));
        }


        public void treeNodeShouldNotExistFailsIfTreeNodeDoesntExist() throws Throwable {
            setNodeFound(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodeShouldNotExist(treeIdentifier, nodeIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Tree node '" + nodeIdentifier + "' exists."));
        }

        private void setNodeFound(final boolean nodeFound) {
            checking(new Expectations() {{
                one(nodeExistenceResolver).treeNodeExists(nodeIdentifier);
                will(returnValue(nodeFound));
            }});
        }
    }
}
