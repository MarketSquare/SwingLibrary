package org.robotframework.swing.keyword.dialog;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.laughingpanda.beaninject.Inject;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.dialog.DialogOperator;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.util.IComponentConditionResolver;


@RunWith(JDaveRunner.class)
public class DialogKeywordsSpec extends MockSupportSpecification<DialogKeywords> {
    private String dialogIdentifier = "someDialog";

    public class Any {
        public DialogKeywords create() {
            return new DialogKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasOperatorFactory() throws Throwable {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void hasDialogExistenceResolver() throws Throwable {
            specify(context, satisfies(new FieldIsNotNullContract("dialogExistenceResolver")));
        }

        public void hasSelectDialogKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectDialog")));
        }

        public void hasCloseDialogKeyword() {
            specify(context, satisfies(new RobotKeywordContract("closeDialog")));
        }

        public void hasDialogShouldBeOpenKeyword() {
            specify(context, satisfies(new RobotKeywordContract("dialogShouldBeOpen")));
        }

        public void hasDialogShouldNotBeOpenKeyword() {
            specify(context, satisfies(new RobotKeywordContract("dialogShouldNotBeOpen")));
        }
    }

    public class Operating {
        private OperatorFactory operatorFactory;
        private DialogOperator containerOperator;

        public DialogKeywords create() {
            DialogKeywords dialogKeywords = new DialogKeywords();
            operatorFactory = injectMockTo(dialogKeywords, "operatorFactory", IdentifierParsingOperatorFactory.class);
            containerOperator = mock(DialogOperator.class);

            checking(new Expectations() {{
                one(operatorFactory).createOperator(with(equal(dialogIdentifier)));
                will(returnValue(containerOperator));
            }});

            return dialogKeywords;
        }

        public void selectsDialog() {
            context.selectDialog(dialogIdentifier);
            specify(Context.getContext(), must.equal(containerOperator));
        }

        public void closesDialog() {
            checking(new Expectations() {{
                one(containerOperator).close();
            }});

            context.closeDialog(dialogIdentifier);
        }
    }

    public class CheckingConditions {
        private IComponentConditionResolver conditionResolver;

        public DialogKeywords create() {
            DialogKeywords dialogKeywords = new DialogKeywords();

            conditionResolver = mock(IComponentConditionResolver.class);
            Inject.field("dialogExistenceResolver").of(dialogKeywords).with(conditionResolver);

            return dialogKeywords;
        }

        public void dialogShouldBeOpenPassesWhenDialogIsOpen() throws Throwable {
            setDialogIsOpen(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.dialogShouldBeOpen(dialogIdentifier);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void dialogShouldBeOpenFailsWhenDialogIsNotOpen() throws Throwable {
            setDialogIsOpen(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.dialogShouldBeOpen(dialogIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Dialog '" + dialogIdentifier + "' is not open"));
        }

        public void dialogShouldNotBeOpenPassesWhenDialogIsNotOpen() throws Throwable {
            setDialogIsOpen(false);

            specify(new Block() {
                public void run() throws Throwable {
                    context.dialogShouldNotBeOpen(dialogIdentifier);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void dialogShouldNotBeOpenFailsWhenDialogIsOpen() throws Throwable {
            setDialogIsOpen(true);

            specify(new Block() {
                public void run() throws Throwable {
                    context.dialogShouldNotBeOpen(dialogIdentifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Dialog '" + dialogIdentifier + "' is open"));
        }

        private void setDialogIsOpen(final boolean dialogExists) {
            checking(new Expectations() {{
                one(conditionResolver).satisfiesCondition(with(equal(dialogIdentifier)));
                will(returnValue(dialogExists));
            }});
        }
    }
}
