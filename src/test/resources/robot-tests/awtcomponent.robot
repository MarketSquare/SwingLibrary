*** Settings ***
Library         TestSwingLibrary

*** Variables ***
${AWT ERR} =    AWT components are not supported by this keyword.


*** Test Cases ***
Unsupported AWT operation fails
    Run keyword and expect error  ${AWT ERR}   Push button    awt=someButtom

Clear AWT Text Field
    Insert Into Text Field       awt=awtTextField    AWTName
    Clear text field             awt=awtTextField
    ${text}=        Get Text Field Value  awt=awtTextField
    Should be empty  ${text}

Insert Into AWT Text Field With Name
    Clear text field             awt=awtTextField
    Insert Into Text Field       awt=awtTextField    AWTName
    ${text}=        Get Text Field Value  awt=awtTextField
    shouldBeEqual  ${text}   AWTName

Insert Into AWT Text Field With Index
    Clear text field             awt=0
    Insert Into Text Field       awt=0    AWTindex
    ${text}=        Get Text Field Value  awt=awtTextField
    shouldBeEqual  ${text}   AWTindex

Type Into AWT Text Field
    [Tags]  Known Issue Windows
    Clear text field           awt=awtTextField
    Type Into Text Field       awt=awtTextField    AWT Typed
    ${text}=        Get Text Field Value  awt=awtTextField
    shouldBeEqual  ${text}     AWT Typed

AWT Text field should be enabled
    Text Field Should be enabled    awt=awtTextField
    Run keyword and expect error    Textfield 'awt=awtDisabledTextField' is disabled.
    ...                      Text Field Should be enabled    awt=awtDisabledTextField

AWT Text field should be disabled
    Text Field Should be disabled    awt=awtDisabledTextField
    Run keyword and expect error    Textfield 'awt=awtTextField' is enabled.
    ...                      Text Field Should be disabled    awt=awtTextField
