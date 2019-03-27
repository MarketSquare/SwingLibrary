package org.robotframework.swing.list;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.Waiter;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JListOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.common.TimeoutCopier;
import org.robotframework.swing.common.TimeoutName;
import org.robotframework.swing.operator.ComponentWrapper;

public class ListOperator extends IdentifierSupport implements ComponentWrapper {
    private JListOperator jListOperator;
    private CellTextExtractor itemTextExtractor;

    public ListOperator(ContainerOperator container, ComponentChooser chooser) {
        this(new JListOperator(container, chooser));
    }

    public ListOperator(ContainerOperator container, int index) {
        this(new JListOperator(container, index));
    }
    
    public ListOperator(JListOperator jListOperator) {
        this(jListOperator, new CellTextExtractor(jListOperator)); 
    }
    
    public ListOperator(JListOperator jListOperator, CellTextExtractor itemTextExtractor) {
        this.jListOperator = jListOperator;
        this.itemTextExtractor = itemTextExtractor; 
    }
    
    public void clickOnItem(String itemIdentifier, int clickCount) {
        jListOperator.clickOnItem(findIndex(itemIdentifier), clickCount);
    }

    public void clearSelection() {
        jListOperator.clearSelection();
    }
    
    public Object getSelectedValue() {
        int selectedIndex = jListOperator.getSelectedIndex();
        return itemTextExtractor.getTextFromRenderedComponent(selectedIndex);
    }
    
    public Component getSource() {
        return jListOperator.getSource();
    }

    public int getSize() {
        return jListOperator.getModel()
                            .getSize();
    }
    
    public JPopupMenuOperator callPopupOnListItem(String itemIdentifier) {
        Point coordinates = jListOperator.indexToLocation(findIndex(itemIdentifier));
    	return new JPopupMenuOperator(JPopupMenuOperator.callPopup(getSource(), 
			    (int)coordinates.getX(), 
			    (int)coordinates.getY(), 
			    InputEvent.BUTTON3_MASK));
    }

    public void selectAll() {
        jListOperator.setSelectionInterval(0, getSize() - 1);
    }

    public void selectItems(List<String> itemIdentifiers) {
        int[] indices = findIndices(itemIdentifiers);
        jListOperator.selectItems(indices);
    }

    protected int[] findIndices(List<String> itemIdentifiers) {
        int[] indices = new int[itemIdentifiers.size()];
        for (int i = 0; i < indices.length; i++)
            indices[i] = findIndex(itemIdentifiers.get(i));
        return indices;
    }
    
    protected int findIndex(String itemIdentifier) {
        if (isIndex(itemIdentifier))
            return asIndex(itemIdentifier);
        return findIndexWithWait(itemIdentifier);
    }
        
    private int findIndexWithWait(String itemIdentifier) {
        try {
            Waiter waiter = listIndexWaiterFor(itemTextExtractor, 
                                               jListOperator, 
                                               itemIdentifier);
            return (Integer) waiter.waitAction(null);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }    
    
    private Waiter listIndexWaiterFor(CellTextExtractor textExtractor, JListOperator listOperator, String itemIdentifier) {
        Waiter waiter = new Waiter(new ListFindItemIndexWaitable(textExtractor, itemIdentifier));
        waiter.setTimeouts(new TimeoutCopier(listOperator, 
                                             TimeoutName.J_LIST_OPERATOR_WAIT_FIND_ITEM_INDEX_TIMEOUT).getTimeouts());
        return waiter;
    }

	public List<String> getListValues() {
		ListModel model = jListOperator.getModel();
		List<String> items = new ArrayList<String>();
		for (int i = 0, itemCount = model.getSize(); i < itemCount; i++)
			items.add(String.valueOf(model.getElementAt(i)));
		return items;
	}
}
