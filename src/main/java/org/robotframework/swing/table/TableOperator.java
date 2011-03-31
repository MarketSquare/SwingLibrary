/*
 * Copyright 2008-2011 Nokia Siemens Networks Oyj
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
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.swing.table.TableColumn;

import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTableOperator.TableCellChooser;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.operator.ComponentWrapper;
import org.robotframework.swing.util.PropertyExtractor;

public class TableOperator extends IdentifierSupport implements ComponentWrapper {
    private final JTableOperator jTableOperator;
    private PropertyExtractor propertyExtractor = new PropertyExtractor();
    private CellValueExtractor cellValueExtractor;

    public TableOperator(JTableOperator jTableOperator) {
        this.jTableOperator = jTableOperator;
        this.jTableOperator.setComparator(new EqualsStringComparator());
        cellValueExtractor = new CellValueExtractor(jTableOperator);
    }

    public TableHeaderOperator headerOperator() {
        return new TableHeaderOperator(jTableOperator.getHeaderOperator());
    }

    public String getCellValue(String row, String columnIdentifier) {
        Point cell = findCell(row, columnIdentifier);
        return cellValueExtractor.textOf(cell.y, cell.x);
    }

    public boolean isCellEditable(String row, String columnIdentifier) {
        Point coordinates = findCell(row, columnIdentifier);
        return jTableOperator.isCellEditable(coordinates.y, coordinates.x);
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
            throw new RuntimeException("Column '" + columnIdentifier + " not found.");
        return jTableOperator.findCellRow(text, col, 0);
    }

    public Object getSelectedCellValue() {
        int selectedRow = jTableOperator.getSelectedRow();
        int selectedColumn = jTableOperator.getSelectedColumn();
        return cellValueExtractor.textOf(selectedRow, selectedColumn);
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
        return cellValueExtractor.textOf(coordinates.y, coordinates.x);
    }

    protected Point findCell(String row, String columnIdentifier) {
        return findCell(asIndex(row), columnIdentifier);
    }

    protected Point findCell(int row, String columnIdentifier) {
        TableCellChooser cellChooser = createCellChooser(row, columnIdentifier);
        Point cell = jTableOperator.findCell(cellChooser);
        if (isInvalid(cell))
            throw new InvalidCellException(row, columnIdentifier);
        return cell;
    }

    protected boolean isInvalid(Point cell) {
        return cell.x < 0 || cell.y < 0;
    }

    protected TableCellChooser createCellChooser(int row, String columnIdentifier) {
        if (isIndex(columnIdentifier)) {
            return new ColumnIndexTableCellChooser(row, columnIdentifier);
        }
        return new ColumnNameTableCellChooser(row, columnIdentifier);
    }

    public Map<String, Object> getCellProperties(String row, String columnIdentifier) {
        Point cell = findCell(row, columnIdentifier);
        Component cellComponent = cellValueExtractor.getCellRendererComponent(cell.y, cell.x);
        return propertyExtractor.extractProperties(cellComponent);
    }

    public void clickOnCell(String rowNumber, String columnIdentifier, String clickCount, String button, String[] keyModifiers) {
        Point cell = findCell(rowNumber, columnIdentifier);
        jTableOperator.clickOnCell(cell.y,
                cell.x,
                Integer.parseInt(clickCount),
                toInputEventMask(button),
                toCombinedInputEventMasks(keyModifiers));
    }

    private int toInputEventMask(String inputEventFieldName) {
        try {
            return InputEvent.class.getField(inputEventFieldName).getInt(null);
        } catch (NoSuchFieldException e) {
            throw new IllegalArgumentException("No field name '" + inputEventFieldName + "' in class " +
                    InputEvent.class.getName() + ".");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int toCombinedInputEventMasks(String[] modifierStrings) {
        int combinedInputEventMask = 0;
        if (modifierStrings.length > 0)
            for (String modifierAsString : modifierStrings)
                combinedInputEventMask |= toInputEventMask(modifierAsString);
        return combinedInputEventMask;
    }
}
