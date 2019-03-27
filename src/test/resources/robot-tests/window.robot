*** Settings ***
Test Setup      selectEmptyContext
Library         TestSwingLibrary

*** Test Cases ***
Select Main Window
    selectMainWindow
    contextIsMainWindow

Select Window By Title
    selectWindow  Test App
    contextIsMainWindow

Select Window By Name
    selectWindow  Main Frame
    contextIsMainWindow

Select Window By Title RegExp
    selectWindow  regexp=T.{2}t.*
    contextIsMainWindow

Select Window By Title Without RegExp Prefix Should Fail
    selectWindow  regexp=T.{2}t.*
    contextIsMainWindow
    selectEmptyContext
    runKeywordAndExpectError  Frame with name or title 'T.{2}t.*'  selectWindow  T.{2}t.*

Select Window By Index
    selectWindow  0
    contextIsMainWindow

Get Selected Window Title
    selectMainWindow
    ${title}=  getSelectedWindowTitle
    shouldBeEqual  ${title}  Test App

List Windows
    selectFromMainMenu  Test Menu|Show Test Window
    Select Window       Test Window
    @{windows} =   List windows
    Should be equal   @{windows}[0]    Test App
    [teardown]   closeWindow  Test Window

Resize Window
    ${initial_size}  Get Window Size  Test App
    Set Window Size  Test App  1280  720
    ${resized_size}  Get Window Size  Test App
    Should Be Equal As Integers  ${resized_size[0]}  1280
    Should Be Equal As Integers  ${resized_size[1]}  720
    [Teardown]  Set Window Size  Test App  ${initial_size[0]}  ${initial_size[1]}

Close Window
    selectFromMainMenu  Test Menu|Show Test Window
    Select Window       Test Window
    closeWindow  Test Window
    Run keyword and expect error    Frame with name or title 'Test Window'     Select Window       Test Window

Close Window With RegExp
    selectFromMainMenu  Test Menu|Show Test Window
    Select Window       Test Window
    closeWindow  regexp=T.{2}t\\sW.*
    Run keyword and expect error    *     Select Window       Test Window


*** Keywords ***
contextIsMainWindow
    ${context}=  getCurrentContextSourceAsString
    shouldContain  ${context}  Test App

