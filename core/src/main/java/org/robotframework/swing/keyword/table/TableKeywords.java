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

import java.util.Map;

import junit.framework.Assert;

import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.table.TableOperator;
import org.robotframework.swing.table.TableOperatorFactory;

@RobotKeywords
public class TableKeywords extends IdentifierSupport {
    private OperatorFactory<TableOperator> operatorFactory = new TableOperatorFactory();

    @RobotKeyword("Selects a cell in a table.\n\n"
        + "Example:\n"
        + "| Select Table Cell | _myTable_ | _0_ | _2_       | # Selects cell from first row and third column |\n"
        + "| Select Table Cell | _myTable_ | _1_ | _Keyword_ | # Selects cell from second row and column with header 'Keyword' |\n")
    public void selectTableCell(String identifier, String row, String cellIdentifier) {
        createTableOperator(identifier).selectCell(row, cellIdentifier);
    }

    @RobotKeyword("Selects a cell in a table and adds it to the selection.\n"
        + "Does not clear earlier selections.\n\n"
        + "Example:\n"
        + "| Select Table Cell | _myTable_ | _0_ | _2_       | # Selects cell from first row and third column |\n"
        + "| Select Table Cell | _myTable_ | _1_ | _Keyword_ | # Selects cell from second row and column with header 'Keyword' |\n")
    public void addTableCellSelection(String identifier, String row, String cellIdentifier) {
        createTableOperator(identifier).addTableCellSelection(row, cellIdentifier);
    }
    
    @RobotKeyword("Selects a cell area in a table.\n"
        + "Does not clear earlier selections.\n\n"
        + "Example:\n"
        + "| Select Table Cell Area | _myTable_ | _0_ | _2_ | _0_ | _2_ | # Selects cells from first to third row and first to third column |\n")
    public void selectTableCellArea(String identifier, String startRow, String endRow, String startColumn, String endColumn) {
        createTableOperator(identifier).selectCellArea(startRow, endRow, startColumn, endColumn);
    }

    @RobotKeyword("Clears selection from a table.\n\n"
        + "Example:\n"
        + "| Clear Table Selection | _myTable_ |\n")
    public void clearTableSelection(String identifier) {
        createTableOperator(identifier).clearSelection();
    }

    @RobotKeyword("Fails if given table cell is not selected in a table.\n\n"
        + "Example:\n"
        + "| Table Cell Should Be Selected | _myTable_ | _0_ | _2_       |\n"
        + "| Table Cell Should Be Selected | _myTable_ | _1_ | _Keyword_ |\n")
    public void tableCellShouldBeSelected(String identifier, String row, String columnIdentifier) {
        TableOperator tableOperator = createTableOperator(identifier);
        Assert.assertTrue("Cell '" + row + "', '" + columnIdentifier + "' is not selected.", tableOperator.isCellSelected(row, columnIdentifier));
    }

    @RobotKeyword("Fails if given table cell is selected in a table.\n\n"
        + "Example:\n"
        + "| Table Cell Should Be Selected | _myTable_ | _0_ | _2_       |\n"
        + "| Table Cell Should Be Selected | _myTable_ | _1_ | _Keyword_ |\n")
    public void tableCellShouldNotBeSelected(String identifier, String row, String columnIdentifier) {
        TableOperator tableOperator = createTableOperator(identifier);
        Assert.assertFalse("Cell '" + row + "', '" + columnIdentifier + "' is selected.", tableOperator.isCellSelected(row, columnIdentifier));
    }

    @RobotKeyword("Returns cell's value from a table.\n\n"
        + "Example:\n"
        + "| ${cellValue}=   | Get Table Cell Value | _myTable_ | _0_            | _2_ |\n"
        + "| Should Be Equal | _tuesday_            |           | _${cellValue}_ |     |\n")
    public String getTableCellValue(String identifier, String row, String columnIdentifier) {
        return createTableOperator(identifier).getCellValue(row, columnIdentifier).toString();
    }

    @RobotKeyword("Returns selected cell's value from a table.\n\n"
        + "Example:\n"
        + "| ${cellValue}=   | Get Selected Table Cell Value   | _myTable_      |\n"
        + "| Should Be Equal | _tuesday_                       | _${cellValue}_ |\n")
    public Object getSelectedTableCellValue(String identifier) {
        return createTableOperator(identifier).getSelectedCellValue().toString();
    }

    @RobotKeyword("Sets cell value in a table.\n\n"
    	+ "Example:\n"
    	+ "| Set Table Cell Value | _1_ | _2_ | _New value_ |\n")
    public void setTableCellValue(String identifier, String row, String columnIdentifier, String newValue) {
        TableOperator tableOperator = createTableOperator(identifier);
        tableOperator.setCellValue(newValue, row, columnIdentifier);
    }
    
