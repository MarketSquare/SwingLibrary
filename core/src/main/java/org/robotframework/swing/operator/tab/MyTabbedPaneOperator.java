package org.robotframework.swing.operator.tab;

import javax.swing.JTabbedPane;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JTabbedPaneOperator;
import org.robotframework.swing.operator.IOperator;

/**
 * @author Heikki Hulkko
 */
public class MyTabbedPaneOperator extends JTabbedPaneOperator implements IOperator {
    public MyTabbedPaneOperator(ContainerOperator container, ComponentChooser chooser) {
        super(container, chooser);
    }

    public MyTabbedPaneOperator(ContainerOperator container, int index) {
        super(container, index);
    }

    public MyTabbedPaneOperator(JTabbedPane source) {
        super(source);
    }

    public MyTabbedPaneOperator(ContainerOperator container) {
        super(container);
    }
}
