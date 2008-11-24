package org.robotframework.swing.tree;

import javax.swing.JTree;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.ContextVerifierSpecification;
import org.robotframework.swing.context.ContextVerifier;
import org.robotframework.swing.operator.ComponentWrapper;


@RunWith(JDaveRunner.class)
public class TreeContextVerifierSpec extends ContextVerifierSpecification<TreeContextVerifier> {
    public class WithEmptyContext extends EmptyContext {
        @Override
        protected ContextVerifier createVerifier() {
            return new TreeContextVerifier();
        }
    }

    public class WithContext {
        public TreeContextVerifier create() {
            Context.setContext(mock(ComponentWrapper.class));
            return new TreeContextVerifier();
        }

        public void passesIfContextClassIsJTree() throws Throwable {
            specifyContextClassMatches(JTree.class);
        }
    }

    @Override
    protected String getExpectedErrorMessage() {
        return TreeContextVerifier.ERROR_MESSAGE;
    }
}
