/*
 * Copyright 2008-2011 Nokia Siemens Networks Oyj
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

package org.robotframework.swing.keyword.context;

import java.awt.*;
import java.lang.reflect.Method;

import org.apache.commons.collections.functors.FalsePredicate;
import org.junit.Assert;
import org.netbeans.jemmy.operators.Operator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.*;
import org.robotframework.swing.factory.OperatorFactory;

import javax.swing.*;

@RobotKeywords
public class ContextKeywords {
    private OperatorFactory<ContainerOperator> operatorFactory = new ContainerOperatorFactory();
    private ContextVerifier contextVerifier = new DefaultContextVerifier();

    @RobotKeyword("Selects a container as current context.\n"
        + "Context has to be a window, dialog, internal frame or panel. "
        + "Requires that a parent context has been selected beforehand using `Select Window` or `Select Dialog`.\n\n"
        + "Example:\n"
        + "| `Select Window`  | Main Window | # Selects the 'parent context' |\n"
        + "| `Select Context` | myPanel     | # Sets 'myPanel' as current context |\n")
    @ArgumentNames({"identifier"})
    public void selectContext(String identifier) {
        ContainerOperator op = operatorFactory.createOperator(identifier);
        verifyContext(op.getSource().getClass());
        Context.setContext(op);
    }

    @RobotKeyword("Returns the component name in current context or title if window or dialog is selected.\n\n"
    	+ "Example:\n"
    	+ "| ${context}= | `Get Current Context` | # Sets the identifier of the current context to a variable |\n")
    public String getCurrentContext() {
		Component component = Context.getContext()
		                             .getSource();
		if (hasTitle(component))
			return titleOf(component);
		return component.getName();
    }

    private boolean hasTitle(Component component) {
    	return component instanceof Frame || component instanceof Dialog;
    }

    private String titleOf(Component component) {
    	try {
			Method m = component.getClass()
			                    .getMethod("getTitle");
			return (String) m.invoke(component);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }

    private static void verifyContext(Class contextClass) {
        Class<? extends Component>[] expectedClasses = new Class[] { Window.class, JPanel.class, Panel.class,
                JInternalFrame.class, JLayer.class };
        boolean assignable = false;
        StringBuilder str = new StringBuilder();
        for (Class<? extends Component> expectedClass : expectedClasses) {
            assignable = assignable || expectedClass.isAssignableFrom(contextClass);
            str.append(expectedClass.getName());
            str.append(" ");
        }

        if (!assignable) {
            Assert.fail("Invalid context " + contextClass.getName() + ". Should be one of (" + str + ")");
        }
    }
}
