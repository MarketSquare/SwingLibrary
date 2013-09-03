package org.robotframework.swing.textcomponent;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JTextComponentOperator;
import org.robotframework.swing.operator.ComponentWrapper;

public class SwingTextComponentOperator extends JTextComponentOperator implements ComponentWrapper {
    public SwingTextComponentOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public SwingTextComponentOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }

    /*
     * We want to let the application do whatever it wants with the inputs the textfield receives.
     */
    @Override
    public boolean getVerification() {
        return false;
    }
}
