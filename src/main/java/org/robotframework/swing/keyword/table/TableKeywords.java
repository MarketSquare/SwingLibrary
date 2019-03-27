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

package org.robotframework.swing.keyword.table;

import java.awt.*;
import java.util.Map;

import org.junit.Assert;

import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.utils.OptionalArgsForTableCellAndComponentClicking;
import org.robotframework.swing.table.CellValueExtractor;
import org.robotframework.swing.table.TableOperator;
import org.robotframework.swing.table.TableOperatorFactory;


@RobotKeywords
public class TableKeywords extends IdentifierSupport {
    private final OperatorFactory<TableOperator> operatorFactory = new TableOperatorFactory();

    @RobotKeyword("Selects a cell in a table.\n\n"
            + "Examples:\n"
            + "| `Select Table Cell` | myTable | 0 | 2       | # Selects cell from first row and third column |\n"
            + "| `Select Table Cell` | myTable | 1 | Keyword | # Selects cell from second row and column with header 'Keyword' |\n")
    @ArgumentNames({"identifier", "row", "cellIdentifier"})
    public void selectTableCell(String identifier, String row, String cellIdentifier) {
        createTableOperator(identifier).selectCell(row, cellIdentifier);
    }

    @RobotKeyword("Selects a cell in a table and adds it to the selection.\n"
            + "Does not clear earlier selections.\n\n"
            + "Examples:\n"
            + "| `Select Table Cell` | myTable | 0 | 2       | # Selects cell from first row and third column |\n"
            + "| `Select Table Cell` | myTable | 1 | Keyword | # Selects cell from second row and column with header 'Keyword' |\n")
    @ArgumentNames({"identifier", "row", "cellIdentifier"})
    public void addTableCellSelection(String identifier, String row, String cellIdentifier) {
        createTableOperator(identifier).addTableCellSelection(row, cellIdentifier);
    }

    @RobotKeyword("Selects a cell area in a table.\n"
            + "Does not clear earlier selections.\n\n"
            + "Example:\n"
            + "| `Select Table Cell Area` | myTable | 0 | 2 | 0 | 2 | # Selects cells from first to third row and first to third column |\n")
    @ArgumentNames({"identifier", "startRow", "endRow", "startColumn", "endColumn"})
    public void selectTableCellArea(String identifier, String startRow, String endRow, String startColumn, String endColumn) {
        createTableOperator(identifier).selectCellArea(startRow, endRow, startColumn, endColumn);
    }

    @RobotKeyword("Clears selection from a table.\n\n"
            + "Example:\n"
            + "| `Clear Table Selection` | myTable |\n")
    @ArgumentNames({"identifier"})
    public void clearTableSelection(String identifier) {
        createTableOperator(identifier).clearSelection();
    }

    @RobotKeyword("Fails if given table cell is not editable in a table.\n\n"
            + "Examples:\n"
            + "| `Table Cell Should Be Editable` | myTable | 0 | 2       |\n"
            + "| `Table Cell Should Be Editable` | myTable | 1 | Keyword |\n")
    @ArgumentNames({"identifier", "row", "columnIdentifier"})
    public void tableCellShouldBeEditable(String identifier, String row, String columnIdentifier) {
        TableOperator tableOperator = createTableOperator(identifier);
        Assert.assertTrue("Cell '" + row + "', '" + columnIdentifier + "' is not editable.", tableOperator.isCellEditable(row, columnIdentifier));
    }

    @RobotKeyword("Fails if given table cell is editable in a table.\n\n"
            + "Examples:\n"
            + "| `Table Cell Should Not Be Editable` | myTable | 0 | 2       |\n"
            + "| `Table Cell Should Not Be Editable` | myTable | 1 | Keyword |\n")
    @ArgumentNames({"identifier", "row", "columnIdentifier"})
    public void tableCellShouldNotBeEditable(String identifier, String row, String columnIdentifier) {
        TableOperator tableOperator = createTableOperator(identifier);
        Assert.assertFalse("Cell '" + row + "', '" + columnIdentifier + "' is editable.", tableOperator.isCellEditable(row, columnIdentifier));
    }

    @RobotKeyword("Fails if given table cell is not selected in a table.\n\n"
            + "Examples:\n"
            + "| `Table Cell Should Be Selected` | myTable | 0 | 2       |\n"
            + "| `Table Cell Should Be Selected` | myTable | 1 | Keyword |\n")
    @ArgumentNames({"identifier", "row", "columnIdentifier"})
    public void tableCellShouldBeSelected(String identifier, String row, String columnIdentifier) {
        TableOperator tableOperator = createTableOperator(identifier);
        Assert.assertTrue("Cell '" + row + "', '" + columnIdentifier + "' is not selected.", tableOperator.isCellSelected(row, columnIdentifier));
    }

