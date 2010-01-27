package org.robotframework.swing.combobox;

import org.netbeans.jemmy.Waitable;
import org.netbeans.jemmy.Waiter;
import org.robotframework.swing.common.Identifier;
import org.robotframework.swing.common.TimeoutCopier;
import org.robotframework.swing.common.TimeoutName;

public class ComboboxSelectedItemWaitable implements Waitable {

    private ComboBoxOperator comboboxOperator;
    private Identifier itemIdentifier;
    
    private ComboboxSelectedItemWaitable(ComboBoxOperator comboboxOperator, String itemIdentifier) {
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
    
    public static Waiter getWaiter(ComboBoxOperator comboboxOperator, String itemIdentifier) {
        Waiter waiter = new Waiter(new ComboboxSelectedItemWaitable(comboboxOperator, itemIdentifier));
        waiter.setTimeouts(new TimeoutCopier(comboboxOperator.getComboboxOperator(), 
                                             TimeoutName.J_COMBOBOX_OPERATOR_WAIT_GET_SELECTED_ITEM_TIMEOUT).getTimeouts());
        return waiter;
    }
}
