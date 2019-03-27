*** Settings ***
Library         TestSwingLibrary
Library         Collections
Variables       platform_info.py

*** Variables ***
${textAreaName}  testTextArea
${textFieldName}  testTextField
${componentName}  Main Panel
${buttonName}  testButton

*** Test Cases ***
Sets Focus
    [Tags]  display-required
    clearTextField  ${textFieldName}
    clearTextField  ${textAreaName}
    typeIntoTextfield  ${textFieldName}  copyPaste
    selectTextFieldContents  ${textFieldName}
    sendKeyboardEvent  VK_C  ${CTRL CMD}
    focusToComponent  ${textAreaName}
    sendKeyboardEvent  VK_V  ${CTRL CMD}
    ${textFieldContents}=  getTextFieldValue  ${textAreaName}
    shouldBeEqual  copyPaste  ${textFieldContents}

Component Should Exist By Index
    componentShouldExist  0

Component Should Exist By Name
    componentShouldExist  ${componentName}

Component Should Exist Fails If The Component Doesn't Exist
    runKeywordAndExpectError  *Component 'Unexisting component' does not exist*  componentShouldExist  Unexisting component
    runKeywordAndExpectError  *Component '9999' does not exist*  componentShouldExist  9999

Component Should Not Exist By Index
    componentShouldNotExist  9999

Component Should Not Exist By Name
    componentShouldNotExist  Unexisting component

Component Should Not Exist Fails If The Component Exists
    runKeywordAndExpectError  *Component '${componentName}' exists*  componentShouldNotExist  ${componentName}
    runKeywordAndExpectError  *Component '0' exists*  componentShouldNotExist  0

Click On Component
    [Setup]  resetButton
    clickOnComponent  testButton
    ${text}=  getButtonText  ${buttonName}
    shouldBeEqual  Button Was Pushed1  ${text}

Doubleclick On Component
    [Setup]  resetButton
    doubleClickOnComponent  testButton
    ${text}=  getButtonText  ${buttonName}
    shouldBeEqual  Button Was Pushed2  ${text}

Click On Component With Shift
    [Setup]  resetButton
    clickOnComponent  testButton  1  LEFT BUTTON  SHIFT
    ${text}=  getButtonText  ${buttonName}
    shouldBeEqual    Click With Shift    ${text}

Right Click on Component
    [Setup]  resetButton
    rightClickOnComponent    testButton
    ${text}=  getButtonText  ${buttonName}
    shouldBeEqual    Right Clicked    ${text}

Get Tooltip Text
    ${tooltip}=  getToolTipText  testLabel
    shouldBeEqual  TEST LABEL  ${tooltip}

List Component Methods
    ${methods}=  listComponentMethods  ${buttonName}
    listShouldContainValue  ${methods}  java.lang.String getToolTipText()

Call Component Method
   ${tooltipText}=  setVariable  tooltip test
   callComponentMethod  ${buttonName}  setToolTipText  ${tooltipText}
   ${tooltipValue}=  callComponentMethod  ${buttonName}  getToolTipText
   Should Be Equal  ${tooltipText}  ${tooltipValue}

*** Keywords ***
resetButton
    setButtonText  ${buttonName}  Test Button

doubleClickOnComponent
    [Arguments]  ${identifier}
    clickOnComponent  ${identifier}  2

