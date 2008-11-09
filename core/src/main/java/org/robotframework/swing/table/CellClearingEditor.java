package org.robotframework.swing.table;

import java.awt.Component;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CellClearingEditor extends DefaultCellEditor {
    public CellClearingEditor() {
        super(new JTextField());
    }
    
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return super.getTableCellEditorComponent(table, null, isSelected, row, column);
    }
}
