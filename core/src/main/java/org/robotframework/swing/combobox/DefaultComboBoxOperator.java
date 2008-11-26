package org.robotframework.swing.combobox;

import java.awt.Component;

import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.comparator.EqualsStringComparator;

/**
 * @author Heikki Hulkko
 */
public class DefaultComboBoxOperator extends IdentifierSupport implements ComboBoxOperator {
    private final JComboBoxOperator comboboxOperator;

    public DefaultComboBoxOperator(JComboBoxOperator jComboboxOperator) {
        comboboxOperator = jComboboxOperator;
    }

    public Component getSource() {
        return comboboxOperator.getSource();
    }

    public void selectItem(String comboItemIdentifier) {
        comboboxOperator.pushComboButton();
        if (isIndex(comboItemIdentifier)) {
            comboboxOperator.selectItem(asIndex(comboItemIdentifier));
        } else {
            comboboxOperator.selectItem(comboItemIdentifier, new EqualsStringComparator());
        }
    }

    public Object getSelectedItem() {
        return comboboxOperator.getSelectedItem();
    }
}
