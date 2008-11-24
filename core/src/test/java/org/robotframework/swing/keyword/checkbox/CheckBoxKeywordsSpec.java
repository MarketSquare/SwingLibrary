package org.robotframework.swing.keyword.checkbox;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.checkbox.CheckBoxOperator;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.keyword.checkbox.CheckBoxKeywords;


@RunWith(JDaveRunner.class)
public class CheckBoxKeywordsSpec extends MockSupportSpecification<CheckBoxKeywords> {
    private CheckBoxKeywords checkboxKeywords = new CheckBoxKeywords();
    private String checkboxIdentifier = "someCheckbox";

    public class Any {
        public void isRobotKeywordsAnnotated() {
            specify(checkboxKeywords, satisfies(new RobotKeywordsContract()));
        }

        public void hasOperatorFactory() throws Throwable {
            specify(checkboxKeywords, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void hasCheckBoxShouldBeCheckedKeyword() {
            specify(checkboxKeywords, satisfies(new RobotKeywordContract("checkBoxShouldBeChecked")));
        }

        public void hasCheckBoxShouldBeUnCheckedKeyword() {
            specify(checkboxKeywords, satisfies(new RobotKeywordContract("checkBoxShouldBeUnchecked")));
        }

        public void hasCheckBoxShouldNotBeCheckedKeyword() {
            specify(checkboxKeywords, satisfies(new RobotKeywordContract("checkBoxShouldNotBeChecked")));
        }

        public void hasCheckCheckBoxKeyword() {
            specify(checkboxKeywords, satisfies(new RobotKeywordContract("checkCheckBox")));
        }

        public void hasUncheckCheckBoxKeyword() {
            specify(checkboxKeywords, satisfies(new RobotKeywordContract("uncheckCheckBox")));
        }
    }

    public class Operating {
        private OperatorFactory<CheckBoxOperator> operatorFactory;
        private CheckBoxOperator operator;

        public CheckBoxKeywords create() {
            injectMockOperatorFactory();
            return checkboxKeywords;
        }

        public void checksCheckBox() {
            checking(new Expectations() {{
                one(operator).changeSelection(true);
            }});

            context.checkCheckBox(checkboxIdentifier);
        }

        public void unchecksCheckBox() {
            checking(new Expectations() {{
                one(operator).changeSelection(false);
            }});

            context.uncheckCheckBox(checkboxIdentifier);
        }

        public void checkBoxShouldBeCheckedPassesIfCheckBoxIsChecked() throws Throwable {
            setIsSelected(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.checkBoxShouldBeChecked(checkboxIdentifier);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void checkBoxShouldBeCheckedFailsIfCheckBoxIsNotChecked() throws Throwable {
            setIsSelected(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.checkBoxShouldBeChecked(checkboxIdentifier);
                }
            }, must.raise(AssertionFailedError.class));
        }

        public void checkBoxShouldBeUnCheckedPassesIfCheckBoxIsNotChecked() throws Throwable {
            setIsSelected(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.checkBoxShouldBeUnchecked(checkboxIdentifier);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void checkBoxShouldBeUnCheckedFailsIfCheckBoxIsChecked() throws Throwable {
            setIsSelected(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.checkBoxShouldBeUnchecked(checkboxIdentifier);
                }
            }, must.raise(AssertionFailedError.class));
        }

        private void setIsSelected(final boolean b) {
            checking(new Expectations() {{
                one(operator).isSelected(); will(returnValue(b));
            }});
        }

        private void injectMockOperatorFactory() {
            operator = mock(CheckBoxOperator.class);
            operatorFactory = injectMockTo(checkboxKeywords, OperatorFactory.class);

            checking(new Expectations() {{
                one(operatorFactory).createOperator(with(equal(checkboxIdentifier)));
                will(returnValue(operator));
            }});
        }
    }

    public class HandlingAliases {
        private boolean isAnAlias = false;
        public void checkBoxShouldNotBeCheckedIsAnAliasForCheckBoxShouldBeUnchecked() {
            checkboxKeywords = new CheckBoxKeywords() {
                @Override
                public void checkBoxShouldBeUnchecked(String identifier) {
                    if (identifier.equals(checkboxIdentifier)) {
                        isAnAlias = true;
                    }
                }
            };

            checkboxKeywords.checkBoxShouldNotBeChecked(checkboxIdentifier);
            specify(isAnAlias);
        }
    }
}
