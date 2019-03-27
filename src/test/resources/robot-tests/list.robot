*** Settings ***
Test Setup      clearSelectionFromList  ${listIndex}
Library         TestSwingLibrary
Library         Collections
Variables       platform_info.py

*** Variables ***
${listName}  testList
${listWithRendererName}  listWithRenderer
${listIndex}  0
@{listValues}  one  two  three

*** Test Cases ***
Get Selected List Value From List By Index
    selectFromList  ${listIndex}  0
    ${selectedValue}=  getSelectedValueFromList  0
    shouldBeEqual  ${selectedValue}  one

Get Selected List Value From List By Name
    selectFromList  ${listName}  0
    ${selectedValue}=  getSelectedValueFromList  ${listName}
    shouldBeEqual  ${selectedValue}  one

Get Selected List Value From Custom Rendered List By Name
    selectFromList  ${listWithRendererName}  0
    ${selectedValue}=  getSelectedValueFromList  ${listWithRendererName}
    shouldBeEqual  ${selectedValue}  ONE
    selectFromList  ${listWithRendererName}  TWO
    ${selectedValue}=  getSelectedValueFromList  ${listWithRendererName}
    shouldBeEqual  ${selectedValue}  TWO

Select From List By Index
    selectFromList  ${listIndex}  0
    listSelectionShouldBe  ${listIndex}  one
    selectFromList  ${listIndex}  1
    listSelectionShouldBe  ${listIndex}  two

Select From List By Name
    selectFromList  ${listName}  one
    listSelectionShouldBe  ${listName}  one
    selectFromList  ${listName}  three
    listSelectionShouldBe  ${listName}  three

Select From List By Name Not Found
    runKeywordAndExpectError  Couldn't find text 'horse'  selectFromList  ${listName}  horse

Select Multiple Items From List
    Comment    Selecting multiple list elements does not work on OSX. This seems to be a Jemmy bug.
    Run keyword if   $is_osx  Set tags  non-critical
    selectFromList  ${listName}  one  two  three
    allListItemsShouldBeSelected

Selecting Item By A Substring Of The Name Should Fail
    runKeywordAndExpectError  *  selectFromList  ${listIndex}  hre

Click On List Item
    clickOnListItem  ${listName}  three  2
    listSelectionShouldBe  ${listName}  three
    ${clickCount}=  getSelectionClickCount
    shouldBeEqualAsIntegers  2  ${clickCount}

Select All List Items
    selectAllListItems  ${listName}
    allListItemsShouldBeSelected

Clear Selection From List
    selectFromList  ${listIndex}  three
    listSelectionShouldBe  ${listIndex}  three
    clearSelectionFromList  ${listIndex}
    listSelectionShouldNotBe  ${listIndex}  three

Get List Item Count By Index
    ${itemCount}=  getListItemCount  ${listIndex}
    shouldBeEqualAsIntegers  3  ${itemCount}

Get List Item Count By Name
    ${itemCount}=  getListItemCount  ${listName}
    shouldBeEqualAsIntegers  3  ${itemCount}

List Keywords Should Fail If Context Is Not A Window
    [Setup]  selectEmptyContext
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  selectFromList  0  0
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  getSelectedValueFromList  0
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  clearSelectionFromList  0
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  getListItemCount  0
    [Teardown]  selectMainWindow

Get List Values
    ${values}=  Get List Values  ${listName}
    Lists Should Be Equal  ${listValues}  ${values}

List Should Contain
    List Should Contain  ${listName}  one
    runKeywordAndExpectError  List ${listName} does not contain missing element  List Should Contain  ${listName}  missing element

List Should Not Contain
    List Should Not Contain  ${listName}  missing element
    runKeywordAndExpectError  List ${listName} contains one  List Should Not Contain  ${listName}  one

Select From List Item Popup Menu
    selectFromListItemPopupMenu  ${listName}  three  Show name
    selectDialog  Message
    ${labelContents}=  getLabelContent    OptionPane.label
    shouldBeEqual  ${listName}  ${labelContents}
    [Teardown]  Run Keywords  closeDialog  Message  AND  selectMainWindow

*** Keywords ***
listSelectionShouldBe
    [Arguments]  ${listIndex}  ${expectedValue}
    ${selectedValue}=  getSelectedValueFromList  ${listIndex}
    shouldBeEqual  ${expectedValue}  ${selectedValue}

listSelectionShouldNotBe
    [Arguments]  ${listIndex}  ${expectedValue}
    ${selectedValue}=  getSelectedValueFromList  ${listIndex}
    shouldNotBeEqual  ${expectedValue}  ${selectedValue}

allListItemsShouldBeSelected
    ${selectedValues}=  getSelectedValues  0
    listsShouldBeEqual  ${listValues}  ${selectedValues}

