package org.robotframework.swing.combobox;

import java.awt.Component;

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
        comboboxOperator.selectItem(findItemIndex(comboItemIdentifier));
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
    
    private int findItemIndex(String comboItemIdentifier) {
        if (isIndex(comboItemIdentifier)) {
            return asIndex(comboItemIdentifier);
        } else {
            return findItemIndexFromRenderedText(comboItemIdentifier);
        }
    }

    public int findItemIndexFromRenderedText(String expectedText) {
        for (int itemIndex = 0; itemIndex < itemTextExtractor.itemCount(); itemIndex++) {
            String text = itemTextExtractor.getTextFromRenderedComponent(itemIndex);
            if (expectedText.equals(text))
                return itemIndex;
        }
        comboboxOperator.hidePopup();
        throw new RuntimeException("Couldn't find text '" + expectedText + "'");
    }
}
