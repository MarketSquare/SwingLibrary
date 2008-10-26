package org.robotframework.swing.operator.label;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JLabelOperator;
import org.robotframework.swing.operator.IOperator;

/**
 * @author Heikki Hulkko
 */
public class MyLabelOperator extends JLabelOperator implements IOperator {
    public MyLabelOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }

    public MyLabelOperator(ContainerOperator container, int index) {
        super(container, index);
    }
}
