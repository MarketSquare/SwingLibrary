package org.robotframework.swing.operator.button;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.robotframework.swing.operator.IOperator;

/**
 * @author Heikki Hulkko
 */
public class DefaultButtonOperator extends JButtonOperator implements IOperator {
    public DefaultButtonOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public DefaultButtonOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }
}
