package org.robotframework.swing.container;

import java.awt.*;
import java.util.ArrayList;

public abstract class ContainerIterator {
    private int level;
    private Container container;
    protected java.util.List<String> resultComponentList;

    public ContainerIterator(Container container) {
        this.container = container;
        resultComponentList = new ArrayList<String>();
    }

    public java.util.List<String> iterate() {
        processComponent(container);
        return resultComponentList;
    }

    public abstract void operateOnComponent(Component component, int level);

    private void processComponent(Component component) {
        operateOnComponent(component, level);
        level++;
        if (component instanceof Container) {
            Component[] subComponents = ((Container) component).getComponents();
            for (int i = 0; i < subComponents.length; i++) {
                processComponent(subComponents[i]);
                level--;
            }
        }
    }

    protected String componentToString(Component component) {
        String componentString = component.toString();
        int indexToStartOfDetails = componentString.indexOf('[');
        if (indexToStartOfDetails == -1)
            return componentString;
        return componentString.substring(0, indexToStartOfDetails);
    }
}
