*** Settings ***
Test Setup      clearTable
Library         TestSwingLibrary
Library         Collections

*** Variables ***
${tableName}  testTable
${columnHeaderOne}  column one
${columnHeaderThree}  column three
${enabledMenuItem}  Replace text
${disabledMenuItem}  Disabled menuitem

*** Test Cases ***
Click On Table Cell
    tableCellShouldNotBeSelected  ${tableName}  1  1
    Click On Table Cell  ${tableName}  1  1
    tableCellShouldBeSelected  ${tableName}  1  1
    Click On Table Cell  ${tableName}  1  1  2  BUTTON2_MASK
    assertCellClick  ${tableName}  2  2  512

Table Cell Should Be Editable
    Table Cell Should Be Editable  simpleTable  0  0
    Run Keyword And Expect Error  Cell '1', '1' is not editable.  Table Cell Should Be Editable  simpleTable  1  1

Table Cell Should Not Be Editable
    Table Cell Should Not Be Editable  simpleTable  0  1
    Run Keyword And Expect Error  Cell '1', '0' is editable.  Table Cell Should Not Be Editable  simpleTable  1  0

Table Cell Should Not Be Selected By Index
    tableCellShouldNotBeSelected  ${tableName}  0  0

Table Cell Should Not Be Selected By Column Header
    tableCellShouldNotBeSelected  ${tableName}  0  ${columnHeaderOne}

Table Cell Should Be Selected By Index
    selectTableCell  ${tableName}  3  2
    tableCellShouldBeSelected  ${tableName}  3  2

Table Cells Should Be Selected By Index
    selectTableCell  ${tableName}  0  0
    tableCellShouldBeSelected  ${tableName}  0  0

Many Table Cells Should Be Selected By Index
    selectTableCell  ${tableName}  0  0
    addTableCellSelection  ${tableName}  2  1
    addTableCellSelection  ${tableName}  2  2
    addTableCellSelection  ${tableName}  3  1
    addTableCellSelection  ${tableName}  3  2
    tableCellShouldBeSelected  ${tableName}  0  0
    tableCellShouldBeSelected  ${tableName}  2  1
    tableCellShouldBeSelected  ${tableName}  2  2
    tableCellShouldBeSelected  ${tableName}  3  1
    tableCellShouldBeSelected  ${tableName}  3  2

Table Cell Area Should Be Selected
    selectTableCellArea  ${tableName}  1  2  1  2
    tableCellShouldBeSelected  ${tableName}  1  1
    tableCellShouldBeSelected  ${tableName}  1  2
    tableCellShouldBeSelected  ${tableName}  2  1
    tableCellShouldBeSelected  ${tableName}  2  2
    tableCellShouldNotBeSelected  ${tableName}  0  0
    tableCellShouldNotBeSelected  ${tableName}  0  3
    tableCellShouldNotBeSelected  ${tableName}  3  0
    tableCellShouldNotBeSelected  ${tableName}  3  3

Table Cell Should Be Selected By Column Header
    selectTableCell  ${tableName}  3  column two
    tableCellShouldBeSelected  ${tableName}  3  column two

Select Table Cell By Index
    selectTableCell  ${tableName}  3  2
    ${cellValue}=  getSelectedTableCellValue  ${tableName}
    shouldBeEqual  four/three  ${cellValue}

Select Table Cell By Column Header Name
    selectTableCell  ${tableName}  1  column three
    ${cellValue}=  getSelectedTableCellValue  ${tableName}
    shouldBeEqual  two/three  ${cellValue}

Select Table Cell Should Fail If The Cell Doesn't Exist
    runKeywordAndExpectError  The specified table cell (row: 10, column: 15) is invalid.  selectTableCell  ${tableName}  10  15
    runKeywordAndExpectError  The specified table cell (row: 10, column: nonexisting) is invalid.  selectTableCell  ${tableName}  10  nonexisting

Clear Table Selection
    selectTableCell  ${tableName}  1  2
    tableCellShouldBeSelected  ${tableName}  1  2
    clearTableSelection  ${tableName}
    tableCellShouldNotBeSelected  ${tableName}  1  2

Get Selected Table Cell Value
    selectTableCell  ${tableName}  3  2
    ${cellValue}=  getSelectedTableCellValue  ${tableName}
    shouldBeEqual  four/three  ${cellValue}

Get Selected Table Cell Value From Model
    selectTableCell  ${tableName}  3  2
    ${cellValue}=  getSelectedTableCellValue  ${tableName}  source=MODEL
    shouldBeEqual  four/three!!!  ${cellValue}

