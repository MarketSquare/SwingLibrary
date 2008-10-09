package org.robotframework.swing.keyword.tab;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.operator.tab.MyTabbedPaneOperator;

@RunWith(JDaveRunner.class)
public class TabKeywordsSpec extends MockSupportSpecification<TabKeywords> {
    private TabKeywords tabKeywords = new TabKeywords();
    private MyTabbedPaneOperator operator;

    public class Any {
        public TabKeywords create() {
            return tabKeywords;
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasSelectTabKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectTab")));
        }

        public void hasGetSelectedTabNameKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getSelectedTabLabel")));
        }

        public void hasSelectTabPaneKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectTabPane")));
        }

        public void hasOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }
    }

    public class Operating {
        private TabPaneOperatorFactory operatorFactory;
        private String tabName = "someTab";
        private String tabIndex = "2";

        public TabKeywords create() {
            operator = mock(MyTabbedPaneOperator.class);
            injectMockOperatorFactory();
            return tabKeywords;
        }

        public void selectsTabFromFirstTabbedPaneWhenTabbedPaneIdentifierOmittedByName() {
            checking(new Expectations() {{
                one(operator).selectPage(tabName);
            }});
            context.selectTab(tabName);
        }

        public void selectsTabFromFirstTabbedPaneWhenTabbedPaneIdentifierOmittedByIndex() {
            checking(new Expectations() {{
                one(operator).selectPage(Integer.parseInt(tabIndex));
            }});
            context.selectTab(tabIndex);
        }

        public void getsTabNameFromFirstTabbedPaneWhenTabbedPaneIdentifierOmitted() {
            checking(new Expectations() {{
                one(operator).getSelectedIndex(); will(returnValue(1));
                one(operator).getTitleAt(1); will(returnValue(tabName));
            }});

            specify(context.getSelectedTabLabel(), must.equal(tabName));
        }

        private void injectMockOperatorFactory() {
            operatorFactory = injectMockTo(tabKeywords, "operatorFactory", TabPaneOperatorFactory.class);
            checking(new Expectations() {{
                one(operatorFactory).createOperatorFromContext();
                will(returnValue(operator));
            }});
        }
    }

    public class SelectingTabPane {
        private String tabPaneName = "someTabPane";
        private TabPaneOperatorFactory operatorFactory;

        public TabKeywords create() {
            operator = mock(MyTabbedPaneOperator.class);
            operatorFactory = injectMockTo(tabKeywords, "operatorFactory", TabPaneOperatorFactory.class);
            return tabKeywords;
        }

        public void selectTab() {
            checking(new Expectations() {{
                one(operatorFactory).createOperator(tabPaneName);
                will(returnValue(operator));
            }});

            context.selectTabPane(tabPaneName);
            specify(Context.getContext(), must.equal(operator));
        }
    }
}
