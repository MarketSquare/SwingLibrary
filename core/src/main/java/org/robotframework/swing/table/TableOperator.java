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

import org.robotframework.swing.operator.IOperator;

public interface TableOperator extends IOperator {
    void selectCell(String row, String column);
    void setValueAt(Object newValue, String row, String column);
    void changeCellObject(String row, String column, String newValue);
    void clearSelection();
    boolean isCellSelected(String row, String column);
    int getSelectedRow();
    int getSelectedColumn();
    int getColumnCount();
    int getRowCount();
    Object getValueAt(int row, int column);
    Object getValueAt(String row, String column);
    Point findCell(String row, String column);
}
