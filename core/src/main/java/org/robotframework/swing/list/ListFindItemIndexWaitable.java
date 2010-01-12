package org.robotframework.swing.list;

import org.netbeans.jemmy.Waitable;
import org.netbeans.jemmy.Waiter;
import org.netbeans.jemmy.operators.JListOperator;
import org.robotframework.swing.chooser.ListItemChooser;
import org.robotframework.swing.common.TimeoutCopier;

public class ListFindItemIndexWaitable implements Waitable {

    private JListOperator listOperator;
    private String itemIdentifier;
    
    private ListFindItemIndexWaitable(JListOperator listOperator, String itemIdentifier) {
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
    
    public static Waiter getWaiter(JListOperator listOperator, String itemIdentifier) {
        Waiter waiter = new Waiter(new ListFindItemIndexWaitable(listOperator, itemIdentifier));
        waiter.setTimeouts(new TimeoutCopier(listOperator, "JListOperator.WaitFindItemIndexTimeout").getTimeouts());
        return waiter;
    }
}
