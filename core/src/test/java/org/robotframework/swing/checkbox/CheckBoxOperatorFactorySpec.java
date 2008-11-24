package org.robotframework.swing.checkbox;

import java.awt.Component;

import javax.swing.JCheckBox;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.checkbox.CheckBoxOperator;
import org.robotframework.swing.checkbox.CheckBoxOperatorFactory;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;


@RunWith(JDaveRunner.class)
public class CheckBoxOperatorFactorySpec extends OperatorFactorySpecification<CheckBoxOperatorFactory> {
    public class Any extends AnyIdentifierParsingOperatorFactory {
        @Override
        protected OperatorFactory<CheckBoxOperator> createOperatorFactory() {
            return new CheckBoxOperatorFactory();
        }

        @Override
        protected Component createComponent() {
            return new JCheckBox() {
                public boolean isShowing() {
                    return true;
                }
            };
        }
    }
}
