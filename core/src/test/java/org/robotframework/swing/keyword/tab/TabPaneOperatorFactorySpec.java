package org.robotframework.swing.keyword.tab;

import java.awt.Component;

import javax.swing.JTabbedPane;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.laughingpanda.beaninject.Inject;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.netbeans.jemmy.operators.Operator;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;

@RunWith(JDaveRunner.class)
public class TabPaneOperatorFactorySpec extends OperatorFactorySpecification<TabPaneOperatorFactory> {
    private JTabbedPane tabbedPane = new JTabbedPane() {
        public boolean isShowing() {
            return true;
        }
    };

    public class Any {
        public TabPaneOperatorFactory create() {
            return new TabPaneOperatorFactory();
        }

        public void hasContextVerifier() {
            specify(context, satisfies(new FieldIsNotNullContract("contextVerifier")));
        }
    }

    public class ParsingIdentifier extends AnyIdentifierParsingOperatorFactory {
        @Override
        protected Component createComponent() {
            return tabbedPane;
        }

        @Override
        protected OperatorFactory<? extends Operator> createOperatorFactory() {
            TabPaneOperatorFactory tabPaneOperatorFactory = new TabPaneOperatorFactory();
            Inject.field("contextVerifier").of(tabPaneOperatorFactory).with(dummy(IContextVerifier.class));
            return tabPaneOperatorFactory;
        }
    }

    public class CreatingOperatorFromContext {
        public TabPaneOperatorFactory create() {
            return new TabPaneOperatorFactory();
        }

        public void destroy() {
            Context.setContext(null);
        }

        public void createsOperatorFromContextSourceIfSourceIsJTabbedPane() {
            final JTabbedPaneOperator operator = mock(JTabbedPaneOperator.class);
            final JTabbedPane source = dummy(JTabbedPane.class);
            checking(new Expectations() {{
                exactly(2).of(operator).getSource(); will(returnValue(source));
            }});

            Context.setContext(operator);
            specify(context.createOperatorFromContext().getSource(), must.equal(new JTabbedPaneOperator(source).getSource()));
        }

        public void createsOperatorFromContextIfSourceIsContainer() {
            mockFindsByIndex(tabbedPane);
            specifyChecksContext();
            context.createOperatorFromContext();
        }
    }

    public class CreatingOperatorWithIdentifier {
        public TabPaneOperatorFactory create() {
            return new TabPaneOperatorFactory();
        }
    }

    private void specifyChecksContext() {
        final IContextVerifier contextVerifier = injectMockToContext(IContextVerifier.class);
        checking(new Expectations() {{
            one(contextVerifier).verifyContext();
        }});
    }
}
