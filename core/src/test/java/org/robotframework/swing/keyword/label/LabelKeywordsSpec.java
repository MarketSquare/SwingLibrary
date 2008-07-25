package org.robotframework.swing.keyword.label;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.keyword.label.LabelKeywords;
import org.robotframework.swing.util.IComponentConditionResolver;


@RunWith(JDaveRunner.class)
public class LabelKeywordsSpec extends MockSupportSpecification<LabelKeywords> {
    private String labelIdentifier = "someLabel";

    public class Any {
        public LabelKeywords create() {
            return new LabelKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasLabelExistenceResolver() {
            specify(context, satisfies(new FieldIsNotNullContract("labelExistenceResolver")));
        }

        public void hasOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void hasContextVerifier() {
            specify(context, satisfies(new FieldIsNotNullContract("contextVerifier")));
        }

        public void hasGetLabelContentKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getLabelContent")));
        }

        public void hasLabelShouldExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("labelShouldExist")));
        }

        public void hasLabelShouldNotExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("labelShouldNotExist")));
        }
    }

    public class Operating {
        private JLabelOperator labelOperator = mock(JLabelOperator.class);

        public LabelKeywords create() {
            LabelKeywords labelKeywords = new LabelKeywords();
            final OperatorFactory operatorFactory = injectMockTo(labelKeywords, "operatorFactory", IdentifierParsingOperatorFactory.class);
            final IContextVerifier contextVerifier = injectMockTo(labelKeywords, "contextVerifier", IContextVerifier.class);

            checking(new Expectations() {{
                one(operatorFactory).createOperator(with(equal(labelIdentifier)));
                will(returnValue(labelOperator));

                one(contextVerifier).verifyContext();
            }});

            return labelKeywords;
        }

        public void getsLabelContent() {
            final String labelText = "textFromLabel";
            checking(new Expectations() {{
                one(labelOperator).getText(); will(returnValue(labelText));
            }});

            specify(context.getLabelContent(labelIdentifier), must.equal(labelText));
        }
    }

    public class CheckingConditions {
        private IComponentConditionResolver existenceResolver;

        public LabelKeywords create() {
            LabelKeywords labelKeywords = new LabelKeywords();
            existenceResolver = injectMockTo(labelKeywords, "labelExistenceResolver", IComponentConditionResolver.class);
            final IContextVerifier contextVerifier = injectMockTo(labelKeywords, "contextVerifier", IContextVerifier.class);
            checking(new Expectations() {{
                one(contextVerifier).verifyContext();
            }});

            return labelKeywords;
        }

        public void labelShouldExistPassesIfLabelExists() throws Throwable {
            setLabelExists(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.labelShouldExist(labelIdentifier);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void labelShouldExistFailsIfLabelDoesntExist() throws Throwable {
            setLabelExists(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.labelShouldExist(labelIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Label '" + labelIdentifier + "' doesn't exist"));
        }

        public void labelShouldNotExistPassesIfLabelDoesntExist() throws Throwable {
            setLabelExists(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.labelShouldNotExist(labelIdentifier);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void labelShouldNotExistFailsIfLabelExists() throws Throwable {
            setLabelExists(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.labelShouldNotExist(labelIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Label '" + labelIdentifier + "' exists"));
        }

        private void setLabelExists(final boolean labelExists) {
            checking(new Expectations() {{
                one(existenceResolver).satisfiesCondition(with(equal(labelIdentifier)));
                will(returnValue(labelExists));
            }});
        }
    }
}
