/*
 * Copyright 2008 Nokia Siemens Networks Oyj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.robotframework.swing.table;

import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.table.TableColumn;

import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTableOperator.TableCellChooser;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.operator.ComponentWrapper;

public class TableOperator extends IdentifierSupport implements ComponentWrapper {
    private final JTableOperator jTableOperator;

    public TableOperator(JTableOperator jTableOperator) {
        this.jTableOperator = jTableOperator;
        this.jTableOperator.setComparator(new EqualsStringComparator());
    }
    
    public Object getCellValue(String row, String columnIdentifier) {
        Point coordinates = findCell(row, columnIdentifier);
        return getValueAt(coordinates);
    }

    public boolean isCellSelected(String row, String columnIdentifier) {
        Point coordinates = findCell(row, columnIdentifier);
        return jTableOperator.isCellSelected(coordinates.y, coordinates.x);
    }

    public void selectCell(String row, String columnIdentifier) {
        Point coordinates = findCell(row, columnIdentifier);
        jTableOperator.selectCell(coordinates.y, coordinates.x);
    }

    public void setCellValue(Object newValue, String row, String columnIdentifier) {
        Point coordinates = findCell(row, columnIdentifier);
        jTableOperator.setValueAt(newValue, coordinates.y, coordinates.x);
    }
    
    public void typeIntoCell(Object newValue, String row, String columnIdentifier) {
        Point coordinates = findCell(row, columnIdentifier);
        jTableOperator.changeCellObject(coordinates.y, coordinates.x, newValue);
    }
    
    public void clearCell(String row, String columnIdentifier) {
        Point coordinates = findCell(row, columnIdentifier);
        jTableOperator.changeCellObject(coordinates.y, coordinates.x, "");
    }
    
    public void clearSelection() {
        jTableOperator.clearSelection();
    }

    public int getColumnCount() {
        return jTableOperator.getColumnCount();
    }

    public int getRowCount() {
        return jTableOperator.getRowCount();
    }

    public int findCellRow(String text) {
        return jTableOperator.findCellRow(text);
    }
    
    public Object getSelectedCellValue() {
      int selectedRow = jTableOperator.getSelectedRow();
      int selectedColumn = jTableOperator.getSelectedColumn();
      return jTableOperator.getValueAt(selectedRow, selectedColumn);
    }

    public JPopupMenuOperator callPopupOnCell(String row, String columnIdentifier) {
        Point coordinates = findCell(row, columnIdentifier);
        return new JPopupMenuOperator(jTableOperator.callPopupOnCell(coordinates.y, coordinates.x));
    }
    
    public Component getSource() {
        return jTableOperator.getSource();
    }
    
    protected Point findCell(String row, String columnIdentifier) {
        return findCell(asIndex(row), columnIdentifier);
    }
    
    protected Point findCell(int row, String columnIdentifier) {
        TableCellChooser cellChooser = createCellChooser(row, columnIdentifier);
        Point cell = jTableOperator.findCell(cellChooser);
        if (cellIsInvalid(cell))
            throw new InvalidCellException(row, columnIdentifier);
        return cell;
    }
    
    protected boolean cellIsInvalid(Point cell) {
        return cell.x < 0 || cell.y < 0;
    }

    protected TableCellChooser createCellChooser(int row, String columnIdentifier) {
    	if (isIndex(columnIdentifier)) {
    		return new ColumnIndexTableCellChooser(row, columnIdentifier);
    	} 
    	return new ColumnNameTableCellChooser(row, columnIdentifier);
    }
    
    private Object getValueAt(Point coordinates) {
        return jTableOperator.getValueAt(coordinates.y, coordinates.x);
    }

    public String[] getTableHeaders() {
        Enumeration<TableColumn> columns = jTableOperator.getTableHeader().getColumnModel().getColumns();
        List<String> results = new ArrayList<String>();
        while (columns.hasMoreElements()) {
            TableColumn tableColumn = (TableColumn) columns.nextElement();
            results.add(tableColumn.getHeaderValue().toString());
        }
        return results.toArray(new String[0]);
    }
    
    public Object[] getTableColumnValues(String columnIdentifier) {
        Object[] columnValues = new Object[getRowCount()];
        for (int row = 0; row < columnValues.length; ++row) {
            Point coordinates = findCell(row, columnIdentifier);
            columnValues[row] = getValueAt(coordinates);
        }
        return columnValues;
    }
}
