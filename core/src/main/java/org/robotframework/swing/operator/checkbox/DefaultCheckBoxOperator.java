package org.robotframework.swing.operator.checkbox;

import javax.swing.JCheckBox;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.robotframework.swing.operator.IOperator;

/**
 * @author Heikki Hulkko
 */
public class DefaultCheckBoxOperator extends JCheckBoxOperator implements IOperator {
    public DefaultCheckBoxOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public DefaultCheckBoxOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }
    
    public DefaultCheckBoxOperator(JCheckBox source) {
        super(source);
    }
}
