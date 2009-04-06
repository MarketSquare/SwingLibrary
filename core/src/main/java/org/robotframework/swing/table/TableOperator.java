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

import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.swing.operator.ComponentWrapper;

public interface TableOperator extends ComponentWrapper {
    void selectCell(String row, String column);
    void setCellValue(Object newValue, String row, String column);
    void typeIntoCell(Object newValue, String row, String column);
    void clearSelection();
    void clearCell(String row, String columnIdentifier);
    boolean isCellSelected(String row, String column);
    int getColumnCount();
    int getRowCount();
    Object getCellValue(String row, String column);
    Object getSelectedCellValue();
    JPopupMenuOperator callPopupOnCell(String row, String columnIdentifier);
}
