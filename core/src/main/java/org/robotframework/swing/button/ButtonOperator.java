package org.robotframework.swing.button;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JButtonOperator;

/**
 * @author Heikki Hulkko
 */
public class ButtonOperator extends JButtonOperator implements AbstractButtonOperator {
    public ButtonOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public ButtonOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }
}
