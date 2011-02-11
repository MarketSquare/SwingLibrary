package org.robotframework.swing.radiobutton;

import java.awt.Component;

import javax.swing.JRadioButton;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;
import org.robotframework.swing.operator.ComponentWrapper;

@RunWith(JDaveRunner.class)
public class RadioButtonOperatorFactorySpec extends OperatorFactorySpecification<RadioButtonOperatorFactory> {
    public class Any extends AnyIdentifierParsingOperatorFactory {
        @Override
        protected Component createComponent() {
            return new JRadioButton() {
                public boolean isShowing() {
                    return true;
                }
            };
        }

        @Override
        protected OperatorFactory<? extends ComponentWrapper> createOperatorFactory() {
            return new RadioButtonOperatorFactory();
        }
    }
}
