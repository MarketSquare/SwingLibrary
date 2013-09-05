package org.robotframework.swing.textcomponent;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.swing.operator.ComponentWrapper;


public class AWTTextComponentOperator extends org.netbeans.jemmy.operators.TextComponentOperator implements ComponentWrapper {
    public AWTTextComponentOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public AWTTextComponentOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }

    /*
     * We want to let the application do whatever it wants with the inputs the textfield receives.
     */
    @Override
    public boolean getVerification() {
        return false;
    }

    /**
     * For whatever reason clearText fails on AWT component, if the component is not shown on screen.
     * This is to work around that problem.
     **/
    @Override
    public void clearText() {
        super.setText("");
    }
}
