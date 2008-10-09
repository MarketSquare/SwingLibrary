package org.robotframework.swing.keyword.spinner;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.spinner.SpinnerOperator;
import org.robotframework.swing.util.IComponentConditionResolver;

@RunWith(JDaveRunner.class)
public class SpinnerKeywordsSpec extends MockSupportSpecification<SpinnerKeywords> {
    private String spinnerIdentifier = "spinnerButtonIdentifier";
    private SpinnerKeywords spinnerKeywords;

    public class Any {
        public SpinnerKeywords create() {
            return new SpinnerKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasSpinnerButtonShouldExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("spinnerShouldExist")));
        }

        public void hasSpinnerButtonShouldNotExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("spinnerShouldNotExist")));
        }

        public void hasSetSpinnerButtonValueKeyword() {
            specify(context, satisfies(new RobotKeywordContract("setSpinnerValue")));
        }

        public void hasGetSpinnerButtonValueKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getSpinnerValue")));
        }

        public void createsOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void createsContextVerifier() {
            specify(context, satisfies(new FieldIsNotNullContract("contextVerifier")));
        }

        public void createsExistenceResolver() {
            specify(context, satisfies(new FieldIsNotNullContract("existenceResolver")));
        }
    }

    public class OperatingOnSpinnerButtons {
        private SpinnerOperator spinnerButtonOperator;
        private String someValue = "someValue";

        public SpinnerKeywords create() {
            spinnerKeywords = new SpinnerKeywords();
            injectMockContextVerifier();
            injectMockOperatorFactory();
            return spinnerKeywords;
        }

        public void setsSpinnerButtonValue() {
            checking(new Expectations() {{
                one(spinnerButtonOperator).setValue(someValue);
            }});

            context.setSpinnerValue(spinnerIdentifier, someValue);
        }

        public void getsSpinnerButtonValue() {
            checking(new Expectations() {{
                one(spinnerButtonOperator).getValue(); will(returnValue(someValue));
            }});

            specify(context.getSpinnerValue(spinnerIdentifier), must.equal(someValue));
        }

        private void injectMockOperatorFactory() {
            final IdentifierParsingOperatorFactory operatorFactory = injectMockTo(spinnerKeywords, "operatorFactory", IdentifierParsingOperatorFactory.class);
            spinnerButtonOperator = mock(SpinnerOperator.class);

            checking(new Expectations() {{
                one(operatorFactory).createOperator(spinnerIdentifier);
                will(returnValue(spinnerButtonOperator));
            }});
        }
    }

    public class ResolvingSpinnerExistence {
        private IComponentConditionResolver existenceResolver;

        public SpinnerKeywords create() {
            spinnerKeywords = new SpinnerKeywords();
            injectMockContextVerifier();
            injectMockExistenceResolver();
            return spinnerKeywords;
        }

        public void spinnerShouldExistPassesIfSpinnerButtonExists() throws Throwable {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(spinnerIdentifier); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.spinnerShouldExist(spinnerIdentifier);
                }
            }, should.not().raise(AssertionFailedError.class));
        }

        public void spinnerShouldExistFailsIfSpinnerButtonDoesntExists() throws Throwable {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(spinnerIdentifier); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.spinnerShouldExist(spinnerIdentifier);
                }
            }, should.raiseExactly(AssertionFailedError.class, "Spinner '" + spinnerIdentifier + "' doesn't exist."));
        }

        public void spinnerShouldNotExistPassesIfSpinnerButtonDoesntExist() throws Throwable {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(spinnerIdentifier); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.spinnerShouldNotExist(spinnerIdentifier);
                }
            }, should.not().raise(AssertionFailedError.class));
        }

        public void spinnerShouldNotExistFailsIfSpinnerButtonExists() throws Throwable {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(spinnerIdentifier); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.spinnerShouldNotExist(spinnerIdentifier);
                }
            }, should.raiseExactly(AssertionFailedError.class, "Spinner '" + spinnerIdentifier + "' exists."));
        }

        private void injectMockExistenceResolver() {
            existenceResolver = injectMockTo(spinnerKeywords, "existenceResolver", IComponentConditionResolver.class);
        }
    }

    private void injectMockContextVerifier() {
        final IContextVerifier contextVerifier = injectMockTo(spinnerKeywords, IContextVerifier.class);
        checking(new Expectations() {{
            one(contextVerifier).verifyContext();
        }});
    }
}
