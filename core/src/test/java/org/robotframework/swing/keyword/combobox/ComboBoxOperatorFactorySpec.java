package org.robotframework.swing.keyword.combobox;

import java.awt.Component;

import javax.swing.JComboBox;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.combobox.ComboBoxOperator;
import org.robotframework.swing.combobox.keyword.ComboBoxOperatorFactory;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;

@RunWith(JDaveRunner.class)
public class ComboBoxOperatorFactorySpec extends OperatorFactorySpecification<ComboBoxOperatorFactory> {
    public class Any extends AnyIdentifierParsingOperatorFactory {
        protected OperatorFactory<ComboBoxOperator> createOperatorFactory() {
            return new ComboBoxOperatorFactory();
        }

        @Override
        protected Component createComponent() {
            return new JComboBox() {
                public boolean isShowing() {
                    return true;
                }
            };
        }
    }
}
