package org.robotframework.swing.combobox;

import org.netbeans.jemmy.Waitable;
import org.netbeans.jemmy.Waiter;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.common.TimeoutCopier;

public class ComboboxSelectedItemWaitable implements Waitable {

    private ComboBoxOperator comboboxOperator;
    private String itemIdentifier;
    
    private ComboboxSelectedItemWaitable(ComboBoxOperator comboboxOperator, String itemIdentifier) {
        this.comboboxOperator = comboboxOperator;
        this.itemIdentifier = itemIdentifier;
    }
    
    /**@return The selected item or selected index, depending on the itemIdentifier.*/
    public Object actionProduced(Object obj) {
        if (new IdentifierSupport().isIndex(itemIdentifier))
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
                                             "JComboboxOperator.WaitGetSelectedItemTimeout").getTimeouts());
        return waiter;
    }
}
