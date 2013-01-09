package org.robotframework.swing.util;

import org.netbeans.jemmy.TimeoutExpiredException;
import org.robotframework.swing.arguments.ArgumentParser;

import java.util.EmptyStackException;

public class ComponentHasChildrenResolver implements IComponentConditionResolver {

    private ArgumentParser<?> identifierParser;

    public ComponentHasChildrenResolver(ArgumentParser<?> identifierParser) {
        this.identifierParser = identifierParser;
    }

    public boolean satisfiesCondition(String componentIdentifier) {
        try {
            identifierParser.parseArgument(componentIdentifier);
        } catch (IllegalStateException e) {
            return false;
        } catch(TimeoutExpiredException e) {
            return false;
        }
        return true;
    }
}
