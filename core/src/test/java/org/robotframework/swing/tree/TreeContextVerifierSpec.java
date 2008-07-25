package org.robotframework.swing.tree;

import javax.swing.JTree;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.ContextVerifierSpecification;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.tree.TreeContextVerifier;


@RunWith(JDaveRunner.class)
public class TreeContextVerifierSpec extends ContextVerifierSpecification<TreeContextVerifier> {
    public class WithEmptyContext extends EmptyContext {
        @Override
        protected IContextVerifier createVerifier() {
            return new TreeContextVerifier();
        }
    }

    public class WithContext {
        public TreeContextVerifier create() {
            Context.setContext(mock(ContainerOperator.class));
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
