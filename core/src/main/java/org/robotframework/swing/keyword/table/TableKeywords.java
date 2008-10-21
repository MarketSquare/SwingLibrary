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

package org.robotframework.swing.keyword.table;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.DefaultContextVerifier;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.table.EnhancedTableOperator;
import org.robotframework.swing.table.TableOperatorFactory;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class TableKeywords extends IdentifierSupport {
    private OperatorFactory<EnhancedTableOperator> operatorFactory = new TableOperatorFactory();
    private IContextVerifier contextVerifier = new DefaultContextVerifier();

    @RobotKeyword("Selects a cell.\n"
        + "Assumes current context is a table.\n\n"
        + "Example:\n"
        + "| Select Table Cell | _0_ | _2_       | # Selects cell from first row and third column |\n"
        + "| Select Table Cell | _1_ | _Keyword_ | # Selects cell from second row and column with header 'Keyword' |\n")
    public void selectTableCell(String row, String cellIdentifier) {
        contextVerifier.verifyContext();
        tableOperator().selectCell(row, cellIdentifier);
    }

    @RobotKeyword("Clears selection from table.\n"
        + "Assumes current context is a table\n\n"
        + "Example:\n"
        + "| Clear Table Selection |\n")
    public void clearTableSelection() {
        contextVerifier.verifyContext();
        tableOperator().clearSelection();
    }

    @RobotKeyword("Fails if given table cell is not selected.\n"
        + "Assumes current context is a table.\n\n"
        + "Example:\n"
        + "| Table Cell Should Be Selected | _0_ | _2_       |\n"
        + "| Table Cell Should Be Selected | _1_ | _Keyword_ |\n")
    public void tableCellShouldBeSelected(String row, String columnIdentifier) {
        contextVerifier.verifyContext();
        Assert.assertTrue("Cell '" + row + "', '" + columnIdentifier + "' is not selected.", tableOperator().isCellSelected(row, columnIdentifier));
    }

    @RobotKeyword("Fails if given table cell is selected.\n"
        + "Assumes current context is a table.\n\n"
        + "Example:\n"
        + "| Table Cell Should Be Selected | _0_ | _2_       |\n"
        + "| Table Cell Should Be Selected | _1_ | _Keyword_ |\n")
    public void tableCellShouldNotBeSelected(String row, String columnIdentifier) {
        contextVerifier.verifyContext();
        Assert.assertFalse("Cell '" + row + "', '" + columnIdentifier + "' is selected.", tableOperator().isCellSelected(row, columnIdentifier));
    }

    @RobotKeyword("Returns cell's value.\n"
        + "Assumes current context is a table.\n\n"
        + "Example:\n"
        + "| ${cellValue}=   | Get Table Cell Value   | _0_            | _2_ |\n"
        + "| Should Be Equal | _tuesday_              | _${cellValue}_ |     |\n")
    public String getTableCellValue(String row, String columnIdentifier) {
        contextVerifier.verifyContext();
        return tableOperator().getValueAt(row, columnIdentifier).toString();
    }

    @RobotKeyword("Returns selected cell's value.\n"
        + "Assumes current context is a table.\n\n"
        + "Example:\n"
        + "| ${cellValue}=   | Get Selected Table Cell Value   |              |\n"
        + "| Should Be Equal | _tuesday_                       | _${cellValue}_ |\n")
    public Object getSelectedTableCellValue(String identifier) {
        EnhancedTableOperator tableOperator = createTableOperator(identifier);
        int selectedRow = tableOperator.getSelectedRow();
        int selectedColumn = tableOperator.getSelectedColumn();
        return tableOperator.getValueAt(selectedRow, selectedColumn).toString();
    }

    @RobotKeyword("Sets cell value in a table.\n\n"
    	+ "Example:\n"
    	+ "| Set Table Cell Value | _1_ | _2_ | _New value_ |\n")
    @ArgumentNames({"identifier", "row", "columnIdentifier", "newValue"})
    public void setTableCellValue(String identifier, String row, String columnIdentifier, String newValue) {
        EnhancedTableOperator tableOperator = createTableOperator(identifier);
        tableOperator.setValueAt(newValue, row, columnIdentifier);
    }

    @RobotKeyword("Returns the number of columns from a table.\n\n"
        + "Example:\n"
        + "| ${columnCount}= | Get Table Column Count | _myTable_ |\n"
        + "| Should Be Equal As Integers | _4_ | _${columnCount}_ |\n")
    public int getTableColumnCount(String identifier) {
        return createTableOperator(identifier).getColumnCount();
    }

    @RobotKeyword("Returns the number of rows from a table.\n\n"
        + "Example:\n"
        + "| ${rowCount}= | Get Table Row Count | _myTable_ |\n"
        + "| Should Be Equal As Integers | _4_ | _${rowCount}_ |\n")
    public int getTableRowCount(String identifier) {
        return createTableOperator(identifier).getRowCount();
    }
    
    private EnhancedTableOperator createTableOperator(String identifier) {
        contextVerifier.verifyContext();
        return operatorFactory.createOperator(identifier);   
    }
    
    private EnhancedTableOperator tableOperator() {
        return (EnhancedTableOperator) Context.getContext();
    }
}
