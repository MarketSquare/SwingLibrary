package org.robotframework.swing.context;

import java.awt.Component;
import java.awt.Container;

import jdave.Block;
import jdave.Specification;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.robotframework.swing.operator.IOperator;

public abstract class ContextVerifierSpecification<T extends IContextVerifier> extends Specification<T> {
    public abstract class EmptyContext {
        public IContextVerifier create() {
            Context.setContext(null);
            return createVerifier();
        }

        public void throwsErrorIfContextIsEmpty() {
            selectEmptyContext();
            specify(new Block() {
                public void run() throws Throwable {
                    context.verifyContext();
                }
            }, must.raiseExactly(AssertionFailedError.class, getExpectedErrorMessage() + " Current context is java.awt.Container"));
        }

        protected abstract IContextVerifier createVerifier();
    }

    protected void specifyContextClassMatches(final Class contextClass) throws Throwable {
        checking(new Expectations() {{
            atLeast(1).of(Context.getContext()).getSource(); will(returnValue(dummy(contextClass)));
        }});

        specify(new Block() {
            public void run() throws Throwable {
                context.verifyContext();
            }
        }, must.not().raise(AssertionFailedError.class));
    }

    protected abstract String getExpectedErrorMessage();

    private void selectEmptyContext() {
        final Container container = new Container();
        Context.setContext(new IOperator() {
            public Component getSource() {
                return container;
            }
        });
    }

}
