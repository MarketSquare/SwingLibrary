package org.robotframework.swing.spinner;

import java.awt.Component;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JSpinnerOperator;

@RunWith(JDaveRunner.class)
public class DefaultSpinnerOperatorSpec extends Specification<SpinnerOperator> {
    public class Any {
        private JSpinnerOperator spinnerOperator;

        public SpinnerOperator create() {
            spinnerOperator = mock(JSpinnerOperator.class);
            return new DefaultSpinnerOperator(spinnerOperator);
        }

        public void delegatesSetValueToUnderlyingOperator() {
            final String spinnerValue = "newValue";
            checking(new Expectations() {{
                one(spinnerOperator).setValue(spinnerValue);
            }});

            context.setValue(spinnerValue);
        }

        public void delegatesGetSourceToUnderlyingOperator() {
            final Component source = dummy(Component.class);
            checking(new Expectations() {{
                one(spinnerOperator).getSource(); will(returnValue(source));
            }});

            specify(context.getSource(), must.equal(source));
        }

        public void delegatesGetValueToUnderlyingOperator() {
            final Object someValue = new Object();
            checking(new Expectations() {{
                one(spinnerOperator).getValue(); will(returnValue(someValue));
            }});

            specify(context.getValue(), must.equal(someValue));
        }

    }
}
