package org.robotframework.swing.togglebutton;

import java.awt.Component;

import javax.swing.JToggleButton;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;
import org.robotframework.swing.operator.ComponentWrapper;

@RunWith(JDaveRunner.class)
public class ToggleButtonOperatorFactorySpec extends OperatorFactorySpecification<ToggleButtonOperatorFactory> {
    public class Any extends AnyIdentifierParsingOperatorFactory {
        @Override
        protected Component createComponent() {
            return new JToggleButton() {
                public boolean isShowing() {
                    return true;
                }
            };
        }

        @Override
        protected OperatorFactory<? extends ComponentWrapper> createOperatorFactory() {
            return new ToggleButtonOperatorFactory();
        }
    }
}
