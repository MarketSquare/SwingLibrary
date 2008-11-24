package org.robotframework.swing.keyword.context;

import java.awt.Component;
import java.awt.Container;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.context.ContainerOperator;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;
import org.robotframework.swing.keyword.context.ContainerOperatorFactory;


@RunWith(JDaveRunner.class)
public class ContainerOperatorFactorySpec extends OperatorFactorySpecification<ContainerOperatorFactory> {
    public class Any extends AnyIdentifierParsingOperatorFactory {
        @Override
        protected OperatorFactory<ContainerOperator> createOperatorFactory() {
            return new ContainerOperatorFactory();
        }

        @Override
        protected Component createComponent() {
            return new Container() {
                @Override
                public boolean isShowing() {
                    return true;
                }
            };
        }
    }
}
