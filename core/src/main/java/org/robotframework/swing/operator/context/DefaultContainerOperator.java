package org.robotframework.swing.operator.context;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.swing.operator.IOperator;

/**
 * @author Heikki Hulkko
 */
public class DefaultContainerOperator extends ContainerOperator implements IOperator {
    public DefaultContainerOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public DefaultContainerOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }
}
