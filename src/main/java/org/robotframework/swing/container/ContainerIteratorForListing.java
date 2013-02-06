package org.robotframework.swing.container;

import org.robotframework.swing.keyword.development.ComponentOccurences;


import java.awt.*;

public class ContainerIteratorForListing extends ContainerIterator {

    public String formatted = "";
    private ComponentOccurences occurences = new ComponentOccurences();
    public ContainerIteratorForListing(Container container) {
        super(container);
    }

    private String spacesToFormatOutputAsTree(int level) {
        String result = "";
        for (int i = 0; i < level; i++)
            result +="   ";
        return result;
    }

    public void operateOnComponent(Component component, int level) {
        String result =spacesToFormatOutputAsTree(level);
        String componentName = componentToString(component);
        result +=level + " " + componentName + " " + occurences.countIndexOf(component) + ": " + component.getName()+"\n";
        System.out.print(result);
        formatted += result;
        resultComponentList.add(componentName);
    }
}
