package org.robotframework.swing.tab;

import java.awt.Container;

import org.robotframework.swing.operator.ComponentWrapper;

public class TabOperator extends org.netbeans.jemmy.operators.ContainerOperator implements ComponentWrapper {
    public TabOperator(Container container) {
        super(container);
    }
}
