package org.robotframework.swing.list;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JListOperator;
import org.robotframework.swing.operator.ComponentWrapper;

/**
 * @author Heikki Hulkko
 */
public class ListOperator extends JListOperator implements ComponentWrapper {
    public ListOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }

    public ListOperator(ContainerOperator container, int index) {
        super(container, index);
    }
}
