package org.robotframework.swing.tab;

import java.awt.Component;

import javax.swing.JTabbedPane;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.laughingpanda.beaninject.Inject;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.ContextVerifier;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;
import org.robotframework.swing.operator.ComponentWrapper;

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
        protected OperatorFactory<TabbedPaneOperator> createOperatorFactory() {
            TabPaneOperatorFactory tabPaneOperatorFactory = new TabPaneOperatorFactory();
            Inject.field("contextVerifier").of(tabPaneOperatorFactory).with(dummy(ContextVerifier.class));
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
            final ComponentWrapper operator = mock(ComponentWrapper.class);
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
        final ContextVerifier contextVerifier = injectMockToContext(ContextVerifier.class);
        checking(new Expectations() {{
            one(contextVerifier).verifyContext();
        }});
    }
}
