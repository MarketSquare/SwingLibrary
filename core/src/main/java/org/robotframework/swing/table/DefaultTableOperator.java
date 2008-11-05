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

import java.awt.Point;

import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.operators.JTableOperator.TableCellChooser;
import org.robotframework.swing.arguments.IdentifierHandler;
import org.springframework.util.ObjectUtils;

/**
 * @author Heikki Hulkko
 */
public class DefaultTableOperator implements TableOperator {
    private final JTableOperator delegate;

    public DefaultTableOperator(JTableOperator delegate) {
        this.delegate = delegate;
    }
    
    public Object getValueAt(String rowIdentifier, String columnIdentifier) {
        Point coordinates = findCell(rowIdentifier, columnIdentifier);
        return delegate.getValueAt(coordinates.y, coordinates.x);
    }

    public boolean isCellSelected(String rowIdentifier, String columnIdentifier) {
        Point coordinates = findCell(rowIdentifier, columnIdentifier);
        return delegate.isCellSelected(coordinates.y, coordinates.x);
    }

    public void selectCell(String rowIdentifier, String columnIdentifier) {
        Point coordinates = findCell(rowIdentifier, columnIdentifier);
        delegate.selectCell(coordinates.y, coordinates.x);
    }

    public void setValueAt(Object newValue, String rowIdentifier, String columnIdentifier) {
        Point coordinates = findCell(rowIdentifier, columnIdentifier);
        delegate.setValueAt(newValue, coordinates.y, coordinates.x);
    }
    
    public void changeCellObject(String row, String columnIdentifier, String newValue) {
        Point coordinates = findCell(row, columnIdentifier);
        delegate.changeCellObject(coordinates.y, coordinates.x, newValue);
    }

    public Point findCell(String row, String columnIdentifier) {
        TableCellChooser cellChooser = createCellChooser(row, columnIdentifier);
        Point cell = delegate.findCell(cellChooser);
        if (cellIsInvalid(cell))
            throw new InvalidCellException(row, columnIdentifier);
        return cell;
    }
    
    public void clearSelection() {
        delegate.clearSelection();
    }

    public int getColumnCount() {
        return delegate.getColumnCount();
    }

    public int getRowCount() {
        return delegate.getRowCount();
    }

    public int getSelectedColumn() {
        return delegate.getSelectedColumn();
    }

    public int getSelectedRow() {
        return delegate.getSelectedRow();
    }

    public Object getValueAt(int row, int column) {
        return delegate.getValueAt(row, column);
    }

    public Object getSource() {
        return delegate.getSource();
    }

    private boolean cellIsInvalid(Point cell) {
        return cell.x < 0 || cell.y < 0;
    }

    private TableCellChooser createCellChooser(String row, String columnIdentifier) {
        return new CellChooserFactory(row).createCellChooser(columnIdentifier);
    }

    private Object getColumHeader(int columnIndex) {
        return delegate.getColumnModel().getColumn(columnIndex).getHeaderValue();
    }

    private class CellChooserFactory extends IdentifierHandler<TableCellChooser> {
        private int row;

        public CellChooserFactory(String rowAsString) {
            row = Integer.parseInt(rowAsString);
        }

        public TableCellChooser indexArgument(final int column) {
            return new AbstractTableCellChooser(row) {
                protected boolean checkColumn(int index) {
                    return column == index;
                }
            };
        }

        public TableCellChooser nameArgument(final String columnHeader) {
            return new AbstractTableCellChooser(row) {
                protected boolean checkColumn(int columnIndex) {
                    return ObjectUtils.nullSafeEquals(columnHeader, getColumHeader(columnIndex));
                }
            };
        }

        public TableCellChooser createCellChooser(String columnIdentifier) {
            return parseArgument(columnIdentifier);
        }
    }
}
