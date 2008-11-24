package org.robotframework.swing.combobox;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.robotframework.swing.operator.ComponentWrapper;

/**
 * @author Heikki Hulkko
 */
public class ComboBoxOperator extends JComboBoxOperator implements ComponentWrapper {
    public ComboBoxOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public ComboBoxOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }
}