Get Table Cell Value By Index
    ${cellValue}=  getTableCellValue  ${tableName}  3  2
    shouldBeEqual  four/three  ${cellValue}

Get Table Cell Value From Model By Index
    ${cellValue}=  getTableCellValue  ${tableName}  3  2  source=MODEL
    shouldBeEqual  four/three!!!  ${cellValue}

Clear Table Cell
    clearTableCell  ${tableName}  2  1
    ${cellValue}=  getTableCellValue  ${tableName}  2  1
    shouldBeEmpty  ${cellValue}

Get Table Cell Value By Column Header
    ${cellValue}=  getTableCellValue  ${tableName}  1  column two
    shouldBeEqual  two/two  ${cellValue}

Type Into Table Cell
    typeIntoTableCell  ${tableName}  2  1  newValue
    ${cellValue}=  getTableCellValue  ${tableName}  2  1
    shouldBeEqual  newValue  ${cellValue}

Set Table Cell Value
    setTableCellValue  ${tableName}  1  0  four/one
    ${cellValue}=  getTableCellValue  ${tableName}  1  0
    shouldBeEqual  four/one  ${cellValue}
    run keyword and expect error  Cell '1', '1' is not editable.  setTableCellValue  simpleTable  1  1  some value

Get Table Column Count By Name
    ${columnCount}=  getTableColumnCount  ${tableName}
    shouldBeEqualAsIntegers  4  ${columnCount}

Get Table Column Count By Index
    ${columnCount}=  getTableColumnCount  0
    shouldBeEqualAsIntegers  4  ${columnCount}

Get Table Row Count By Name
    ${rowCount}=  getTableRowCount  ${tableName}
    shouldBeEqualAsIntegers  4  ${rowCount}

Find Table Row
    ${row}=  findTableRow  ${tableName}  one/one
    shouldBeEqualAsIntegers  0  ${row}
    ${row}=  findTableRow  ${tableName}  four/three!!!
    shouldBeEqualAsIntegers  3  ${row}

Find Table Row With Column
    ${row}=  findTableRow  ${tableName}  one/one  column one
    shouldBeEqualAsIntegers  0  ${row}
    ${row}=  findTableRow  ${tableName}  four/three!!!  column three
    shouldBeEqualAsIntegers  3  ${row}
    ${row}=  findTableRow  TableWithSingleValue  foo
    shouldBeEqualAsIntegers  0  ${row}
    ${row}=  findTableRow  TableWithSingleValue  foo  column three
    shouldBeEqualAsIntegers  2  ${row}
    ${row}=  findTableRow  TableWithSingleValue  foo  column two
    shouldBeEqualAsIntegers  1  ${row}

Find Table Row With Column Identifier As Index
    ${row}=  findTableRow  ${tableName}  one/one  0
    shouldBeEqualAsIntegers  0  ${row}

Get Table Row Count By Index
    ${rowCount}=  getTableRowCount  0
    shouldBeEqualAsIntegers  4  ${rowCount}

Select From Table Cell Popup Menu
    selectFromTableCellPopupMenu  ${tableName}  2  column one  Submenu|Enabled menuitem
    selectFromTableCellPopupMenu  ${tableName}  2  1  Replace text
    ${newCellValue}=  getTableCellValue  ${tableName}  2  1
    shouldBeEqual  newValue  ${newCellValue}

Select From Popup On Selected Cells
    selectTableCellArea  ${tableName}  1  2  1  2
    selectFromTableCellPopupMenuOnSelectedCells  ${tableName}  Replace text in selected
    ${newCellValue}=  getTableCellValue  ${tableName}  1  1
    shouldBeEqual  newValue  ${newCellValue}
    ${newCellValue}=  getTableCellValue  ${tableName}  1  2
    shouldBeEqual  newValue  ${newCellValue}
    ${newCellValue}=  getTableCellValue  ${tableName}  2  1
    shouldBeEqual  newValue  ${newCellValue}
    ${newCellValue}=  getTableCellValue  ${tableName}  2  2
    shouldBeEqual  newValue  ${newCellValue}

Select From Table Cell Popup Menu Accepts Integer Arguments
    ${rowAsInteger}=  convertToInteger  3
    selectFromTableCellPopupMenu  ${tableName}  ${rowAsInteger}  1  Replace text
    ${newCellValue}=  getTableCellValue  ${tableName}  ${rowAsInteger}  1
    shouldBeEqual  newValue  ${newCellValue}

