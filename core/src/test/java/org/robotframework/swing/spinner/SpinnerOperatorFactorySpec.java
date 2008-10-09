package org.robotframework.swing.spinner;

import java.awt.Component;

import javax.swing.JSpinner;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;

@RunWith(JDaveRunner.class)
public class SpinnerOperatorFactorySpec extends OperatorFactorySpecification<SpinnerOperatorFactory> {
    public class Any extends AnyIdentifierParsingOperatorFactory {
        @Override
        protected OperatorFactory<SpinnerOperator> createOperatorFactory() {
            return new SpinnerOperatorFactory();
        }

        @Override
        protected Component createComponent() {
            return new JSpinner() {
                public boolean isShowing() {
                    return true;
                }
            };
        }
    }
}
