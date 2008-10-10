package org.robotframework.swing.spinner;

import org.netbeans.jemmy.operators.JSpinnerOperator;

public class DefaultSpinnerOperator implements SpinnerOperator {
    private final JSpinnerOperator spinnerOperator;

    public DefaultSpinnerOperator(JSpinnerOperator spinnerOperator) {
        this.spinnerOperator = spinnerOperator;
    }

    @Override
    public Object getSource() {
        return spinnerOperator.getSource();
    }

    @Override
    public Object getValue() {
        return spinnerOperator.getValue();
    }

    @Override
    public void decrease() {
        spinnerOperator.getDecreaseOperator().push();
    }

    @Override
    public void decreaseToMinimum() {
        spinnerOperator.scrollToMinimum();
    }

    @Override
    public void increase() {
        spinnerOperator.getIncreaseOperator().push();
    }

    @Override
    public void increaseToMaximum() {
        spinnerOperator.scrollToMaximum();
    }
}
