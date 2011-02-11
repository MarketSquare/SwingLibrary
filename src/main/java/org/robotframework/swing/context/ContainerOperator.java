package org.robotframework.swing.context;

import org.netbeans.jemmy.ComponentChooser;
import org.robotframework.swing.operator.ComponentWrapper;

public class ContainerOperator extends org.netbeans.jemmy.operators.ContainerOperator implements ComponentWrapper {
    public ContainerOperator(org.netbeans.jemmy.operators.ContainerOperator container, int index) {
        super(container, index);
    }

    public ContainerOperator(org.netbeans.jemmy.operators.ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }
}
