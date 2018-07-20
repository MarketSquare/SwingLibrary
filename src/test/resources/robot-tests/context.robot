*** Settings ***
Library         TestSwingLibrary

*** Variables ***
${panelName}  Main Panel
${dialogMenu2}  Test Menu|Show Non-Modal Dialog
${dialogTitle2}  Non-modal Dialog
${textFieldName}  testTextField
${jLayerName}  jLayerComponent

*** Test Cases ***
Select Context
    selectContext  ${panelName}
    ${contextAsString}=  getCurrentContextSourceAsString
    shouldContain  ${contextAsString}  org.robotframework.swing.testapp.PopupPanel
    shouldContain  ${contextAsString}  ${panelName}

Select Invalid Context should fail
    Select And Assert Main Window
    Run keyword and expect error    Invalid context *. Should be one of (java.awt.Window javax.swing.JPanel java.awt.Panel javax.swing.JInternalFrame javax.swing.JLayer )    selectContext    ${textFieldName}

Get Current Context With Panels
    Select And Assert Main Window
    selectContext  ${panelName}
    ${contextName}=  getCurrentContext
    Should Be Equal  ${contextName}  ${panelName}
    Select And Assert Main Window
    selectContext  ${contextName}
    ${contextName}=  getCurrentContext
    Should Be Equal  ${contextName}  ${panelName}

Get Current Context With Window
    select And Assert Main Window
    ${mainWindowTitle}=  getCurrentContext
    Should Be Equal  ${mainWindowTitle}  Test App
    selectContext  ${panelName}
    ${contextName}=  getCurrentContext
    Should Be Equal  ${contextName}  ${panelName}
    selectWindow  ${mainWindowTitle}
    ${title}=  getSelectedWindowTitle
    Should Be Equal  ${title}  Test App

Get Current Context With Dialog
    [Setup]  openNonModalTestDialog
    selectDialog  ${dialogTitle2}
    ${title}=  getCurrentContext
    Should Be Equal  ${dialogTitle2}  ${title}
    [Teardown]  closeDialog  ${dialogTitle2}

Get Current Context With JLayer
    Select And Assert Main Window
    selectContext  ${jLayerName}
    ${contextName}=  getCurrentContext
    Should Be Equal  ${contextName}  ${jLayerName}
    ${contextAsString}=  getCurrentContextSourceAsString
    shouldContain  ${contextAsString}  ${jLayerName}

*** Keywords ***
Select And Assert Main Window
    selectMainWindow
    ${mainFrame}=  getCurrentContext
    Should Be Equal  ${mainFrame}  Test App

openNonModalTestDialog
    selectFromMainMenu  ${dialogMenu2}
    dialogShouldBeOpen  ${dialogTitle2}