    @RobotKeyword("Fails if given table cell is selected in a table.\n\n"
            + "Examples:\n"
            + "| `Table Cell Should Be Selected` | myTable | 0 | 2       |\n"
            + "| `Table Cell Should Be Selected` | myTable | 1 | Keyword |\n")
    @ArgumentNames({"identifier", "row", "columnIdentifier"})
    public void tableCellShouldNotBeSelected(String identifier, String row, String columnIdentifier) {
        TableOperator tableOperator = createTableOperator(identifier);
        Assert.assertFalse("Cell '" + row + "', '" + columnIdentifier + "' is selected.", tableOperator.isCellSelected(row, columnIdentifier));
    }

    @RobotKeyword("Returns cell's value from a table.\n\n"
            + "The value from cell rendered with check box is a true/false string.\n"
            + "Optional parameter ``source`` allows to override text extraction strategy. "
            + "Available values are ``auto`` (default, will try to get text from cell component "
            + "first and then from table model) and ``model`` (will only try to get text from table model).\n\n"
            + "Example:\n"
            + "| ${cellValue}=     | `Get Table Cell Value` | myTable | 0            | 2 |\n"
            + "| `Should Be Equal` | tuesday                |         | ${cellValue} |   |\n")
    @ArgumentNames({"identifier", "row", "columnIdentifier", "source=auto"})
    public String getTableCellValue(String identifier, String row, String columnIdentifier, String source) {
        TableOperator operator = createTableOperator(identifier);
        Component component = operator.getSource();
        CellValueExtractor.TextSource textSource = textExtractionSourceFromText(source);
        return operator.getCellValue(row, columnIdentifier, textSource);
    }

    private CellValueExtractor.TextSource textExtractionSourceFromText(String text) {
        if (text.toLowerCase().equals("model"))
            return CellValueExtractor.TextSource.MODEL;
        else
            return CellValueExtractor.TextSource.AUTO;
    }

    @RobotKeywordOverload
    public String getTableCellValue(String identifier, String row, String columnIdentifier) {
        return getTableCellValue(identifier, row, columnIdentifier, "auto");
    }

    @RobotKeyword("Returns selected cell's value from a table.\n\n"
            + "Optional parameter ``source`` allows to override text extraction strategy. "
            + "Available values are ``auto`` (default, will try to get text from cell component "
            + "first and then from table model) and ``model`` (will only try to get text from table model).\n\n"
            + "Example:\n"
            + "| ${cellValue}=   | `Get Selected Table Cell Value`   | myTable      |\n"
            + "| `Should Be Equal` | tuesday                         | ${cellValue} |\n")
    @ArgumentNames({"identifier", "source=auto"})
    public Object getSelectedTableCellValue(String identifier, String source) {
        CellValueExtractor.TextSource textSource = textExtractionSourceFromText(source);
        return createTableOperator(identifier).getSelectedCellValue(textSource).toString();
    }

    @RobotKeywordOverload
    public Object getSelectedTableCellValue(String identifier) {
        return getSelectedTableCellValue(identifier, "auto");
    }

    @RobotKeyword("Sets cell value in a table.\n\n"
            + "Example:\n"
            + "| `Set Table Cell Value` | 1 | 2 | New value |\n")
    @ArgumentNames({"identifier", "row", "columnIdentifier", "newValue"})
    public void setTableCellValue(String identifier, String row, String columnIdentifier, String newValue) {
        TableOperator tableOperator = createTableOperator(identifier);
        if (! tableOperator.isCellEditable(row, columnIdentifier))
            throw new RuntimeException("Cell '" + row + "', '" + columnIdentifier + "' is not editable.");
        tableOperator.setCellValue(newValue, row, columnIdentifier);
    }

    @RobotKeyword("Types a string into a table cell.\n"
            + "Uses real keyboard events, this is useful when processing need to be triggered by input events.\n\n"
            + "Example:\n"
            + "| `Type Into Table Cell` | 1 | 2 | New value |\n")
    @ArgumentNames({"identifier", "row", "columnIdentifier", "newValue"})
    public void typeIntoTableCell(String identifier, String row, String columnIdentifier, String newValue) {
        TableOperator tableOperator = createTableOperator(identifier);
        tableOperator.typeIntoCell(newValue, row, columnIdentifier);
    }

    @RobotKeyword("Returns the number of columns from a table.\n\n"
            + "Example:\n"
            + "| ${columnCount}= | `Get Table Column Count` | myTable |\n"
            + "| `Should Be Equal As Integers` | 4 | ${columnCount} |\n")
    @ArgumentNames({"identifier"})
    public int getTableColumnCount(String identifier) {
        return createTableOperator(identifier).getColumnCount();
    }

