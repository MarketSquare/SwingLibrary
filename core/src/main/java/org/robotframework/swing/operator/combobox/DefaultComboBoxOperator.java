package org.robotframework.swing.operator.combobox;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.robotframework.swing.operator.IOperator;

/**
 * @author Heikki Hulkko
 */
public class DefaultComboBoxOperator extends JComboBoxOperator implements IOperator {
    public DefaultComboBoxOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public DefaultComboBoxOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }
}