    @RobotKeyword("Types a string into a table cell.\n"
    	+ "Uses real keyboard events, this is useful when processing need to be triggered by input events.\n\n"
    	+ "Example:\n"
        + "| Type Into Table Cell | _1_ | _2_ | _New value_ |\n")
    public void typeIntoTableCell(String identifier, String row, String columnIdentifier, String newValue) {
        TableOperator tableOperator = createTableOperator(identifier);
        tableOperator.typeIntoCell(newValue, row, columnIdentifier);
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
    
    @RobotKeyword("Clears table cell contents.\n\n"
        + "Example:\n"
        + "| Clear Table Cell Value | _myTable_ | _1_ | _2_ |\n")
    public void clearTableCell(String identifier, String row, String columnIdentifier) {
        createTableOperator(identifier).clearCell(row, columnIdentifier);
    }
    
    @RobotKeyword("Finds the first row index that has a cell  that contains the given _text_.\n"
        + "This is useful when we want to operate on cells which location can change.\n\n"
        + "Example:\n"
        + "| ${row}= | Find Table Row | _myTable_ | _Some Value_ |\n"
        + "| Select From Table Cell Popup Menu | _myTable_ | _${row}_ | _2_ | _Activate_ |\n")
    public int findTableRow(String identifier, String text) {
        return createTableOperator(identifier).findCellRow(text);
    }
    
    @RobotKeyword("Selects an item from a table cell popup.\n"
        + "Separator for items is '|'.\n\n"
        + "Example:\n"
        + "| Select From Table Cell Popup Menu | _myTable_ | _1_ | _3_ | _Cell Actions|Clear Cell Value_ | ")
    public void selectFromTableCellPopupMenu(String identifier, String row, String columnIdentifier, String menuPath) {
        JMenuItemOperator menuItem = getPopupMenuItem(identifier, row, columnIdentifier, menuPath);
        menuItem.push();
    }
    
    @RobotKeyword("Fails if the given table cell popup menu is disabled.\n"
        + "Separator for items is '|'.\n\n"
        + "Example:\n"
        + "| Table Cell Popup Menu Should Be Enabled | _myTable_ | _1_ | _3_ | _Cell Actions|Clear Cell Value_ |\n")
    public void tableCellPopupMenuShouldBeEnabled(String identifier, String row, String columnIdentifier, String menuPath) {
        JMenuItemOperator menuItem = getPopupMenuItem(identifier, row, columnIdentifier, menuPath);
        String errorMessage = "Menuitem '" + menuPath + "' at '" + row + ", " +  columnIdentifier + "' is disabled.";
        Assert.assertTrue(errorMessage, menuItem.isEnabled());
    }

    @RobotKeyword("Fails if the given table cell popup menu is enabled.\n"
        + "Separator for items is '|'.\n\n"
        + "Example:\n"
        + "| Table Cell Popup Menu Should Be Disabled | _myTable_ | _1_ | _3_ | _Cell Actions|Clear Cell Value_ |\n")
    public void tableCellPopupMenuShouldBeDisabled(String identifier, String row, String columnIdentifier, String menuPath) {
        JMenuItemOperator menuItem = getPopupMenuItem(identifier, row, columnIdentifier, menuPath);
        String errorMessage = "Menuitem '" + menuPath + "' at '" + row + ", " +  columnIdentifier + "' is enabled.";
        Assert.assertFalse(errorMessage, menuItem.isEnabled());
    }

    @RobotKeyword("Returns table's header names.\n\n"
        + "Example:\n"
        + "| @{headers}= | Get Table Headers | _myTable_ |\n")
    public String[] getTableHeaders(String identifier) {
        return createTableOperator(identifier).getTableHeaders();
    }
    
    @RobotKeyword("Returns a list containing all the values of a table column.\n\n"
        + "Example:\n"
        + "| _${columnValues}=_ | Get Table Column Values | _myTable_ | _columnTwo_ |\n"
        + "| Should Contain  | _${expectedValue}_ | _${columnValues}_ |\n")
    public Object[] getTableColumnValues(String identifier, String columnIdentifier) {
        return createTableOperator(identifier).getColumnValues(columnIdentifier);
    }
    
    @RobotKeyword("Returns the property of the table cell.\n\n"
        + "Example:\n"
        + "| _${background}=_ | Get Table Cell Properties | _myTable_ | _1_ | _2_ | _background_ |\n"
        + "| Should Be Equal As Integers | _255_ | _${background.getRed()}_ | | | |\n")
    public Object getTableCellProperty(String identifier, String row, String columnIdentifier, String propertyName) {
        Map<String, Object> properties = createTableOperator(identifier).getCellProperties(row, columnIdentifier);
        return properties.get(propertyName);
    }
    
    private TableOperator createTableOperator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }
    
    private JMenuItemOperator getPopupMenuItem(String identifier, String row, String columnIdentifier, String menuPath) {
        TableOperator tableOperator = createTableOperator(identifier);
        JPopupMenuOperator popupMenuOperator = tableOperator.callPopupOnCell(row, columnIdentifier);
        return popupMenuOperator.showMenuItem(menuPath, new EqualsStringComparator());
    }
}
