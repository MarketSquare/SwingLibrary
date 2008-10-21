package org.robotframework.swing.keyword.tree;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;


@RunWith(JDaveRunner.class)
public class SelectTreeKeywordsSpec extends TreeSpecification<SelectTreeKeywords> {
    public class Any {
        public SelectTreeKeywords create() {
            return new SelectTreeKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasClearTreeSelectionKeyword() {
            specify(context, satisfies(new RobotKeywordContract("clearTreeSelection")));
        }
    }

    public class ClearingSelection {
        public SelectTreeKeywords create() {
            treeKeywords = new SelectTreeKeywords();
            return createKeywordsWithMockInternals();
        }

        public void clearsTreeSelection() {
            checking(new Expectations() {{
                one(treeOperator).clearSelection();
            }});

            context.clearTreeSelection(treeIdentifier);
        }
    }
}
