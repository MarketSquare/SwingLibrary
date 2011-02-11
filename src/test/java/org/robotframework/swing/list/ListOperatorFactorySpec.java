package org.robotframework.swing.list;

import java.awt.Component;

import javax.swing.JList;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;


@RunWith(JDaveRunner.class)
public class ListOperatorFactorySpec extends OperatorFactorySpecification<ListOperatorFactory> {
    public class Any extends AnyIdentifierParsingOperatorFactory {
        @Override
        protected OperatorFactory<ListOperator> createOperatorFactory() {
            return new ListOperatorFactory();
        }

        @Override
        protected Component createComponent() {
            return new JList() {
                public boolean isShowing() {
                    return true;
                }
            };
        }
    }
}