    @RobotKeyword("Returns the number of rows from a table.\n\n"
            + "Example:\n"
            + "| ${rowCount}= | `Get Table Row Count` | myTable |\n"
            + "| `Should Be Equal As Integers` | 4 | ${rowCount} |\n")
    @ArgumentNames({"identifier"})
    public int getTableRowCount(String identifier) {
        return createTableOperator(identifier).getRowCount();
    }

    @RobotKeyword("Clears table cell contents.\n\n"
            + "Example:\n"
            + "| `Clear Table Cell` | myTable | 1 | 2 |\n")
    @ArgumentNames({"identifier", "row", "columnIdentifier"})
    public void clearTableCell(String identifier, String row, String columnIdentifier) {
        createTableOperator(identifier).clearCell(row, columnIdentifier);
    }

    @RobotKeyword("Finds the first row index that has a cell  that contains the given text.\n"
            + "This is useful when we want to operate on cells which location can change.\n\n"
            + "Examples:\n"
            + "| ${row}= | `Find Table Row` | myTable | Some Value |\n"
            + "| `Select From Table Cell Popup Menu` | myTable | ${row} | 2 | Activate |\n"
            + "| ${row}= | `Find Table Row` | myTable | Some Value | Some Column | # Searches the 'Some Value' from the specified  'Some Column'  | \n")
    @ArgumentNames({"identifier", "text", "columnIdentifier="})
    public int findTableRow(String identifier, String text, String columnIdentifier) {
        if (columnIdentifier != null && !"".equals(columnIdentifier))
            return createTableOperator(identifier).findCellRow(text, columnIdentifier);
        return createTableOperator(identifier).findCellRow(text);
    }

    @RobotKeywordOverload
    public int findTableRow(String identifier, String text) {
        return findTableRow(identifier, text, "");
    }

    @RobotKeyword("Selects an item from a table cell popup.\n"
            + "Separator for items is ``|``.\n\n"
            + "Example:\n"
            + "| `Select From Table Cell Popup Menu` | myTable | 1 | 3 | Cell Actions|Clear Cell Value | ")
    @ArgumentNames({"identifier", "row", "columnIdentifier", "menuPath"})
    public void selectFromTableCellPopupMenu(String identifier, String row, String columnIdentifier, String menuPath) {
        JMenuItemOperator menuItem = getPopupMenuItem(identifier, row, columnIdentifier, menuPath);
        menuItem.push();
    }

    @RobotKeyword("Selects an item from a table cell popup on the table cells that are selected.\n"
            + "Separator for items is ``|``.\n\n"
            + "Example:\n"
            + "| `Select From Table Cell Popup Menu On Selected Cells` | myTable | Cell Actions|Clear Cell Value | ")
    @ArgumentNames({"identifier", "menuPath"})
    public void selectFromTableCellPopupMenuOnSelectedCells(String identifier, String menuPath) {
        createTableOperator(identifier).callPopupMenuItemOnSelectedCells(menuPath);
    }

    @RobotKeyword("Fails if the given table cell popup menu is disabled.\n"
            + "Separator for items is ``|``.\n\n"
            + "Example:\n"
            + "| `Table Cell Popup Menu Should Be Enabled` | myTable | 1 | 3 | Cell Actions|Clear Cell Value |\n")
    @ArgumentNames({"identifier", "row", "columnIdentifier", "menuPath"})
    public void tableCellPopupMenuShouldBeEnabled(String identifier, String row, String columnIdentifier, String menuPath) {
        JMenuItemOperator menuItem = getPopupMenuItem(identifier, row, columnIdentifier, menuPath);
        String errorMessage = "Menuitem '" + menuPath + "' at '" + row + ", " +  columnIdentifier + "' is disabled.";
        Assert.assertTrue(errorMessage, menuItem.isEnabled());
    }

    @RobotKeyword("Fails if the given table cell popup menu is enabled.\n"
            + "Separator for items is ``|``.\n\n"
            + "Example:\n"
            + "| `Table Cell Popup Menu Should Be Disabled` | myTable | 1 | 3 | Cell Actions|Clear Cell Value |\n")
    @ArgumentNames({"identifier", "row", "columnIdentifier", "menuPath"})
    public void tableCellPopupMenuShouldBeDisabled(String identifier, String row, String columnIdentifier, String menuPath) {
        JMenuItemOperator menuItem = getPopupMenuItem(identifier, row, columnIdentifier, menuPath);
        String errorMessage = "Menuitem '" + menuPath + "' at '" + row + ", " +  columnIdentifier + "' is enabled.";
        Assert.assertFalse(errorMessage, menuItem.isEnabled());
    }

