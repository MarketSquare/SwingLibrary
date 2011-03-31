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


package org.robotframework.swing.combobox;

import java.awt.Component;

import javax.swing.ComboBoxModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import org.laughingpanda.jretrofit.AllMethodsNotImplementedException;
import org.laughingpanda.jretrofit.Retrofit;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.robotframework.swing.chooser.WithText;
import org.robotframework.swing.common.SmoothInvoker;

public class ItemTextExtractor {
    private final JComboBoxOperator comboboxOperator;

    public ItemTextExtractor(JComboBoxOperator comboboxOperator) {
        this.comboboxOperator = comboboxOperator;
    }

    public int itemCount() {
        return comboboxModel().getSize();
    }
    
    public String getTextFromRenderedComponent(int itemIndex) {
        WithText textElement = getComponentWithText(itemIndex);
        return textElement.getText();
    }
    
    private ComboBoxModel comboboxModel() {
        return comboboxOperator.getModel();
    }

    private WithText getComponentWithText(int itemIndex) {
        try {
            return coerceToWithText(getRenderedComponent(itemIndex));
        } catch (AllMethodsNotImplementedException e) {
            return wrapElementToWithText(itemIndex);
        }
    }
    
    private Component getRenderedComponent(final int itemIndex) {
        final JList popupList = comboboxOperator.waitList();
        final Object element = comboboxModel().getElementAt(itemIndex);
        final boolean isSelected = comboboxOperator.getSelectedIndex() == itemIndex;
        final boolean hasFocus = comboboxOperator.hasFocus();
        final ListCellRenderer cellRenderer = comboboxOperator.getRenderer();
        
        return new SmoothInvoker<Component>() {
            public Object work() {
                return cellRenderer.getListCellRendererComponent(popupList, element, itemIndex, isSelected, hasFocus);
            }
        }.invoke();
    }    
    
    private WithText coerceToWithText(Object element) {
        return (WithText) Retrofit.complete(element, WithText.class);
    }

    private WithText wrapElementToWithText(final int itemIndex) {
        return new WithText() {
            public String getText() {
                return comboboxModel().getElementAt(itemIndex).toString();
            }
        };
    }
}
