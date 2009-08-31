package org.robotframework.swing.combobox;

import java.awt.Component;

import javax.swing.ComboBoxModel;

import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.operator.ComponentWrapper;

public class ComboBoxOperator extends IdentifierSupport implements ComponentWrapper {
    private final JComboBoxOperator comboboxOperator;
    private ItemTextExtractor itemTextExtractor;


    public ComboBoxOperator(JComboBoxOperator jComboboxOperator) {
        comboboxOperator = jComboboxOperator;
        itemTextExtractor = new ItemTextExtractor(jComboboxOperator);
    }
    
    ComboBoxOperator(JComboBoxOperator jComboboxOperator, ItemTextExtractor itemTextExtractor) {
        this.comboboxOperator = jComboboxOperator;
        this.itemTextExtractor = itemTextExtractor;
    }

    public Component getSource() {
        return comboboxOperator.getSource();
    }

    public void selectItem(String comboItemIdentifier) {
        comboboxOperator.pushComboButton();
        int itemIndex = findItemIndex(comboItemIdentifier);
        comboboxOperator.selectItem(itemIndex);
    }
    
    public Object getSelectedItem() {
        return comboboxOperator.getSelectedItem();
    }

    public boolean isEnabled() {
        return comboboxOperator.isEnabled();
    }
    
    public void typeText(String text) {
        comboboxOperator.clearText();
        comboboxOperator.typeText(text);
    }

    public Object[] getValues() {
        ComboBoxModel model = comboboxOperator.getModel();
        Object[] values = new Object[model.getSize()];
        for (int i = 0; i < values.length; i++) {
            values[i] = model.getElementAt(i);
        }
        return values;
    }
    
    private int findItemIndex(String comboItemIdentifier) {
        if (isIndex(comboItemIdentifier)) {
            return asIndex(comboItemIdentifier);
        } else {
            return findItemIndexFromRenderedText(comboItemIdentifier);
        }
    }

    private int findItemIndexFromRenderedText(String expectedText) {
        try {
            return findItemIndexWithRenderer(expectedText);
        } finally {
            hidePopup();
        }
        
    }
    
    private int findItemIndexWithRenderer(String expectedText) {
        for (int itemIndex = 0; itemIndex < itemTextExtractor.itemCount(); itemIndex++) {
            String text = itemTextExtractor.getTextFromRenderedComponent(itemIndex);
            if (expectedText.equals(text))
                return itemIndex;
        }
        throw new RuntimeException("Couldn't find text '" + expectedText + "'");
    }

    private void hidePopup() {
        comboboxOperator.hidePopup();
        waitToAvoidInstability();
    }
    
    private void waitToAvoidInstability() {
        new EventTool().waitNoEvent(200);
    }
}
