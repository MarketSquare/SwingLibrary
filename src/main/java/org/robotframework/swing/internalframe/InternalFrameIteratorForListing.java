package org.robotframework.swing.internalframe;


import javax.swing.JInternalFrame;
import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

public class InternalFrameIteratorForListing{

    private List<String> resultComponentList = new ArrayList<String>();

    private InternalFrameIteratorForListing() {
    }

    public static java.util.List<String> getFrameList(Container container) {
        InternalFrameIteratorForListing iter = new InternalFrameIteratorForListing();
        iter.processComponent(container);
        return iter.resultComponentList;
    }

    private void processComponent(Component component) {
        operateOnComponent(component);
        if (component instanceof Container) {
            Component[] subComponents = ((Container) component).getComponents();
            for (int i = 0; i < subComponents.length; i++) {
                processComponent(subComponents[i]);
            }
        }
    }

    private void operateOnComponent(Component component) {
        if (JInternalFrame.class.isAssignableFrom(component.getClass())) {
            if(component.isVisible()) {
                resultComponentList.add(componentToString(component));
            }
        }
    }

    private String componentToString(Component component) {
        String title = ((JInternalFrame)component).getTitle();
        if (!"".equals(title))
            return title;
        String componentString = component.toString();
        int indexToStartOfDetails = componentString.indexOf('[');
        if (indexToStartOfDetails == -1)
            return componentString;
        return componentString.substring(0, indexToStartOfDetails);
    }
}
