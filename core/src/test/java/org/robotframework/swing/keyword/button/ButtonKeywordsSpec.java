package org.robotframework.swing.keyword.button;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.operator.button.DefaultButtonOperator;
import org.robotframework.swing.util.IComponentConditionResolver;


@RunWith(JDaveRunner.class)
public class ButtonKeywordsSpec extends MockSupportSpecification<ButtonKeywords> {
    private ButtonKeywords buttonKeywords = new ButtonKeywords();
    private String buttonIdentifier = "someButton";

    public class Any {
        public ButtonKeywords create() {
            return new ButtonKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasOperatorFactory() throws Throwable {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void hasConditionResolver() throws Throwable {
            specify(context, satisfies(new FieldIsNotNullContract("buttonExistenceResolver")));
        }

        public void hasPushButtonKeyword() {
            specify(context, satisfies(new RobotKeywordContract("pushButton")));
        }

        public void hasGetButtonTextKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getButtonText")));
        }

        public void hasButtonShouldExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("buttonShouldExist")));
        }

        public void hasButtonShouldNotExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("buttonShouldNotExist")));
        }

        public void hasButtonShouldBeEnabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("buttonShouldBeEnabled")));
        }

        public void hasButtonShouldBeDisabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("buttonShouldBeDisabled")));
        }
    }

    public class Operating {
        private OperatorFactory<DefaultButtonOperator> operatorFactory;
        private DefaultButtonOperator operator;

        public ButtonKeywords create() {
            injectMockOperatorFactory();
            return buttonKeywords;
        }

        public void pushesButton() {
            checking(new Expectations() {{
                one(operator).push();
            }});

            context.pushButton(buttonIdentifier);
        }

        public void getsButtonText() {
            checking(new Expectations() {{
                one(operator).getText(); will(returnValue("buttonText"));
            }});

            specify(context.getButtonText(buttonIdentifier), must.equal("buttonText"));
        }

        public void buttonShouldBeEnabledPassesIfButtonIsEnabled() throws Throwable {
            setButtonIsEnabled(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.buttonShouldBeEnabled(buttonIdentifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void buttonShouldBeEnabledFailsIfButtonIsDisabled() throws Throwable {
            setButtonIsEnabled(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.buttonShouldBeEnabled(buttonIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Button was disabled."));
        }

        public void buttonShouldBeDisabledPassesIfButtonIsDisabled() throws Throwable {
            setButtonIsEnabled(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.buttonShouldBeDisabled(buttonIdentifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void buttonShouldBeDisabledFailsIfButtonIsEnabled() throws Throwable {
            setButtonIsEnabled(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.buttonShouldBeDisabled(buttonIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Button was enabled."));
        }

        private void setButtonIsEnabled(final boolean b) {
            checking(new Expectations() {{
                one(operator).isEnabled(); will(returnValue(b));
            }});
        }

        private void injectMockOperatorFactory() {
            operator = mock(DefaultButtonOperator.class);
            operatorFactory = injectMockTo(buttonKeywords, "operatorFactory", IdentifierParsingOperatorFactory.class);
            checking(new Expectations() {{
                one(operatorFactory).createOperator(buttonIdentifier);
                will(returnValue(operator));
            }});
        }
    }

    public class ResolvingButtonExistence {
        private IComponentConditionResolver conditionResolver;

        public ButtonKeywords create() {
            return buttonKeywords;
        }

        public void buttonShouldExistPassesIfButtonExists() throws Throwable {
            setButtonExists(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.buttonShouldExist(buttonIdentifier);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void buttonShouldExistFailsIfButtonDoesntExist() throws Throwable {
            setButtonExists(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.buttonShouldExist(buttonIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Button '" + buttonIdentifier + "' doesn't exist"));
        }

        public void buttonShouldNotExistPassesIfButtonDoesntExist() throws Throwable {
            setButtonExists(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.buttonShouldNotExist(buttonIdentifier);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void buttonShouldNotExistFailsIfButtonExists() {
            setButtonExists(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.buttonShouldNotExist(buttonIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Button '" + buttonIdentifier + "' exists"));
        }

        private void setButtonExists(final boolean exists) {
            conditionResolver = injectMockTo(buttonKeywords, "buttonExistenceResolver", IComponentConditionResolver.class);
            checking(new Expectations() {{
                one(conditionResolver).satisfiesCondition(buttonIdentifier); will(returnValue(exists));
            }});
        }
    }
}
