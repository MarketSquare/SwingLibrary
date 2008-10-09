package org.robotframework.swing.spinner;

import org.robotframework.swing.operator.IOperator;

public interface SpinnerOperator extends IOperator {
    void setValue(Object value);
    Object getValue();
}
