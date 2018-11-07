package org.robotframework.swing.keyword.list;

import java.util.ArrayList;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.list.ListOperator;


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
        
        public void hasSelectAllListItemsKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectAllListItems")));
        }
        
        public void hasClickOnListItemKeyword() {
            specify(context, satisfies(new RobotKeywordContract("clickOnListItem")));
        }
        
        public void hasSelectFromListItemPopupMenuKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromListItemPopupMenu")));
        }
    }

    public class Operating {
        private String listIdentifier = "someList";
        private OperatorFactory<?> operatorFactory;
        private ListOperator listOperator;

        public ListKeywords create() {
            ListKeywords listKeywords = new ListKeywords();
            operatorFactory = injectMockTo(listKeywords, OperatorFactory.class);
            listOperator = mock(ListOperator.class);

            checking(new Expectations() {{
                one(operatorFactory).createOperator(listIdentifier); will(returnValue(listOperator));
            }});

            return listKeywords;
        }

        public void clearsSelectionFromList() {
            checking(new Expectations() {{
                one(listOperator).clearSelection();
            }});

            context.clearSelectionFromList(listIdentifier);
        }

        public void clicksOnItem() {
            checking(new Expectations() {{
                one(listOperator).clickOnItem("someListItem", 1);
            }});

            context.clickOnListItem(listIdentifier, "someListItem");
        }
                
        public void clicksWithAlternativeClickCount() {
            checking(new Expectations() {{
                one(listOperator).clickOnItem("3", 2);
            }});
            
            context.clickOnListItem(listIdentifier, "3", 2);
        }
        
        public void selectsSingleItem() {
            checking(new Expectations() {{
                one(listOperator).selectItems(new ArrayList<String>() {{add("2");}});
            }});
            
            context.selectFromList(listIdentifier, "2", new String[0]);
        }
        
        public void selectsItems() {
            checking(new Expectations() {{
                one(listOperator).selectItems(new ArrayList<String>() {{add("one"); add("two"); add("three");}});
            }});
            
            context.selectFromList(listIdentifier, "one", new String[] {"two", "three"});
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
            checking(new Expectations() {{
                one(listOperator).getSize(); will(returnValue(4));
            }});

            specify(context.getListItemCount(listIdentifier), must.equal(4));
        }
        
        public void selectsAllItems() {
            checking(new Expectations() {{
                one(listOperator).selectAll();
            }});
            
            context.selectAllListItems(listIdentifier);
        }
    }
}
