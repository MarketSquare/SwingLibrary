package org.robotframework.swing.operator.combobox;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.robotframework.swing.operator.IOperator;

/**
 * @author Heikki Hulkko
 */
public class MyComboBoxOperator extends JComboBoxOperator implements IOperator {
    public MyComboBoxOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public MyComboBoxOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }
}
