package org.robotframework.swing.tab;

import javax.swing.JTabbedPane;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.robotframework.swing.operator.ComponentWrapper;

public class TabbedPaneOperator extends JTabbedPaneOperator implements ComponentWrapper {
    public TabbedPaneOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }

    public TabbedPaneOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public TabbedPaneOperator(JTabbedPane source) {
        super(source);
    }

    public TabbedPaneOperator(ContainerOperator container) {
        super(container);
    }
}
