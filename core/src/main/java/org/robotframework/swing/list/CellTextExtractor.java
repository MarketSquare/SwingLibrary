package org.robotframework.swing.list;

import java.awt.Component;

import javax.swing.ListModel;

import org.laughingpanda.jretrofit.AllMethodsNotImplementedException;
import org.laughingpanda.jretrofit.Retrofit;
import org.netbeans.jemmy.operators.JListOperator;
import org.robotframework.swing.chooser.WithText;
import org.robotframework.swing.common.SmoothInvoker;

public class CellTextExtractor {

    private JListOperator jListOperator;
    
    public CellTextExtractor(JListOperator jListOperator) {
        this.jListOperator = jListOperator;
    }

    public int itemCount() {
        return listModel().getSize();
    }

    private ListModel listModel() {
        return jListOperator.getModel();
    }

    public String getTextFromRenderedComponent(int itemIndex) {
        if (itemIndex == -1)
            return null;
        WithText textElement = getComponentWithText(itemIndex);
        return textElement.getText();
    }

    private WithText getComponentWithText(int itemIndex) {
        try {
            return coerceToWithText(getRenderedComponent(itemIndex));
        } catch (AllMethodsNotImplementedException e) {
            return wrapElementToWithText(itemIndex);
        }
    }
    
    private WithText coerceToWithText(Object element) {
        return (WithText) Retrofit.complete(element, WithText.class);
    }
    
    private Component getRenderedComponent(final int itemIndex) {
        final boolean isSelected = jListOperator.getSelectedIndex() == itemIndex;
        final boolean hasFocus = jListOperator.hasFocus();
        return new SmoothInvoker<Component>() {
            public Object work() {
                return jListOperator.getRenderedComponent(itemIndex, isSelected, hasFocus);
            }
        }.invoke();
    } 
    
    private WithText wrapElementToWithText(final int itemIndex) {
        return new WithText() {
            public String getText() {
                return listModel().getElementAt(itemIndex).toString();
            }
        };
    }
}
