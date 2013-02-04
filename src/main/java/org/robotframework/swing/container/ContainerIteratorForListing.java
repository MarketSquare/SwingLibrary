package org.robotframework.swing.container;

import org.robotframework.swing.keyword.development.ComponentOccurences;

import java.awt.*;

public class ContainerIteratorForListing extends ContainerIterator {

    private ComponentOccurences occurences = new ComponentOccurences();
    public ContainerIteratorForListing(Container container) {
        super(container);
    }

    private void printSpacesToFormatOutputAsTree(int level) {
        for (int i = 0; i < level; i++)
            System.out.print("   ");
    }

    public void operateOnComponent(Component component, int level) {
        printSpacesToFormatOutputAsTree(level);
        String componentName = componentToString(component);
        System.out.println(level + " " + componentName + " " + occurences.countIndexOf(component) + ": " + component.getName());
        resultComponentList.add(componentName);
    }
}
