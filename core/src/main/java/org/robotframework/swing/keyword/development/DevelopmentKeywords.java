/*
 * Copyright 2008 Nokia Siemens Networks Oyj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.robotframework.swing.keyword.development;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.operator.IOperator;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class DevelopmentKeywords {
    private ArrayList resultComponentList = new ArrayList();

    @RobotKeyword("Prints components (their types and their internal names) from the selected context.\n"
        + "See keywords, `Select Window`, `Select Dialog` and `Select Context` for details about context.\n\n"
        + "Example:\n"
        + "| Select Main Window         |\n"
        + "| List Components In Context |\n")
    public String listComponentsInContext() {
        IOperator operator = Context.getContext();
        new ContainerIteratorForListing((Container) operator.getSource()).iterate();
        return resultComponentList.toString();
    }

    private class ContainerIteratorForListing extends ContainerIterator {
        public ContainerIteratorForListing(Container container) {
            super(container);
        }

        public void operateOnComponent(Component component, int level) {
            printSpacesToFormatOutputAsTree(level);
            String componentName = componentToString(component);
            System.out.println(level + " " + componentName + ": " + component.getName());
            resultComponentList.add(componentName);
        }
    }

    private void printSpacesToFormatOutputAsTree(int level) {
        for (int i = 0; i < level; i++) {
            System.out.print("   ");
        }
    }

    private String componentToString(Component component) {
        String componentString = component.toString();
        int indexToStartOfDetails = componentString.indexOf('[');
        if (indexToStartOfDetails == -1) {
            return componentString;
        } else {
            return componentString.substring(0, indexToStartOfDetails);
        }
    }
}

abstract class ContainerIterator {
    public abstract void operateOnComponent(Component component, int level);
    private int level;
    private Container container;

    public ContainerIterator(Container container) {
        this.container = container;
    }

    public void iterate() {
        processComponent(container);
    }

    private void processComponent(Component component) {
        operateOnComponent(component, level);
        level++;
        if (component instanceof Container) {
            Container container = (Container) component;
            Component[] subComponents = container.getComponents();
            for (int i = 0; i < subComponents.length; i++) {
                processComponent(subComponents[i]);
                level--;
            }
        }
    }
}
