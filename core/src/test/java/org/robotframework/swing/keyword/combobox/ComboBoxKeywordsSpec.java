package org.robotframework.swing.keyword.combobox;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.combobox.ComboBoxOperator;
import org.robotframework.swing.factory.OperatorFactory;


@RunWith(JDaveRunner.class)
public class ComboBoxKeywordsSpec extends MockSupportSpecification<ComboBoxKeywords> {
    private String comboBoxIdentifier = "someComboBox";

    public class Any {
        public ComboBoxKeywords create() {
            return new ComboBoxKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasOperatorFactory() throws Throwable {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void hasSelectFromComboBoxKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromComboBox")));
        }

        public void hasSelectFromDropDownMenuKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromDropdownMenu")));
        }

        public void hasGetSelectedItemFromComboboxKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getSelectedItemFromComboBox")));
        }

        public void hasGetSelectedItemFromDropDownMenuKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getSelectedItemFromDropdownMenu")));
        }
        
        public void hasComboBoxShouldBeEnabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("comboBoxShouldBeEnabled")));
        }
        
        public void hasComboBoxShouldBeDisabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("comboBoxShouldBeDisabled")));
        }
        
        public void hasTypeIntoComboboxKeyword() {
            specify(context, satisfies(new RobotKeywordContract("typeIntoCombobox")));
        }
    }

    public class Operating {
        private OperatorFactory<?> operatorFactory;
        private ComboBoxOperator operator;

        public ComboBoxKeywords create() {
            operator = mock(ComboBoxOperator.class);
            ComboBoxKeywords comboBoxKeywords = new ComboBoxKeywords();
            operatorFactory = injectMockTo(comboBoxKeywords, OperatorFactory.class);

            checking(new Expectations() {{
                one(operatorFactory).createOperator(with(equal(comboBoxIdentifier)));
                will(returnValue(operator));
            }});

            return comboBoxKeywords;
        }

        public void selectsFromComboBox() {
            final String comboItemIdentifier = "someComboItem";

            checking(new Expectations() {{
                one(operator).selectItem(comboItemIdentifier);
            }});

            context.selectFromComboBox(comboBoxIdentifier, comboItemIdentifier);
        }

        public void getsSelectedItem() {
            final Object selectedItem = new Object();

            checking(new Expectations() {{
                one(operator).getSelectedItem();
                will(returnValue(selectedItem));
            }});

            specify(context.getSelectedItemFromComboBox(comboBoxIdentifier), must.equal(selectedItem));
        }
        
        public void comboboxShouldBeEnabledPassesWhenComboboxIsEnabled() throws Throwable {
            checking(new Expectations() {{
                one(operator).isEnabled(); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.comboBoxShouldBeEnabled(comboBoxIdentifier);
                }
            }, must.not().raiseAnyException());
        }
        
        public void comboboxShouldBeDisabledPassesWhenComboboxIsDisabled() throws Throwable {
            checking(new Expectations() {{
                one(operator).isEnabled(); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.comboBoxShouldBeDisabled(comboBoxIdentifier);
                }
            }, must.not().raiseAnyException());
        }
        
        public void comboboxShouldBeEnabledFailsWhenComboboxIsDisabled() throws Throwable {
            checking(new Expectations() {{
                one(operator).isEnabled(); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.comboBoxShouldBeEnabled(comboBoxIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Combobox '" + comboBoxIdentifier + "' was disabled."));
        }
        
        public void comboboxShouldBeDisabledFailsWhenComboboxIsEnabled() throws Throwable {
            checking(new Expectations() {{
                one(operator).isEnabled(); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.comboBoxShouldBeDisabled(comboBoxIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Combobox '" + comboBoxIdentifier + "' was enabled."));
        }
        
        public void typesIntoCombobox() {
            checking(new Expectations() {{
                one(operator).typeText("someText");
            }});
            
            context.typeIntoCombobox(comboBoxIdentifier, "someText");
        }
    }
    
    public class HandlingAliases {
        private boolean isAnAlias = false;
        public void checkBoxShouldNotBeCheckedIsAnAliasForCheckBoxShouldBeUnchecked() {
            final String comboItemIdentifier = "itemIdentifier";

            ComboBoxKeywords comboBoxKeywords = new ComboBoxKeywords() {
                @Override
                public void selectFromComboBox(String boxIdentifier, String itemIdentifier) {
                    if (boxIdentifier.equals(comboBoxIdentifier) && itemIdentifier.equals(comboItemIdentifier)) {
                        isAnAlias = true;
                    }
                }
            };

            comboBoxKeywords.selectFromDropdownMenu(comboBoxIdentifier, comboItemIdentifier);
            specify(isAnAlias);
        }
    }
}
