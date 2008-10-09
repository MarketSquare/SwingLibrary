package org.robotframework.swing.keyword.list;

import javax.swing.ListModel;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JListOperator.ListItemChooser;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.operator.list.MyListOperator;


@RunWith(JDaveRunner.class)
public class ListKeywordsSpec extends MockSupportSpecification<ListKeywords> {
    public class Any {
        public ListKeywords create() {
            return new ListKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasOperatorFactory() throws Throwable {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void hasContextVerifier() throws Throwable {
            specify(context, satisfies(new FieldIsNotNullContract("contextVerifier")));
        }

        public void hasClearSelectionFromListKeyword() {
            specify(context, satisfies(new RobotKeywordContract("clearSelectionFromList")));
        }

        public void hasSelectFromListKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromList")));
        }

        public void hasGetSelectedValueFromListKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getSelectedValueFromList")));
        }

        public void hasGetListItemCountKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getListItemCount")));
        }
    }

    public class WhenOperating {
        private String listIdentifier = "someList";
        private OperatorFactory<MyListOperator> operatorFactory;
        private MyListOperator listOperator;
        private IContextVerifier contextVerifier;

        public ListKeywords create() {
            ListKeywords listKeywords = new ListKeywords();
            operatorFactory = injectMockTo(listKeywords, OperatorFactory.class);
            listOperator = mock(MyListOperator.class);

            contextVerifier = injectMockTo(listKeywords, "contextVerifier", IContextVerifier.class);
            checking(new Expectations() {{
                one(operatorFactory).createOperator(with(equal(listIdentifier)));
                will(returnValue(listOperator));

                one(contextVerifier).verifyContext();
            }});

            return listKeywords;
        }

        public void clearsSelectionFromList() {
            checking(new Expectations() {{
                one(listOperator).clearSelection();
            }});

            context.clearSelectionFromList(listIdentifier);
        }

        public void selectsFromList() {
            final String numericIdentifier = "2";

            checking(new Expectations() {{
                one(contextVerifier).verifyContext();

                one(operatorFactory).createOperator(listIdentifier);
                will(returnValue(listOperator));

                one(listOperator).findItemIndex(with(any(ListItemChooser.class)));
                will(returnValue(2));

                one(listOperator).selectItem(2);
                one(listOperator).selectItem(Integer.parseInt(numericIdentifier));
            }});

            context.selectFromList(listIdentifier, "someListItem");
            context.selectFromList(listIdentifier, numericIdentifier);
        }

        public void getsSelectedValueFromList() {
            final String selectedValue = "someResult";

            checking(new Expectations() {{
                one(listOperator).getSelectedValue();
                will(returnValue(selectedValue));
            }});

            specify(context.getSelectedValueFromList(listIdentifier), must.equal(selectedValue));
        }

        public void getsListItemCount() {
            final ListModel listModel = mock(ListModel.class);
            checking(new Expectations() {{
                one(listOperator).getModel(); will(returnValue(listModel));
                one(listModel).getSize(); will(returnValue(4));
            }});

            specify(context.getListItemCount(listIdentifier), must.equal(4));
        }
    }
}
