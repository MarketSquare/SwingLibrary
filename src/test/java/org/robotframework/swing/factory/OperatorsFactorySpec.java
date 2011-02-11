package org.robotframework.swing.factory;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collection;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.robotframework.swing.factory.OperatorsFactory.ComponentBasedOperatorFactory;

@RunWith(JDaveRunner.class)
public class OperatorsFactorySpec extends Specification<OperatorsFactory> {
    private Container containerContext = dummy(Container.class);

    public class NoMatchingComponent {
        public OperatorsFactory create() {
            return new OperatorsFactory(dummy(ComponentChooser.class), dummy(ComponentBasedOperatorFactory.class));
        }

        public void createsEmptyOperatorListIfNoneOfTheComponentsMatch() {
            specify(context.createOperators(containerContext).isEmpty());
        }
    }

    public class ComponentsFound {
        private Component component = dummy(Component.class);
        private ComponentBasedOperatorFactory operatorFactory;

        public OperatorsFactory create() {
            ComponentChooser chooser = mock(ComponentChooser.class);
            operatorFactory = mock(ComponentBasedOperatorFactory.class);

            return new OperatorsFactory(chooser, operatorFactory) {
                Component findComponent(Container context, int index) {
                    if (index < 2) {
                        return component;
                    }
                    return null;
                }
            };
        }

        public void createsOperatorListForMatchingComponents() {
            final ComponentOperator operator = dummy(ComponentOperator.class);
            checking(new Expectations() {{
                atLeast(2).of(operatorFactory).createOperator(component);
                will(returnValue(operator));
            }});

            Collection expectedElements = new ArrayList() {{ add(operator); add(operator); }};
            specify(context.createOperators(containerContext), containsExactly(expectedElements));
        }
    }
}
