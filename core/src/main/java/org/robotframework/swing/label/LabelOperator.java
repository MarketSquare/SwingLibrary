package org.robotframework.swing.label;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.robotframework.swing.operator.ComponentWrapper;

public class LabelOperator extends JLabelOperator implements ComponentWrapper {
    public LabelOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }

    public LabelOperator(ContainerOperator container, int index) {
        super(container, index);
    }
}
