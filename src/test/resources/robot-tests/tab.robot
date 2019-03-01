*** Settings ***
Test Setup      selectMainWindow
Library         TestSwingLibrary

*** Variables ***
${firstTabPane}  testTabbedPane1
${secondTabPane}  testTabbedPane2
${textFieldName}  tabButtonOutputTextField

*** Test Cases ***
Select Tab Pane
    selectTabPane  ${firstTabPane}
    tabPaneShouldBeSelected  ${firstTabPane}
    selectMainWindow
    selectTabPane  ${secondTabPane}
    tabPaneShouldBeSelected  ${secondTabPane}

Get Selected Tab Label
    selectTabAsContext  tab2
    tabShouldBeSelected  ${firstTabPane}  2
    ${selectedTabLabel}=  getSelectedTabLabel
    shouldBeEqual  tab2  ${selectedTabLabel}

Select Tab By Name
    selectTabPane  ${firstTabPane}
    selectTabAsContext  tab3
    tabShouldBeSelected  ${firstTabPane}  3
    selectMainWindow
    selectTabAsContext  tab3  ${secondTabPane}
    tabShouldBeSelected  ${secondTabPane}  3

Select Tab By Index
    selectTabAsContext  2
    tabShouldBeSelected  ${firstTabPane}  3
    selectMainWindow
    selectTabAsContext  2  ${secondTabPane}
    tabShouldBeSelected  ${secondTabPane}  3

Get Selected Tab Label By Name
    selectTab  tab2
    ${selectedTabLabel}=  getSelectedTabLabel
    shouldBeEqual  tab2  ${selectedTabLabel}
    selectTab  tab2  ${secondTabPane}
    ${selectedTabLabel}=  getSelectedTabLabel
    shouldBeEqual  tab2  ${selectedTabLabel}

Get Selected Tab Label By Index
    selectTab  1
    ${selectedTabLabel}=  getSelectedTabLabel
    shouldBeEqual  tab2  ${selectedTabLabel}
    selectTab  1  ${secondTabPane}
    ${selectedTabLabel}=  getSelectedTabLabel
    shouldBeEqual  tab2  ${selectedTabLabel}

Tab Keywords Should Fail Context Is Not Correct
    selectEmptyContext
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  selectTab  tab1
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  getSelectedTabLabel
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  selectTabPane  0
    [Teardown]  selectMainWindow

Select Tab With Regexp
    selectTabPane  regexp=.*TabbedPane1
    tabPaneShouldBeSelected  ${firstTabPane}
    selectTabAsContext  regexp=.*ab3
    tabShouldBeSelected  ${firstTabPane}  3
    selectTab  regexp=t.?b2
    ${selectedTabLabel}=  getSelectedTabLabel
    shouldBeEqual  tab2  ${selectedTabLabel}

*** Keywords ***
tabShouldBeSelected_old
    [Arguments]  ${expectedTab}
    ${selectedTabLabel}=  getSelectedTabLabel
    shouldBeEqual  ${expectedTab}  ${selectedTabLabel}

tabPaneShouldBeSelected
    [Arguments]  ${tabPaneIdentifier}
    ${context}=  getCurrentContextSourceAsString
    log  ${context}
    shouldContain  ${context}  ${tabPaneIdentifier}

tabShouldBeSelected
    [Arguments]  ${pane}  ${buttonNum}
    List Components in context
    pushButton  ButtonInTab
    selectMainWindow
    ${textFieldContents}=  getTextFieldValue  ${textFieldName}
    shouldBeEqual  ${pane}.button${buttonNum}  ${textFieldContents}

