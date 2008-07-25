package org.robotframework.swing.keyword.tree;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.keyword.tree.SelectTreeKeywords;
import org.robotframework.swing.tree.EnhancedTreeOperator;


@RunWith(JDaveRunner.class)
public class SelectTreeKeywordsSpec extends MockSupportSpecification<SelectTreeKeywords> {
    public class Any {
        public SelectTreeKeywords create() {
            return new SelectTreeKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasSelectTreeKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectTree")));
        }

        public void hasClearTreeSelectionKeyword() {
            specify(context, satisfies(new RobotKeywordContract("clearTreeSelection")));
        }

        public void hasOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }
    }

    public class SelectingTree {
        private String treeIdentifier = "someTree";

        public SelectTreeKeywords create() {
            return new SelectTreeKeywords();
        }

        public void selectsTree() {
            final OperatorFactory operatorFactory = injectMockToContext(OperatorFactory.class);
            final EnhancedTreeOperator treeOperator = mock(EnhancedTreeOperator.class);
            checking(new Expectations() {{
                one(operatorFactory).createOperator(treeIdentifier);
                will(returnValue(treeOperator));
            }});

            context.selectTree(treeIdentifier);
            specify(Context.getContext(), must.equal(treeOperator));
        }
    }

    public class ClearingSelection {
        private EnhancedTreeOperator treeOperator;
        private boolean verifyContextWasCalled = false;

        public SelectTreeKeywords create() {
            treeOperator = mock(EnhancedTreeOperator.class);
            Context.setContext(treeOperator);

            return new SelectTreeKeywords() {
                protected void verifyContext() {
                    verifyContextWasCalled = true;
                }
            };
        }

        public void clearsTreeSelection() {
            checking(new Expectations() {{
                one(treeOperator).clearSelection();
            }});

            context.clearTreeSelection();
            specify(verifyContextWasCalled);
        }
    }
}
