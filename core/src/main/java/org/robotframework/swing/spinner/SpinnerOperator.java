package org.robotframework.swing.spinner;

import org.robotframework.swing.operator.IOperator;

/**
 * @author Heikki Hulkko
 */
public interface SpinnerOperator extends IOperator {
    Object getValue();
    void increase();
    void increaseToMaximum();
    void decrease();
    void decreaseToMinimum();
}
