package org.robotframework.swing.tree;

import java.awt.Component;

import javax.swing.JTree;

import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;


@RunWith(JDaveRunner.class)
public class EnhancedTreeOperatorFactorySpec extends OperatorFactorySpecification<EnhancedTreeOperatorFactory> {
    public class Any extends AnyIdentifierParsingOperatorFactory {
        @Override
        protected Component createComponent() {
            return new JTree() {
                public boolean isShowing() {
                    return true;
                }
            };
        }

        @Override
        protected OperatorFactory<EnhancedTreeOperator> createOperatorFactory() {
            return new EnhancedTreeOperatorFactory();
        }
    }
}
