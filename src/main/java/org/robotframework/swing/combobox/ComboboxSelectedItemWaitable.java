package org.robotframework.swing.combobox;

import org.netbeans.jemmy.Waitable;
import org.robotframework.swing.common.Identifier;

public class ComboboxSelectedItemWaitable implements Waitable {

    private ComboBoxOperator comboboxOperator;
    private Identifier itemIdentifier;
    
    public ComboboxSelectedItemWaitable(ComboBoxOperator comboboxOperator, String itemIdentifier) {
        this.comboboxOperator = comboboxOperator;
        this.itemIdentifier = new Identifier(itemIdentifier);
    }
    
    /**@return The selected item or selected index, depending on the itemIdentifier.*/
    public Object actionProduced(Object obj) {
        if (itemIdentifier.isIndex())
            return new Integer(comboboxOperator.getComboboxOperator()
                                               .getSelectedIndex());
        return comboboxOperator.getSelectedItem();
    }

    public String getDescription() {
        return "Couldn't get the selected item from the combobox.";
    }
}
