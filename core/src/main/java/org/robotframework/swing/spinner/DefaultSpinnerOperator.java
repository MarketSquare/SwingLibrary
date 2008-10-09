package org.robotframework.swing.spinner;

import org.netbeans.jemmy.operators.JSpinnerOperator;

public class DefaultSpinnerOperator implements SpinnerOperator {
    private final JSpinnerOperator spinnerOperator;

    public DefaultSpinnerOperator(JSpinnerOperator spinnerOperator) {
        this.spinnerOperator = spinnerOperator;
    }

    @Override
    public void setValue(Object value) {
        spinnerOperator.setValue(value);
    }

    @Override
    public Object getSource() {
        return spinnerOperator.getSource();
    }

    @Override
    public Object getValue() {
        return spinnerOperator.getValue();
    }
}
