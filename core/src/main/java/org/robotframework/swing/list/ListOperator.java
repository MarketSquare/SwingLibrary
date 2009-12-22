package org.robotframework.swing.list;

import java.awt.Component;
import java.util.List;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.Timeouts;
import org.netbeans.jemmy.Waiter;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JListOperator;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.operator.ComponentWrapper;

public class ListOperator extends IdentifierSupport implements ComponentWrapper {
    private JListOperator jListOperator;

    public ListOperator(ContainerOperator container, ComponentChooser chooser) {
        jListOperator = new JListOperator(container, chooser);
    }

    public ListOperator(ContainerOperator container, int index) {
        jListOperator = new JListOperator(container, index);
    }
    
    public ListOperator(JListOperator jListOperator) {
        this.jListOperator = jListOperator;
    }
    
    public void clickOnItem(String itemIdentifier, int clickCount) {
        jListOperator.clickOnItem(findIndex(itemIdentifier), clickCount);
    }

    public void clearSelection() {
        jListOperator.clearSelection();
    }
    
    public Object getSelectedValue() {
        return jListOperator.getSelectedValue();
    }
    
    public Component getSource() {
        return jListOperator.getSource();
    }

    public int getSize() {
        return jListOperator.getModel().getSize();
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
            return (Integer) createListWaiter(itemIdentifier).waitAction(null);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    
    protected Waiter createListWaiter(String itemIdentifier) {
        Waiter waiter = new Waiter(new ListFindItemIndexWaitable(jListOperator, itemIdentifier));
        Timeouts nextNodeTimeout = copyTimeout("JListOperator.WaitFindItemIndexTimeout");
        waiter.setTimeouts(nextNodeTimeout);
        return waiter;
    }
    
    protected Timeouts copyTimeout(String timeout) {
        Timeouts timeouts = jListOperator.getTimeouts();
        Timeouts times = timeouts.cloneThis();
        times.setTimeout("Waiter.WaitingTime", timeouts.getTimeout(timeout));
        return times;
    }
}
