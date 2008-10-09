package org.robotframework.swing.keyword.checkbox;

import java.awt.Component;

import javax.swing.JCheckBox;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;
import org.robotframework.swing.operator.checkbox.MyCheckBoxOperator;


@RunWith(JDaveRunner.class)
public class CheckBoxOperatorFactorySpec extends OperatorFactorySpecification<CheckBoxOperatorFactory> {
    public class Any extends AnyIdentifierParsingOperatorFactory {
        @Override
        protected OperatorFactory<MyCheckBoxOperator> createOperatorFactory() {
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
