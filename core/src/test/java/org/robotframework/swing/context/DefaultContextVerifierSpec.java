package org.robotframework.swing.context;

import java.awt.Panel;
import java.awt.Window;

import javax.swing.JPanel;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.DefaultContextVerifier;
import org.robotframework.swing.context.IContextVerifier;

@RunWith(JDaveRunner.class)
public class DefaultContextVerifierSpec extends ContextVerifierSpecification<DefaultContextVerifier> {
    public class WithEmptyContext extends EmptyContext {
        @Override
        protected IContextVerifier createVerifier() {
            return new DefaultContextVerifier();
        }
    }

    public class WithContext {
        public DefaultContextVerifier create() {
            Context.setContext(mock(ContainerOperator.class));
            return new DefaultContextVerifier();
        }

        public void passesIfContextClassIsPanel() throws Throwable {
            specifyContextClassMatches(Panel.class);
        }

        public void passesIfContextClassIsJPanel() throws Throwable {
            specifyContextClassMatches(JPanel.class);
        }

        public void passesIfContextClassIsWindow() throws Throwable {
            specifyContextClassMatches(Window.class);
        }
    }

    @Override
    protected String getExpectedErrorMessage() {
        return DefaultContextVerifier.ERROR_MESSAGE;
    }
}
