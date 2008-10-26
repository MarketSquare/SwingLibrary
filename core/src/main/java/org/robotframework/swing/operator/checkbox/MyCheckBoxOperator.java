package org.robotframework.swing.operator.checkbox;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.robotframework.swing.operator.IOperator;

/**
 * @author Heikki Hulkko
 */
public class MyCheckBoxOperator extends JCheckBoxOperator implements IOperator {
    public MyCheckBoxOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public MyCheckBoxOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }
}
