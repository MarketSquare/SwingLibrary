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

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.table.EnhancedTableOperator;
import org.robotframework.swing.table.TableContextVerifier;
import org.robotframework.swing.table.TableOperatorFactory;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class TableKeywords {
    private OperatorFactory<EnhancedTableOperator> operatorFactory = new TableOperatorFactory();
    private IContextVerifier tableContextVerifier = new TableContextVerifier();

    @RobotKeyword("Selects a table as current context.\n\n"
        + "Example:\n"
        + "| Select Table | _myTable_ |\n")
    public void selectTable(String identifier) {
        Context.setContext(operatorFactory.createOperator(identifier));
    }

    @RobotKeyword("Selects a cell.\n"
        + "Assumes current context is a table.\n\n"
        + "Example:\n"
        + "| Select Table Cell | _0_ | _2_       | # Selects cell from first row and third column |\n"
        + "| Select Table Cell | _1_ | _Keyword_ | # Selects cell from second row and column with header 'Keyword' |\n")
    public void selectTableCell(String row, String cellIdentifier) {
        tableContextVerifier.verifyContext();
        tableOperator().selectCell(row, cellIdentifier);
    }

    @RobotKeyword("Clears selection from table.\n"
        + "Assumes current context is a table\n\n"
        + "Example:\n"
        + "| Clear Table Selection |\n")
    public void clearTableSelection() {
        tableContextVerifier.verifyContext();
        tableOperator().clearSelection();
    }

    @RobotKeyword("Fails if given table cell is not selected.\n"
        + "Assumes current context is a table.\n\n"
        + "Example:\n"
        + "| Table Cell Should Be Selected | _0_ | _2_       |\n"
        + "| Table Cell Should Be Selected | _1_ | _Keyword_ |\n")
    public void tableCellShouldBeSelected(String row, String columnIdentifier) {
        tableContextVerifier.verifyContext();
        Assert.assertTrue("Cell '" + row + "', '" + columnIdentifier + "' is not selected.", tableOperator().isCellSelected(row, columnIdentifier));
    }

    @RobotKeyword("Fails if given table cell is selected.\n"
        + "Assumes current context is a table.\n\n"
        + "Example:\n"
        + "| Table Cell Should Be Selected | _0_ | _2_       |\n"
        + "| Table Cell Should Be Selected | _1_ | _Keyword_ |\n")
    public void tableCellShouldNotBeSelected(String row, String columnIdentifier) {
        tableContextVerifier.verifyContext();
        Assert.assertFalse("Cell '" + row + "', '" + columnIdentifier + "' is selected.", tableOperator().isCellSelected(row, columnIdentifier));
    }

    @RobotKeyword("Returns cell's value.\n"
        + "Assumes current context is a table.\n\n"
        + "Example:\n"
        + "| ${cellValue}=   | Get Table Cell Value   | _0_            | _2_ |\n"
        + "| Should Be Equal | _tuesday_              | _${cellValue}_ |     |\n")
    public String getTableCellValue(String row, String columnIdentifier) {
        tableContextVerifier.verifyContext();
        return tableOperator().getValueAt(row, columnIdentifier).toString();
    }

    @RobotKeyword("Returns selected cell's value.\n"
        + "Assumes current context is a table.\n\n"
        + "Example:\n"
        + "| ${cellValue}=   | Get Selected Table Cell Value   |              |\n"
        + "| Should Be Equal | _tuesday_                       | _${cellValue}_ |\n")
    public Object getSelectedTableCellValue() {
        tableContextVerifier.verifyContext();
        return tableOperator().getValueAt(tableOperator().getSelectedRow(), tableOperator().getSelectedColumn());
    }

    @RobotKeyword("Sets the cell value.\n"
    	+ "Assumes current context is a table.\n\n"
    	+ "Example:\n"
    	+ "| Set Table Cell Value | _1_ | _2_ | _New value_ |\n")
    public void setTableCellValue(String row, String columnIdentifier, String newValue) {
        tableContextVerifier.verifyContext();
        tableOperator().setValueAt(newValue, row, columnIdentifier);
    }

    private EnhancedTableOperator tableOperator() {
        return (EnhancedTableOperator) Context.getContext();
    }
}
