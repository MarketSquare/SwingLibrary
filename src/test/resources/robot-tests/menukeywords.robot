*** Settings ***
Test Teardown   selectMainWindow
Library         TestSwingLibrary

*** Variables ***
${menuText}  Test Menu
${showDialog}  ${menuText}|Show Test Dialog
${mutableMenu}  ${menuText}|Mutable Menu
${disabledMenu}  ${menuText}|Disabled Menu Item
${checkboxMenu}  Test menu checkbox

*** Test Cases ***
Select From Main Menu
    selectEmptyContext
    selectFromMainMenu  ${showDialog}
    dialogShouldBeOpen  Message
    closeDialog  Message

Select From Main Menu And Wait
    selectEmptyContext
    selectFromMainMenuAndWait  ${mutableMenu}
    mainMenuItemShouldExist  ${menuText}|Menu Item Was Pushed

Selecting Disabled Menu Item Should Fail
    selectEmptyContext
    runKeywordAndExpectError  Menu item '${disabledMenu}' is disabled.  selectFromMainMenu  ${disabledMenu}

Menu Item Should Exist
    menuItemShouldExist  ${showDialog}
    runKeywordAndExpectError  Menu item 'unexisting|menu' does not exist.  menuItemShouldExist  unexisting|menu

Menu Item Should Not Exist
    menuItemShouldNotExist  unexisting|menu
    runKeywordAndExpectError  Menu item '${showDialog}' exists.  menuItemShouldNotExist  ${showDialog}

Main Menu Item Should Exist
    selectEmptyContext
    mainMenuItemShouldExist  ${showDialog}
    runKeywordAndExpectError  Menu item 'unexisting|menu' does not exist.  mainMenuItemShouldExist  unexisting|menu

Main Menu Item Should Not Exist
    selectEmptyContext
    mainMenuItemShouldNotExist  unexisting|menu
    runKeywordAndExpectError  Menu item '${showDialog}' exists.  mainMenuItemShouldNotExist  ${showDialog}

Menu Item Should Be Enabled
    menuItemShouldBeEnabled  ${showDialog}

Menu Item Should Be Disabled
    menuItemShouldBeDisabled  ${disabledMenu}

Get Menu Item Names
    [Template]  menuItemsShouldContain
    Test Menu2|Sub Menu1  item 1
    Test Menu2|Sub Menu1  item 2
    Test Menu2|Sub Menu1  item 3
    Test Menu2  placeholder item 1
    Test Menu2  Sub Menu1
    Test Menu2  placeholder item 2

Get Menu Item Names Failures
    runKeywordAndExpectError  Menu item 'unexisting|menu' does not exist.
    ...  getMenuItemNames  unexisting|menu
    @{submenu}=   getMenuItemNames  Test Menu2|Sub Menu1|item 3
    should be empty  ${submenu}
    @{submenu}=   getMenuItemNames  Test Menu2|empty menu
    should be empty  ${submenu}

Get Main Menu Item Name
    mainMenuItemNameShouldBe  0  ${menuText}

Get Main Menu Item Names
    mainMenuItemNamesShouldContain  ${menuText}

Main Menu Item Should Be Selected
    runKeywordAndExpectError  Menu item '${menuText}|${checkboxMenu}' is not selected.  mainMenuItemShouldBeChecked  ${menuText}|${checkboxMenu}
    selectFromMainMenuAndWait  ${menuText}|${checkboxMenu}
    mainMenuItemShouldBeChecked  ${menuText}|${checkboxMenu}
    [Teardown]  selectFromMainMenuAndWait  ${menuText}|${checkboxMenu}

Main Menu Item Should Not Be Selected
    mainMenuItemShouldNotBeChecked  ${menuText}|${checkboxMenu}
    selectFromMainMenuAndWait  ${menuText}|${checkboxMenu}
    runKeywordAndExpectError  Menu item '${menuText}|${checkboxMenu}' is selected.  mainMenuItemShouldNotBeChecked  ${menuText}|${checkboxMenu}
    [Teardown]  selectFromMainMenuAndWait  ${menuText}|${checkboxMenu}

Select From Popup Menu
    selectFromPopupMenu  testTextField  Show name
    selectDialog  Message
    # On Linux and Windows testTextField index is 0, on OSX it is 1. Using label name is a workaround.
    ${labelContents}=  getLabelContent    OptionPane.label
    shouldBeEqual  testTextField  ${labelContents}
    [Teardown]  Run Keywords  closeDialog  Message  AND  Select main window

Get Menu Items From Popup Menu
    ${items}=  getMenuItemsFromPopupMenu  TableWithSingleValue  ${EMPTY}
    Should contain  ${items}  Replace text
    Should contain  ${items}  Disabled menuitem
    Should contain  ${items}  Submenu
    Should contain  ${items}  Replace text in selected

Get Menu Items From Popup Menu's submenu
    ${items}=  getMenuItemsFromPopupMenu  TableWithSingleValue  Submenu
    Should contain  ${items}  Disabled menuitem
    Should contain  ${items}  Enabled menuitem

Get Menu Items From Popup Menu contains no sub items
    ${items}=  getMenuItemsFromPopupMenu  TableWithSingleValue  Replace text
    Should be empty  ${items}

Get Menu Items From Popup Menu's submenu contains no sub items
    ${items}=  getMenuItemsFromPopupMenu  TableWithSingleValue  Submenu|Enabled menuitem
    Should be empty  ${items}

Get Menu Items From Popup Menu fails with non existing menu item
    runKeywordAndExpectError  Wait for "unexisting" subcomponent to be displayed  getMenuItemsFromPopupMenu  unexisting  ${EMPTY}

*** Keywords ***
mainMenuItemNameShouldBe
    [Arguments]  ${menuItemIndex}  ${expectedName}
    ${menuItemName}=  getMainMenuItemName  0
    shouldBeEqual  ${expectedName}  ${menuItemName}

mainMenuItemNamesShouldContain
    [Arguments]  ${expectedName}
    @{menuItemNames}=  getMainMenuItemNames
    shouldContain  ${menuItemNames}  ${expectedName}

menuItemsShouldContain
    [Arguments]  ${path}  ${item}
    @{items}=  getMenuItemNames  ${path}
    shouldContain  ${items}  ${item}
