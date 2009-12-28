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
import java.util.Map;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.laughingpanda.jretrofit.AllMethodsNotImplementedException;
import org.laughingpanda.jretrofit.Retrofit;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTableOperator.TableCellChooser;
import org.robotframework.swing.chooser.WithText;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.common.SmoothInvoker;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.operator.ComponentWrapper;
import org.robotframework.swing.util.PropertyExtractor;

public class TableOperator extends IdentifierSupport implements ComponentWrapper {
    private final JTableOperator jTableOperator;
    private PropertyExtractor propertyExtractor = new PropertyExtractor();

    public TableOperator(JTableOperator jTableOperator) {
        this.jTableOperator = jTableOperator;
        this.jTableOperator.setComparator(new EqualsStringComparator());
    }
    
    public Object getCellValue(String row, String columnIdentifier) {
        Point cell = findCell(row, columnIdentifier);
        return getCellValueFromRenderer(cell.y, cell.x);
    }

    private Object getCellValueFromRenderer(int row, int column) {
        try {
            Component cellRendererComponent = getCellRendererComponent(row, column);
            return coerceToWithText(cellRendererComponent).getText();
        } catch (AllMethodsNotImplementedException e) {
            return wrapElementToWithText(row, column).getText();
        }
    }

    private Component getCellRendererComponent(int row, int column) {
        TableCellRenderer renderer = jTableOperator.getCellRenderer(row, column);
        JTable table = (JTable) jTableOperator.getSource();
        Object value = jTableOperator.getValueAt(row, column);
        boolean isSelected = jTableOperator.isCellSelected(row, column);
        boolean hasFocus = jTableOperator.hasFocus();
        return getTableCellRendererComponentSmoothly(row, column, renderer, table, value, isSelected, hasFocus);
    }
    
    private WithText coerceToWithText(Object element) {
        return (WithText) Retrofit.complete(element, WithText.class);
    }

    private WithText wrapElementToWithText(final int rowIndex, final int columnIndex) {
        return new WithText() {
            public String getText() {
                return jTableOperator.getModel()
                .getValueAt(rowIndex, columnIndex)
                .toString();
            }
        };
    }
    
    public boolean isCellSelected(String row, String columnIdentifier) {
        Point coordinates = findCell(row, columnIdentifier);
        return jTableOperator.isCellSelected(coordinates.y, coordinates.x);
    }

    public void selectCell(String row, String columnIdentifier) {
        Point coordinates = findCell(row, columnIdentifier);
        jTableOperator.selectCell(coordinates.y, coordinates.x);
    }

    public void addTableCellSelection(String row, String columnIdentifier) {
        Point coordinates = findCell(row, columnIdentifier);
        selectCellArea(coordinates.y, coordinates.y, coordinates.x, coordinates.x);
    }

    private void selectCellArea(int startRow, int endRow, int startColumn, int endColumn) {
        jTableOperator.setRowSelectionAllowed(true);
        jTableOperator.addRowSelectionInterval(startRow, endRow);
        jTableOperator.setColumnSelectionAllowed(true);
        jTableOperator.addColumnSelectionInterval(startColumn, endColumn);
    }
    
    public void selectCellArea(String startRow, String endRow, String startColumn, String endColumn) {
        selectCellArea(Integer.valueOf(startColumn), Integer.valueOf(endColumn), Integer.valueOf(startRow), Integer.valueOf(endRow));
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
    
    public int findCellRow(String text, String columnIdentifier) {
        int col = jTableOperator.findColumn(columnIdentifier);
        if (col == -1)
            throw new RuntimeException("Column '"+columnIdentifier+" not found.");
        return jTableOperator.findCellRow(text, col, 0);
    }
    
    public Object getSelectedCellValue() {
      int selectedRow = jTableOperator.getSelectedRow();
      int selectedColumn = jTableOperator.getSelectedColumn();
      return getCellValueFromRenderer(selectedRow, selectedColumn);
    }
    
    public void callPopupMenuItemOnSelectedCells(String menuPath) {
        int selectedRow = jTableOperator.getSelectedRow();
        int selectedColumn = jTableOperator.getSelectedColumn();
        JPopupMenuOperator menuOperator = new JPopupMenuOperator(jTableOperator.callPopupOnCell(selectedRow, selectedColumn));
        JMenuItemOperator item = menuOperator.showMenuItem(menuPath, new EqualsStringComparator());
        item.push();
    }
    
    public JPopupMenuOperator callPopupOnCell(String row, String columnIdentifier) {
        Point coordinates = findCell(row, columnIdentifier);
        return new JPopupMenuOperator(jTableOperator.callPopupOnCell(coordinates.y, coordinates.x));
    }
    
    public Component getSource() {
        return jTableOperator.getSource();
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
    
    public Object[] getColumnValues(String columnIdentifier) {
        Object[] columnValues = new Object[getRowCount()];
        for (int row = 0; row < columnValues.length; ++row) {
            Point coordinates = findCell(row, columnIdentifier);
            columnValues[row] = getValueAt(coordinates);
        }
        return columnValues;
    }
    
    private Object getValueAt(Point coordinates) {
        return getCellValueFromRenderer(coordinates.y, coordinates.x);
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

    public Map<String, Object> getCellProperties(String row, String columnIdentifier) {
        Component cellComponent = getCellRendererComponent(row, columnIdentifier);
        return propertyExtractor.extractProperties(cellComponent);
    }

    Component getCellRendererComponent(String row, String columnIdentifier) {
        Point cell = findCell(row, columnIdentifier);
        return getCellRendererComponent(cell.y, cell.x);
    }

    private Component getTableCellRendererComponentSmoothly(final int row, final int column, final TableCellRenderer renderer,
            final JTable table, final Object value, final boolean isSelected, final boolean hasFocus) {
        return new SmoothInvoker<Component>() {
            public Object work() {
                return renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        }.invoke();
    }
}
