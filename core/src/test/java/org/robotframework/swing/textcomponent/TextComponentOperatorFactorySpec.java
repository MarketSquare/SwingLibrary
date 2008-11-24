package org.robotframework.swing.textcomponent;

import java.awt.Component;

import javax.swing.text.JTextComponent;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;
import org.robotframework.swing.operator.ComponentWrapper;
import org.robotframework.swing.textcomponent.TextComponentOperatorFactory;

@RunWith(JDaveRunner.class)
public class TextComponentOperatorFactorySpec extends OperatorFactorySpecification<TextComponentOperatorFactory> {
    public class Any extends AnyIdentifierParsingOperatorFactory {
        @Override
        protected Component createComponent() {
            return new JTextComponent() {
                public boolean isShowing() {
                    return true;
                }  
            };
        }

        @Override
        protected OperatorFactory<? extends ComponentWrapper> createOperatorFactory() {
            return new TextComponentOperatorFactory();
        }
    }
}
