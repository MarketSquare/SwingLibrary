package org.robotframework.swing.list;

import org.netbeans.jemmy.Waitable;
import org.netbeans.jemmy.operators.JListOperator;
import org.robotframework.swing.chooser.ListItemChooser;

public class ListFindItemIndexWaitable implements Waitable {

    private JListOperator listOperator;
    private String itemIdentifier;
    
    public ListFindItemIndexWaitable(JListOperator listOperator, String itemIdentifier) {
        this.listOperator = listOperator;
        this.itemIdentifier = itemIdentifier;
    }
    
    public Object actionProduced(Object obj) {
        int index = listOperator.findItemIndex(new ListItemChooser(itemIdentifier));
        if (index == -1)
            return null;
        return new Integer(index);
    }

    public String getDescription() {
        return "Couldn't find item: '"+itemIdentifier+"' from the list.";
    }

}
