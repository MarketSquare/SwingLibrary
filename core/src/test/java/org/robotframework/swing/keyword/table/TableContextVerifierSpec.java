package org.robotframework.swing.keyword.table;

import javax.swing.JTable;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.ContextVerifierSpecification;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.keyword.table.TableContextVerifier;


@RunWith(JDaveRunner.class)
public class TableContextVerifierSpec extends ContextVerifierSpecification<TableContextVerifier> {
    public class WithEmptyContext extends EmptyContext {
        @Override
        protected IContextVerifier createVerifier() {
            return new TableContextVerifier();
        }
    }

    public class WithContext {
        public TableContextVerifier create() {
            Context.setContext(mock(ContainerOperator.class));
            return new TableContextVerifier();
        }

        public void passesIfContextClassIsJTable() throws Throwable {
            specifyContextClassMatches(JTable.class);
        }
    }

    @Override
    protected String getExpectedErrorMessage() {
        return TableContextVerifier.ERROR_MESSAGE;
    }
}
