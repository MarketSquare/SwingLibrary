package org.robotframework.swing.keyword.checkbox;

import java.awt.Container;
import java.util.ArrayList;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.OperatorListFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.operator.IOperator;
import org.robotframework.swing.operator.checkbox.DefaultCheckBoxOperator;


@RunWith(JDaveRunner.class)
public class CheckBoxListKeywordsSpec extends MockSupportSpecification<CheckBoxListKeywords> {
    private CheckBoxListKeywords checkboxListKeywords = new CheckBoxListKeywords();

    public class Any {
        public void isRobotKeywordsAnnotated() {
            specify(checkboxListKeywords, satisfies(new RobotKeywordsContract()));
        }

        public void hasCheckAllCheckboxesKeyword() {
            specify(checkboxListKeywords, satisfies(new RobotKeywordContract("checkAllCheckboxes")));
        }

        public void hasUncheckAllCheckboxesKeyword() {
            specify(checkboxListKeywords, satisfies(new RobotKeywordContract("uncheckAllCheckboxes")));
        }

        public void hasAllCheckboxesShouldBeCheckedKeyword() {
            specify(checkboxListKeywords, satisfies(new RobotKeywordContract("allCheckboxesShouldBeChecked")));
        }

        public void hasAllCheckboxesShouldBeUncheckedKeyword() {
            specify(checkboxListKeywords, satisfies(new RobotKeywordContract("allCheckboxesShouldBeUnchecked")));
        }

        public void hasOperatorListFactory() {
            specify(checkboxListKeywords, satisfies(new FieldIsNotNullContract("operatorListFactory")));
        }

        public void hasContextVerifier() {
            specify(checkboxListKeywords, satisfies(new FieldIsNotNullContract("contextVerifier")));
        }
    }

    public class Operating {
        private DefaultCheckBoxOperator operator;
        private Container containerContext;
        private String checkboxText = "Some checkbox";

        public CheckBoxListKeywords create() {
            injectMockInternals();
            return checkboxListKeywords;
        }

        public void checksAllCheckBoxes() {
            checking(new Expectations() {{
                exactly(2).of(operator).changeSelection(true);
            }});

            context.checkAllCheckboxes();
        }

        public void unchecksAllCheckBoxes() {
            checking(new Expectations() {{
                exactly(2).of(operator).changeSelection(false);
            }});

            context.uncheckAllCheckboxes();
        }

        public void allCheckboxesShouldBeCheckedPassesIfAllCheckboxesAreChecked() throws Throwable {
            setCheckboxesAreSelected(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.allCheckboxesShouldBeChecked();
                }
            }, must.not().raise(Exception.class));
        }

        public void allCheckboxesShouldBeCheckedFailsIfAnyOfCheckboxesIsUnchecked() throws Throwable {
            checking(new Expectations() {{
                one(operator).isSelected(); will(returnValue(true));
                one(operator).isSelected(); will(returnValue(false));
                allowing(operator).getText(); will(returnValue(checkboxText));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.allCheckboxesShouldBeChecked();
                }
            }, must.raiseExactly(AssertionFailedError.class, "Checkbox '" + checkboxText + "' is not checked."));
        }

        public void allCheckboxesShouldBeUncheckedPassesIfAllCheckboxesAreUnchecked() throws Throwable {
            setCheckboxesAreSelected(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.allCheckboxesShouldBeUnchecked();
                }
            }, must.not().raise(Exception.class));
        }

        public void allCheckboxesShouldBeUncheckedFailsIfAnyOfCheckboxesIsChecked() throws Throwable {
            checking(new Expectations() {{
                one(operator).isSelected(); will(returnValue(false));
                one(operator).isSelected(); will(returnValue(true));
                allowing(operator).getText(); will(returnValue(checkboxText));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.allCheckboxesShouldBeUnchecked();
                }
            }, must.raiseExactly(AssertionFailedError.class, "Checkbox '" + checkboxText + "' is checked."));
        }

        private void setCheckboxesAreSelected(final boolean b) {
            checking(new Expectations() {{
                exactly(2).of(operator).isSelected(); will(returnValue(b));
                allowing(operator).getText(); will(returnValue(checkboxText));
            }});
        }

        private void injectMockInternals() {
            operator = mock(DefaultCheckBoxOperator.class);
            createMockContext();
            injectMockContextVerifier();
            injectMockOperatorFactory();
        }

        private void injectMockOperatorFactory() {
            final OperatorListFactory<?> operatorListFactory = injectMockTo(checkboxListKeywords, OperatorListFactory.class);
            checking(new Expectations() {{
                one(operatorListFactory).createOperators(containerContext);
                will(returnValue(new ArrayList<JCheckBoxOperator>() {{ add(operator); add(operator); }}));
            }});
        }

        private void createMockContext() {
            final IOperator contextOperator = mock(IOperator.class);
            containerContext = mock(Container.class);
            checking(new Expectations() {{
                one(contextOperator).getSource(); will(returnValue(containerContext));
            }});

            Context.setContext(contextOperator);
        }

        private void injectMockContextVerifier() {
            final IContextVerifier contextVerifier = injectMockTo(checkboxListKeywords, IContextVerifier.class);
            checking(new Expectations() {{
                one(contextVerifier).verifyContext();
            }});
        }
    }
}

