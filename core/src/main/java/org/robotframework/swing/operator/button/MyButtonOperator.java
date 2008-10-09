package org.robotframework.swing.operator.button;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.robotframework.swing.operator.IOperator;

public class MyButtonOperator extends JButtonOperator implements IOperator {
    public MyButtonOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public MyButtonOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }
}
