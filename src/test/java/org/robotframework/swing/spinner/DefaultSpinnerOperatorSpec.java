package org.robotframework.swing.spinner;

import java.awt.Component;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JSpinnerOperator;

@RunWith(JDaveRunner.class)
public class DefaultSpinnerOperatorSpec extends Specification<SpinnerOperator> {
    private JSpinnerOperator spinnerOperator;

    public class Any {
        public SpinnerOperator create() {
            return createDefaultSpinnerOperatorWithMockDelegate();
        }

        public void delegatesGetSource() {
            final Component source = dummy(Component.class);
            checking(new Expectations() {{
                one(spinnerOperator).getSource(); will(returnValue(source));
            }});

            specify(context.getSource(), must.equal(source));
        }

        public void delegatesGetValue() {
            final Object someValue = new Object();
            checking(new Expectations() {{
                one(spinnerOperator).getValue(); will(returnValue(someValue));
            }});

            specify(context.getValue(), must.equal(someValue));
        }

    }

    public class ChangingValue {
        private JButtonOperator changeButton;

        public SpinnerOperator create() {
            changeButton = mock(JButtonOperator.class);
            return createDefaultSpinnerOperatorWithMockDelegate();
        }

        public void increasesValue() {
            checking(new Expectations() {{
                one(spinnerOperator).getIncreaseOperator(); will(returnValue(changeButton));
                one(changeButton).push();
            }});

            context.increase();
        }

        public void decreasesValue() {
            checking(new Expectations() {{
                one(spinnerOperator).getDecreaseOperator(); will(returnValue(changeButton));
                one(changeButton).push();
            }});

            context.decrease();
        }

        public void increasesToMaximum() {
            checking(new Expectations() {{
                one(spinnerOperator).scrollToMaximum();
            }});

            context.increaseToMaximum();
        }

        public void decreasesToMinimum() {
            checking(new Expectations() {{
                one(spinnerOperator).scrollToMinimum();
            }});

            context.decreaseToMinimum();
        }
    }

    private SpinnerOperator createDefaultSpinnerOperatorWithMockDelegate() {
        spinnerOperator = mock(JSpinnerOperator.class);
        return new DefaultSpinnerOperator(spinnerOperator);
    }
}
