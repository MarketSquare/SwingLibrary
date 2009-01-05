package org.robotframework.swing.checkbox;

import javax.swing.JCheckBox;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JCheckBoxOperator;
import org.robotframework.swing.operator.ComponentWrapper;

public class CheckBoxOperator extends JCheckBoxOperator implements ComponentWrapper {
    public CheckBoxOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public CheckBoxOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }
    
    public CheckBoxOperator(JCheckBox source) {
        super(source);
    }
}
