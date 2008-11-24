package org.robotframework.swing.spinner;

import org.robotframework.swing.operator.ComponentWrapper;

/**
 * @author Heikki Hulkko
 */
public interface SpinnerOperator extends ComponentWrapper {
    Object getValue();
    void increase();
    void increaseToMaximum();
    void decrease();
    void decreaseToMinimum();
}
