package org.robotframework.swing.list;

import org.netbeans.jemmy.Waitable;
import org.netbeans.jemmy.Waiter;
import org.netbeans.jemmy.operators.JListOperator;
import org.robotframework.swing.common.TimeoutCopier;
import org.robotframework.swing.common.TimeoutName;

public class ListFindItemIndexWaitable implements Waitable {

    private String itemIdentifier;
    private CellTextExtractor itemTextExtractor;
    
    private ListFindItemIndexWaitable(CellTextExtractor itemTextExtractor, String itemIdentifier) {
        this.itemTextExtractor = itemTextExtractor;
        this.itemIdentifier = itemIdentifier;
    }
    
    public Object actionProduced(Object obj) {
        int index = findItemIndexWithRenderer(itemIdentifier);
        if (index == -1)
            return null;
        return new Integer(index);
    }

    private int findItemIndexWithRenderer(String expectedText) {
        for (int itemIndex = 0, itemCount = itemTextExtractor.itemCount(); itemIndex < itemCount; itemIndex++) {
            String text = itemTextExtractor.getTextFromRenderedComponent(itemIndex);
            if (expectedText.equals(text))
                return itemIndex;
        }
        throw new RuntimeException("Couldn't find text '" + expectedText + "'");
    }
    
    public String getDescription() {
        return "Couldn't find item: '"+itemIdentifier+"' from the list.";
    }
    
    public static Waiter getWaiter(CellTextExtractor textExtractor, JListOperator listOperator, String itemIdentifier) {
        Waiter waiter = new Waiter(new ListFindItemIndexWaitable(textExtractor, itemIdentifier));
        waiter.setTimeouts(new TimeoutCopier(listOperator, 
                                             TimeoutName.J_LIST_OPERATOR_WAIT_FIND_ITEM_INDEX_TIMEOUT).getTimeouts());
        return waiter;
    }
}