Table Cell Popup Menu Should Be Disabled
    tableCellPopupMenuShouldBeDisabled  ${tableName}  2  column one  ${disabledMenuItem}
    runKeywordAndExpectError  Menuitem '${enabledMenuItem}' at '2, column one' is enabled.  tableCellPopupMenuShouldBeDisabled  ${tableName}  2  column one  ${enabledMenuItem}

Table Cell Popup Menu Should Be Enabled
    tableCellPopupMenuShouldBeEnabled  ${tableName}  2  column one  ${enabledMenuItem}
    runKeywordAndExpectError  Menuitem '${disabledMenuItem}' at '2, column one' is disabled.  tableCellPopupMenuShouldBeEnabled  ${tableName}  2  column one  ${disabledMenuItem}

Get Table Headers
    ${header1}  ${header2}  ${header3}  ${header4}=  getTableHeaders  ${tableName}
    shouldBeEqual  column one  ${header1}
    shouldBeEqual  column two  ${header2}
    shouldBeEqual  column three  ${header3}
    shouldBeEqual  column four  ${header4}

Table Keywords Fail If Context Is Not Set
    selectEmptyContext
    tableKeywordShouldFail  getTableCellValue  someTable  0  0
    tableKeywordShouldFail  getSelectedTableCellValue  someTable
    tableKeywordShouldFail  tableCellShouldBeSelected  someTable  0  0
    tableKeywordShouldFail  tableCellShouldNotBeSelected  someTable  0  0
    tableKeywordShouldFail  selectTableCell  someTable  0  0
    tableKeywordShouldFail  clearTableSelection  someTable
    tableKeywordShouldFail  getTableColumnCount  someTable
    tableKeywordShouldFail  getTableRowCount  someTable
    [Teardown]  selectMainWindow

Get Table Column Values
    ${expectedValues}=  createList  bar  bar  foo  bar 3
    ${values}=  getTableColumnValues  TableWithSingleValue  ${columnHeaderThree}
    listsShouldBeEqual  ${expectedValues}  ${values}

Get Table Row Values
    ${expectedValues}=  createList  bar 1  bar 2  bar 3  foo 4
    ${values}=  getTableRowValues  TableWithSingleValue  3
    listsShouldBeEqual  ${expectedValues}  ${values}

Get Table Values
    ${values}=  getTableValues  TableWithSingleValue
    Should Be Equal  ${values[1][1]}   foo
    Should Be Equal  ${values[3][2]}   bar 3
    Should Be Equal  ${values[0][2]}   bar
    Should Be Equal  ${values[3][3]}   foo 4

Get Table Cell Properties
    ${color}=  getTableCellProperty  ${tableName}  2  2  background
    shouldBeEqualAsIntegers  255  ${color.getRed()}
    shouldBeEqualAsIntegers  255  ${color.getGreen()}
    shouldBeEqualAsIntegers  255  ${color.getBlue()}

Click On Table Header
    assertCellValue  tableWithHeader  0  0  ${EMPTY}
    clickTableHeader  tableWithHeader  0
    assertCellValue  tableWithHeader  0  0  Col header 0
    clickTableHeader  tableWithHeader  B
    assertCellValue  tableWithHeader  0  0  Col header 1
    runKeywordAndExpectError  The specified column identifier 'kekkonen' is invalid.
    ...  clickTableHeader  tableWithHeader  kekkonen
    runKeywordAndExpectError  The specified column identifier '666' is invalid.
    ...  clickTableHeader  tableWithHeader  666

Get Value From Check Box
    assertCellValue  ${tableName}  1  3  true
    assertCellValue  ${tableName}  3  3  false


*** Keywords ***
assertCellClick
    [Arguments]  ${tableName}  ${clickCount}  ${button}  ${modEx}
    ${text}=  Get Text Field Value  tableEventTextField
    Should Be Equal  ${text}  ${tableName} cell clicked ${clickCount} times, button: ${button} modEx: ${modEx}

tableKeywordShouldFail
    [Arguments]  ${keyword}  @{arguments}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  ${keyword}  @{arguments}

clearTable
    selectMainWindow
    clearTableSelection  ${tableName}

assertCellValue
    [Arguments]  ${tableId}  ${row}  ${col}  ${expected}
    ${val}=  getTableCellVAlue  ${tableId}  ${row}  ${col}
    Should Be Equal  ${val}  ${expected}
