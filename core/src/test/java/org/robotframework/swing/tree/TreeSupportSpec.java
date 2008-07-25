package org.robotframework.swing.tree;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.tree.EnhancedTreeOperator;
import org.robotframework.swing.tree.TreeSupport;


@RunWith(JDaveRunner.class)
public class TreeSupportSpec extends MockSupportSpecification<TreeSupport> {
    public class GettingContext {
        private EnhancedTreeOperator dummyContext;
        private IContextVerifier contextVerifier;

        public TreeSupport create() {
            dummyContext = dummy(EnhancedTreeOperator.class);
            Context.setContext(dummyContext);

            TreeSupport treeSupport = new TreeSupport();
            contextVerifier = injectMockTo(treeSupport, IContextVerifier.class);
            return treeSupport;
        }

        public void hasContextVerifier() {
            specify(context, satisfies(new FieldIsNotNullContract("contextVerifier")));
        }

        public void treeOperatorReturnsCurrentContextAndVerifiesIt() {
            checking(new Expectations() {{
                one(contextVerifier).verifyContext();
            }});

            specify(context.treeOperator(), must.equal(dummyContext));
        }

        public void verifiesContext() {
            checking(new Expectations() {{
                one(contextVerifier).verifyContext();
            }});

            context.verifyContext();
        }
    }
}
