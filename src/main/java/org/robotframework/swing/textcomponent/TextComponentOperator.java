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
package org.robotframework.swing.textcomponent;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TextComponentOperator  {


    private final SwingTextComponentOperator swingOperator;
    private final AWTTextComponentOperator awtOperator;

    public TextComponentOperator(SwingTextComponentOperator swing) {
        swingOperator = swing;
        awtOperator = null;
    }

    public TextComponentOperator(AWTTextComponentOperator awt) {
        swingOperator = null;
        awtOperator = awt;
    }

    public void setText(String text) {
        if (swingOperator != null)
            swingOperator.setText(text);
        else
            awtOperator.setText(text);
    }

    public boolean isEditable() {
        if (swingOperator != null)
            return swingOperator.isEditable();
        else
            return awtOperator.isEditable();
    }

    public boolean isEnabled() {
        if (swingOperator != null)
            return swingOperator.isEnabled();
        else
            return awtOperator.isEnabled();
    }

    public void setEnabled(boolean enabled) {
        if (swingOperator != null)
            swingOperator.setEnabled(enabled);
        else
            awtOperator.setEnabled(enabled);
    }

    public String getText() {
        if (swingOperator!=null)
            return swingOperator.getText();
        else
            return awtOperator.getText();
    }

    public void typeText(String text) {
        if (swingOperator!=null)
            swingOperator.typeText(text);
        else
            awtOperator.typeText(text);
    }

    public void clearText() {
        if (swingOperator!=null)
            swingOperator.clearText();
        else
            awtOperator.clearText();
    }

    public void makeComponentVisible() {
        if (swingOperator!=null)
            swingOperator.makeComponentVisible();
        else
            awtOperator.makeComponentVisible();
    }

    public void selectAll() {
        if (swingOperator!=null)
            swingOperator.selectAll();
        else
            awtOperator.selectAll();
    }

    private Object operator;

    public Object invoke(String methodName, Object... arguments) {
        Class[] classes = new Class[arguments.length];
        for (int i=0; i < classes.length; i++) {
            classes[i] = arguments[i].getClass();
        }
        try {
            Method m = operator.getClass().getMethod(methodName, classes);
            return m.invoke(operator, new Object[] {});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
