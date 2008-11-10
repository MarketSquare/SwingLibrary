package org.robotframework.swing.togglebutton;

import java.awt.Component;

import javax.swing.JToggleButton;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.button.AbstractButtonOperator;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.factory.OperatorFactorySpecification;
import org.robotframework.swing.operator.IOperator;

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
        protected OperatorFactory<? extends IOperator> createOperatorFactory() {
            ToggleButtonOperatorFactory toggleButtonOperatorFactory = new ToggleButtonOperatorFactory();
            final IContextVerifier contextVerifier = injectMockTo(toggleButtonOperatorFactory, IContextVerifier.class);
            checking(new Expectations() {{
                ignoring(contextVerifier);
            }});
            
            return toggleButtonOperatorFactory;
        }
    }
    
    public class VerifyingContext {
        private AbstractButtonOperator operator = dummy(AbstractButtonOperator.class);
        
        public ToggleButtonOperatorFactory create() {
            return new ToggleButtonOperatorFactory() {
                public AbstractButtonOperator createOperatorByIndex(int index) {
                    return operator;
                }
                
                public AbstractButtonOperator createOperatorByName(String name) {
                    return operator;
                }
            };
        }

        public void hasContextVerifier() {
            specify(context, satisfies(new FieldIsNotNullContract("contextVerifier")));
        }
        
        public void verifiesContext() {
            final IContextVerifier contextVerifier = injectMockToContext(IContextVerifier.class);
            checking(new Expectations() {{
                one(contextVerifier).verifyContext();
            }});
            
            context.createOperator("someToggleButton");
        }
    }
}
