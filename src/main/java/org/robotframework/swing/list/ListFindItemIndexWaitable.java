package org.robotframework.swing.list;

import org.netbeans.jemmy.Waitable;

public class ListFindItemIndexWaitable implements Waitable {

    private String itemIdentifier;
    private CellTextExtractor itemTextExtractor;
    
    public ListFindItemIndexWaitable(CellTextExtractor itemTextExtractor, String itemIdentifier) {
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
}
