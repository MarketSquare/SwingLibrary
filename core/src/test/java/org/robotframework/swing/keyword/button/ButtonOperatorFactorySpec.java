package org.robotframework.swing.keyword.button;

import java.awt.Component;

import javax.swing.JButton;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.button.ButtonOperator;
import org.robotframework.swing.button.ButtonOperatorFactory;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;


@RunWith(JDaveRunner.class)
public class ButtonOperatorFactorySpec extends OperatorFactorySpecification<ButtonOperatorFactory> {
    public class Any extends AnyIdentifierParsingOperatorFactory {
        @Override
        protected OperatorFactory<ButtonOperator> createOperatorFactory() {
            return new ButtonOperatorFactory();
        }

        @Override
        protected Component createComponent() {
            return new JButton() {
                public boolean isShowing() {
                    return true;
                }
            };
        }
    }
}