    @RobotKeyword("Returns table's header names.\n\n"
            + "Example:\n"
            + "| @{headers}= | `Get Table Headers` | myTable |\n")
    @ArgumentNames({"identifier"})
    public String[] getTableHeaders(String identifier) {
        return createTableOperator(identifier).getTableHeaders();
    }

    @RobotKeyword("Returns a list containing all the values of a table column.\n\n"
            + "Example:\n"
            + "| ${columnValues}=  | `Get Table Column Values` | myTable | columnTwo |\n"
            + "| `Should Contain`  | ${expectedValue}  | ${columnValues} |\n")
    @ArgumentNames({"identifier", "columnIdentifier"})
    public Object[] getTableColumnValues(String identifier, String columnIdentifier) {
        return createTableOperator(identifier).getColumnValues(columnIdentifier);
    }

    @RobotKeyword("Returns a list containing all the values of a table row.\n\n"
                + "Example:\n"
                + "| ${rowValues}= | `Get Table Row Values` | myTable | 2 |\n"
                + "| `Should Contain`  | ${expectedValue} | ${rowValues} |\n")
    @ArgumentNames({"identifier", "row"})
    public Object[] getTableRowValues(String identifier, int row) {
        return createTableOperator(identifier).getRowValues(row);
    }

    @RobotKeyword("Returns a list of table rows containing all the values of a table.\n\n"
                + "Example:\n"
                + "| ${tableValues}= | `Get Table Values` | myTable | rowThree |\n"
                + "| `Should Be Equal`  | ${expectedValue} | ${tableValues[1][2]} |\n")
    @ArgumentNames({"identifier"})
    public Object[][] getTableValues(String identifier) {
        return createTableOperator(identifier).getTableValues();
    }

    @RobotKeyword("Returns the property of the table cell.\n\n"
            + "Example:\n"
            + "| ${background}= | `Get Table Cell Property` | myTable | 1 | 2 | background |\n"
            + "| `Should Be Equal As Integers` | 255 | ${background.getRed()} | | | |\n")
    @ArgumentNames({"identifier", "row", "columnIdentifier", "propertyName"})
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

    @RobotKeyword("Clicks on a cell in a table, optionally using click count, a specific mouse button and keyboard modifiers.\n\n"
            + "The codes used for mouse button and key modifiers are the field names from ``java.awt.event.InputEvent``. "
            + "For example: ``BUTTON1_MASK``, ``CTRL_MASK``, ``ALT_MASK``, ``ALT_GRAPH_MASK``, ``SHIFT_MASK``, and ``META_MASK``.\n\n"
            + "*Note:* Some keys have more convinient case insensitive aliases that can be used: ``LEFT BUTTON``, ``RIGHT BUTTON``, ``SHIFT``, "
            + "``CTRL``, ``ALT``, ``META``\n\n"
            + "Examples:\n"
            + "| `Click On Table Cell`  | myTable | 0 | 2 | # Double clicks with mouse button 2 on the cell in the first row and third column... |\n"
            + "| ... | 2 | RIGHT BUTTON | ALT | # ... while holding down the ALT key |\n"
            + "| `Click On Table Cell`  | myTable | 1 | Header | # Single click on the cell in the second row and column with header 'Header'... |\n"
            + "| ... | 1 | BUTTON1_MASK | CTRL_MASK | SHIFT_MASK | # ... while holding down the CTRL and SHIFT keys |\n")
    @ArgumentNames({"identifier", "row", "column", "clickCountString=1", "buttonString=BUTTON1_MASK", "*keyModifierStrings"})
    public void clickOnTableCell(final String identifier, final String row, final String column, final String[] optionalArgs) {
        OptionalArgsForTableCellAndComponentClicking optArgs = new OptionalArgsForTableCellAndComponentClicking(optionalArgs);
        createTableOperator(identifier).clickOnCell(row,
                column,
                optArgs.clickCount(),
                optArgs.button(),
                optArgs.keyModifiers());
    }

    @RobotKeyword("Clicks on table header of the given column.\n\n"
            + "Column can be identified either by index or by column title. Indexes start at 0.\n\n"
            + "Examples:\n"
            + "| `Click Table Header` | myTable | 2 | # Click the third column |\n"
            + "| `Click Table Header` | myTable | amount | # Click the column that has title 'amount' |")
    @ArgumentNames({"identifier", "columnIdentifier"})
    public void clickTableHeader(String tableIdentifier, String columnIdentifier) {
        createTableOperator(tableIdentifier).headerOperator().clickColumn(columnIdentifier);
    }
}
