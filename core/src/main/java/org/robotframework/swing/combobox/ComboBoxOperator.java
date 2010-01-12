package org.robotframework.swing.combobox;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.Waiter;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.operator.ComponentWrapper;

public class ComboBoxOperator extends IdentifierSupport implements ComponentWrapper {
    private final JComboBoxOperator comboboxOperator;
    private ItemTextExtractor itemTextExtractor;
    private boolean verificationEnabled = true;

    public ComboBoxOperator(JComboBoxOperator jComboboxOperator) {
        comboboxOperator = jComboboxOperator;
        itemTextExtractor = new ItemTextExtractor(jComboboxOperator);
    }
    
    ComboBoxOperator(JComboBoxOperator jComboboxOperator, ItemTextExtractor itemTextExtractor) {
        this.comboboxOperator = jComboboxOperator;
        this.itemTextExtractor = itemTextExtractor;
    }

    public void disableVerification() {
        verificationEnabled = false;
        comboboxOperator.setVerification(false);
    }
    
    public Component getSource() {
        return comboboxOperator.getSource();
    }

    public void selectItem(final String comboItemIdentifier) {
        new ComboboxAction() {
            @Override
            protected Object executeWhenComboBoxOpen() {
                int itemIndex = findItemIndex(comboItemIdentifier);
                comboboxOperator.setVerification(false);
                comboboxOperator.selectItem(itemIndex);
                if (verificationEnabled)
                    verifyItemSelection(comboItemIdentifier);
                return null;
            }
        }.execute();
    }

    private void verifyItemSelection(String comboItemIdentifier) {
        try {
            Waiter waiter = ComboboxSelectedItemWaitable.getWaiter(this, comboItemIdentifier);
            Object selectedItem = waiter.waitAction(null);
            if (selectedItem == null)
                throw new RuntimeException("Expected selection to be '"+comboItemIdentifier+"' but no selection found");
            if (! comboItemIdentifier.equals(selectedItem.toString()))
                throw new RuntimeException("Expected selection to be '"+comboItemIdentifier+"' but was '"+selectedItem+"'");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int findItemIndex(String comboItemIdentifier) {
        if (isIndex(comboItemIdentifier))
            return asIndex(comboItemIdentifier);
        return findItemIndexWithRenderer(comboItemIdentifier);
    }

    private int findItemIndexWithRenderer(String expectedText) {
        for (int itemIndex = 0, itemCount = itemTextExtractor.itemCount(); itemIndex < itemCount; itemIndex++) {
            String text = itemTextExtractor.getTextFromRenderedComponent(itemIndex);
            if (expectedText.equals(text))
                return itemIndex;
        }
        throw new RuntimeException("Couldn't find text '" + expectedText + "'");
    }
    
    public Object getSelectedItem() {
        return new ComboboxAction() {
            @Override
            protected Object executeWhenComboBoxOpen() {
                return itemTextExtractor.getTextFromRenderedComponent(comboboxOperator.getSelectedIndex());
            }            
        }.execute();
    }

    public boolean isEnabled() {
        return comboboxOperator.isEnabled();
    }
    
    public void typeText(String text) {
        comboboxOperator.clearText();
        comboboxOperator.typeText(text);
    }

    public String[] getValues() {
        return (String[]) new ComboboxAction() {
            @Override
            protected Object executeWhenComboBoxOpen() {
                List<String> values = new ArrayList<String>();
                for (int i = 0, itemCount = itemTextExtractor.itemCount(); i < itemCount; i++)
                    values.add(itemTextExtractor.getTextFromRenderedComponent(i));
                return values.toArray(new String[0]);
            }            
        }.execute();
    }
    
    private abstract class ComboboxAction {
        public Object execute() {
            try {
                comboboxOperator.pushComboButton();
                return executeWhenComboBoxOpen();
            } finally {
                comboboxOperator.hidePopup();
                waitToAvoidInstability();
            }
        }
        
        private void waitToAvoidInstability() {
            new EventTool().waitNoEvent(200);
        }

        protected abstract Object executeWhenComboBoxOpen();
    }
    
    public JComboBoxOperator getComboboxOperator() {
        return comboboxOperator;
    }
}
