package org.robotframework.swing.operator.context;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.swing.operator.IOperator;

/**
 * @author Heikki Hulkko
 */
public class MyContainerOperator extends ContainerOperator implements IOperator {
    public MyContainerOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public MyContainerOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }
}
