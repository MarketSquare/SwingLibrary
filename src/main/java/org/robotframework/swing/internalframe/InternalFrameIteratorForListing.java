package org.robotframework.swing.internalframe;

import org.robotframework.swing.container.ContainerIterator;

import javax.swing.*;
import java.awt.*;

public class InternalFrameIteratorForListing extends ContainerIterator{
    public InternalFrameIteratorForListing(Container container) {
        super(container);
    }

    @Override
    public void operateOnComponent(Component component, int level) {
        if (JInternalFrame.class.isAssignableFrom(component.getClass())) {
            if(component.isVisible()) {
                resultComponentList.add(componentToString(component));
            }
        }
    }

    @Override
    protected String componentToString(Component component) {
        String title = ((JInternalFrame)component).getTitle();
        if (title.isEmpty()) {
            return super.componentToString(component);
        }
        return title;
    }
}
