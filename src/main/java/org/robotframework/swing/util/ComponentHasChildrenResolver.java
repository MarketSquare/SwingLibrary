package org.robotframework.swing.util;

import org.robotframework.swing.arguments.ArgumentParser;

public class ComponentHasChildrenResolver implements IComponentConditionResolver {

    private ArgumentParser<?> identifierParser;

    public ComponentHasChildrenResolver(ArgumentParser<?> identifierParser) {
        this.identifierParser = identifierParser;
    }

    public boolean satisfiesCondition(String componentIdentifier) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
