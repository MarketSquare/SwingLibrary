package org.robotframework.swing.context;

import java.awt.Component;
import java.awt.Container;

import jdave.Block;
import jdave.Specification;

import org.jmock.Expectations;
import org.robotframework.swing.operator.ComponentWrapper;

public abstract class ContextVerifierSpecification<T extends ContextVerifier> extends Specification<T> {
    public abstract class EmptyContext {
        public ContextVerifier create() {
            Context.setContext(null);
            return createVerifier();
        }

        public void throwsErrorIfContextIsEmpty() {
            selectEmptyContext();
            specify(new Block() {
                public void run() throws Throwable {
                    context.verifyContext();
                }
            }, must.raiseExactly(AssertionError.class, getExpectedErrorMessage() + " Current context is java.awt.Container"));
        }

        protected abstract ContextVerifier createVerifier();
    }

    protected void specifyContextClassMatches(final Class contextClass) throws Throwable {
        checking(new Expectations() {{
            atLeast(1).of(Context.getContext()).getSource(); will(returnValue(dummy(contextClass)));
        }});

        specify(new Block() {
            public void run() throws Throwable {
                context.verifyContext();
            }
        }, must.not().raise(AssertionError.class));
    }

    protected abstract String getExpectedErrorMessage();

    private void selectEmptyContext() {
        final Container container = new Container();
        Context.setContext(new ComponentWrapper() {
            public Component getSource() {
                return container;
            }
        });
    }

}
