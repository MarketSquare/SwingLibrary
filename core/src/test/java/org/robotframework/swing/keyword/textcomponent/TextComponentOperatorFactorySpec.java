package org.robotframework.swing.keyword.textcomponent;

import java.awt.Component;

import javax.swing.text.JTextComponent;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;
import org.robotframework.swing.operator.IOperator;

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
        protected OperatorFactory<? extends IOperator> createOperatorFactory() {
            return new TextComponentOperatorFactory();
        }
    }
}
