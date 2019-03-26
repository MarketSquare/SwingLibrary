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

package org.robotframework.swing.keyword.component;

import org.junit.Assert;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.javalib.reflection.ArgumentConverter;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.component.ComponentOperator;
import org.robotframework.swing.component.ComponentOperatorFactory;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.keyword.utils.OptionalArgsForTableCellAndComponentClicking;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;
import org.robotframework.swing.util.ComponentUtils;

import java.awt.*;
import java.awt.event.InputEvent;
import java.lang.reflect.Method;
import java.util.*;
import java.util.List;

@RobotKeywords
public class ComponentKeywords {
    private final IdentifierParsingOperatorFactory<ComponentOperator> operatorFactory = new ComponentOperatorFactory();
    private final IComponentConditionResolver componentExistenceResolver = new ComponentExistenceResolver(
            operatorFactory);

    @RobotKeyword("Fails if component exists within current context.\n"
            + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`\n\n"
            + "Example:\n" + "| `Component Should Not Exist` | myPanel |\n")
    @ArgumentNames({ "identifier" })
    public void componentShouldNotExist(String identifier) {
        Assert.assertFalse("Component '" + identifier + "' exists",
                componentExistenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Fails if component does not exist within current context.\n"
            + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`\n\n"
            + "Example:\n" + "| `Component Should Not Exist` | myPanel |\n")
    @ArgumentNames({ "identifier" })
    public void componentShouldExist(String identifier) {
        Assert.assertTrue("Component '" + identifier + "' does not exist",
                componentExistenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Clicks on a component, optionally using click count, a specific mouse button and keyboard modifiers.\n\n"
            + "The codes used for mouse button and key modifiers are the field names from ``java.awt.event.InputEvent``. "
            + "For example: ``BUTTON1_MASK``, ``CTRL_MASK``, ``ALT_MASK``, ``ALT_GRAPH_MASK``, ``SHIFT_MASK``, and ``META_MASK``.\n\n"
            + "*Note:* Some keys have more convinient case insensitive aliases that can be used: ``LEFT BUTTON``, ``RIGHT BUTTON``, ``SHIFT``, "
            + "``CTRL``, ``ALT``, ``META``\n\n"
            + "Examples:\n"
            + "| `Click On Component`  | myComponent | # Double clicks with mouse button 2 on the component ... |\n"
            + "| ... | 2 | RIGHT BUTTON | ALT | # ... while holding down the ALT key |\n")
    @ArgumentNames({ "identifier", "clickCountString=1", "buttonString=BUTTON1_MASK", "*keyModifierStrings" })
    public void clickOnComponent(String identifier, String[] optionalArgs) {
        OptionalArgsForTableCellAndComponentClicking optArgs = new OptionalArgsForTableCellAndComponentClicking(optionalArgs);
        operator(identifier).clickOnComponent(
                optArgs.clickCount(),
                optArgs.button(),
                optArgs.keyModifiers());
    }

    @RobotKeyword("Right clicks on a component.\n\n" + "Example:\n"
            + "| `Right Click On Component` | myComponent |")
    @ArgumentNames({ "identifier" })
    public void rightClickOnComponent(String identifier) {
        operator(identifier).clickMouse(1, InputEvent.BUTTON3_MASK);
    }

    @RobotKeyword("Returns the component's tooltip text.\n\n" + "Example:\n"
            + "| ${tooltip}= | `Get Tooltip Text` | saveButton |\n"
            + "| `Should Be Equal`    | Save | ${tooltip} |\n")
    @ArgumentNames({ "identifier" })
    public String getTooltipText(String identifier) {
        return operator(identifier).getToolTipText();
    }

    @RobotKeyword("Sets focus to the component.\n"
            + "Useful for example when sending keyboard events to a component.\n\n"
            + "Example:\n"
            + "| `Focus To Component`     | myTextField |           | |\n"
            + "| `Send Keyboard Event`    | VK_C          | CTRL_MASK | # paste from clipboard |\n")
    @ArgumentNames({ "identifier" })
    public void focusToComponent(String identifier) {
        operator(identifier).getFocus();
    }

    @RobotKeyword("Selects an item from the components context popup menu.\n"
            + "Does a right click on the component and selects the specified menu item from the popup menu.\n\n"
            + "Example:\n"
            + "| `Select From Popup Menu` | myComponent | Actions | Do something |\n")
    @ArgumentNames({ "identifier", "menuPath" })
    public void selectFromPopupMenu(String identifier, String menuPath) {
        JPopupMenuOperator popup = operator(identifier).invokePopup();
        popup.pushMenuNoBlock(menuPath, new EqualsStringComparator());
    }

    @RobotKeyword("Gets item names from the components context popup menu.\n"
            + "Does a right click on the component and retrieves the specified menu items from the popup menu.\n\n"
            + "Example:\n"
            + "| @{items}= | `Get Menu Items From Popup Menu` | myComponent | Actions |\n"
            + "| `Should Contain` | ${items} | Do something |")
    @ArgumentNames({ "identifier", "menuPath" })
    public List<String> getMenuItemsFromPopupMenu(final String identifier, final String menuPath) {
        JPopupMenuOperator popup = operator(identifier).invokePopup();
        if (menuPath == null || "".equals(menuPath)) {
            return ComponentUtils.getParsedElements(popup.getSubElements());
        }
        JMenuItemOperator subItem = popup.showMenuItem(menuPath);
        return subItem.getSubElements().length < 1 ? new ArrayList<String>() :
                ComponentUtils.getParsedElements(subItem.getSubElements()[0].getSubElements());
    }

    @RobotKeyword("Checks that component is visible.\n"
            +"Even if one pixel of the component is visible, this keyword will pass.\n\n"
            +"Example:\n"
            + "| `Component Should Be Visible` | myComponent |\n")
         @ArgumentNames({ "identifier"})
         public void componentShouldBeVisible(String identifier) {
        Rectangle visible = operator(identifier).getVisibleRect();
        Assert.assertFalse(identifier + " is not visible", visible.isEmpty());
    }

    @RobotKeyword("Checks that component is not visible.\n"
            +"Fails if even one pixel of the component is visible.\n\n"
            +"Example:\n"
            + "| `Component Should Not Be Visible` | myComponent |\n")
    @ArgumentNames({ "identifier"})
    public void componentShouldNotBeVisible(String identifier) {
        Rectangle visible = operator(identifier).getVisibleRect();
        Assert.assertTrue(identifier + " is visible. Visible " + visible.toString(), visible.isEmpty());
    }

    @RobotKeyword("Scrolls component to view.\n\n"
            +"Example:\n"
            + "| `Scroll Component To View` | myComponent |\n")
    @ArgumentNames({ "identifier"})
    public void scrollComponentToView(String identifier) {
        operator(identifier).scrollRectToVisible(new Rectangle(100, 100));
    }

    @RobotKeyword("List methods of components object.\n"
            +"When working with custom components you may use this keyword to discover methods you can call "
            +"with `Call Component Method` keyword.\n\n"
            +"Example:\n"
            + "| `List Component Methods` | myComponent |\n")
    @ArgumentNames({"identifier"})
    public String[] listComponentMethods(String identifier) {
        Class klass = operator(identifier).getSource().getClass();
        ArrayList<String> list = new ArrayList<String>();
        System.out.println("*INFO*");
        while (klass != null) {
            String name = String.format("*%s*", klass.getName());
            System.out.println(name);
            list.add(name);
            for (Method m : klass.getDeclaredMethods()) {
                String entry = getMethodDescription(m);
                System.out.println(entry);
                list.add(entry);
            }
            klass = klass.getSuperclass();
        }
        return list.toArray(new String[list.size()]);
    }

    private String getMethodDescription(Method m) {
        String entry = m.getReturnType().getName() + " ";
        entry += m.getName();
        entry += "(";
        Class[] args = m.getParameterTypes();
        for (int i = 0; i < args.length; i++) {
            entry += args[i].getName();
            if (i != args.length - 1)
                entry += ", ";
        }
        return entry + ")";
    }

    private Method getMethodByNameAndArgumentCount(Class klass, String name, int argCount) {
        for (Method m : klass.getMethods()) {
            if (m.getName().equals(name) && m.getParameterTypes().length == argCount)
                return m;
        }
        throw new RuntimeException(String.format("Method \"%s\" with %d argument(s) doesn't exist.", name, argCount));
    }

    @RobotKeyword("Calls a method from specified component.\n\n"
                  + "Arguments are automatically converted if possible to type expected by the method.\n\n"
                  + "Example:\n"
                  + "| `Call Component Method` | buttonId | setToolTipText | new tooltip text |")
    @ArgumentNames({"identifier", "method", "*args"})
    public Object callComponentMethod(String identifier, String method, String[] args) {
        Object component = operator(identifier).getSource();
        Class klass = component.getClass();
        Method m = getMethodByNameAndArgumentCount(klass, method, args.length);

        try {
            return m.invoke(component, new ArgumentConverter(m.getParameterTypes()).convertArguments(args));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private ComponentOperator operator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }

    private int getTimes(String[] times) {
        return times.length == 1 ? Integer.parseInt(times[0]) : 1;
    }
}
