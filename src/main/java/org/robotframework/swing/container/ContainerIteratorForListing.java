package org.robotframework.swing.container;

import org.robotframework.swing.keyword.development.ComponentOccurences;


import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

public class ContainerIteratorForListing {

    private List<String> formatted = new ArrayList<String>();
    private List<String> unformatted = new ArrayList<String>();
    private int level = 0;
    private ComponentOccurences occurences = new ComponentOccurences();

    private ContainerIteratorForListing() {
    }

    public static List<String> getComponentList(Container container) {
        ContainerIteratorForListing cont = new ContainerIteratorForListing();
        cont.processComponent(container);
        return cont.unformatted;
    }

    public static List<String> getFormattedComponentList(Container container) {
        ContainerIteratorForListing cont = new ContainerIteratorForListing();
        cont.processComponent(container);
        return cont.formatted;
    }

    private void processComponent(Component component) {
        operateOnComponent(component);
        level++;
        if (component instanceof Container) {
            Component[] subComponents = ((Container) component).getComponents();
            for (Component subComponent: subComponents) {
                processComponent(subComponent);
                level--;
            }
        }
    }

    private void operateOnComponent(Component component) {
        String componentName = componentToString(component);
        String formattedName =spacesToFormatOutputAsTree(level) +
                " Level: " + level +
                " Component: " + componentName +
                " Index: " + occurences.countIndexOf(component) +
                " Name: " + component.getName();
        formatted.add(formattedName);
        unformatted.add(componentName);
        System.out.println(formattedName);
    }

    private String componentToString(Component component) {
        String componentString = component.toString();
        int indexToStartOfDetails = componentString.indexOf('[');
        if (indexToStartOfDetails == -1)
            return componentString;
        return componentString.substring(0, indexToStartOfDetails);
    }

    private String spacesToFormatOutputAsTree(int level) {
        String result = "";
        for (int i = 0; i < level; i++)
            result +="   ";
        return result;
    }
}
