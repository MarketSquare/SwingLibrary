package org.robotframework.swing.operator.tab;

import javax.swing.JTabbedPane;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.robotframework.swing.operator.IOperator;

/**
 * @author Heikki Hulkko
 */
public class DefaultTabbedPaneOperator extends JTabbedPaneOperator implements IOperator {
    public DefaultTabbedPaneOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }

    public DefaultTabbedPaneOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public DefaultTabbedPaneOperator(JTabbedPane source) {
        super(source);
    }

    public DefaultTabbedPaneOperator(ContainerOperator container) {
        super(container);
    }
}
