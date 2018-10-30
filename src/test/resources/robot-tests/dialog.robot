*** Settings ***
Library         TestSwingLibrary

*** Variables ***
${menuRoot}  Test Menu
${dialogMenu}  ${menuRoot}|Show Test Dialog
${dialogMenu2}  ${menuRoot}|Show Non-Modal Dialog
${dialogTitle}  Message
${dialogTitle2}  Non-modal Dialog

*** Test Cases ***
Select Dialog
    [Setup]  openTestDialog
    selectDialog  ${dialogTitle}
    ${context}=  getCurrentContextSourceAsString
    shouldContain  ${context}  Dialog
    [Teardown]  closeTestDialog

Select Dialog Using RegExp
    [Setup]  openTestDialog
    selectDialog  regexp=M.s{2}a.*
    ${context}=  getCurrentContextSourceAsString
    shouldContain  ${context}  Dialog
    [Teardown]  closeTestDialog

Select Dialog Using RegExp Without Prefix Should Fail
    [Setup]  openTestDialog
    selectDialog  regexp=M.s{2}a.*
    ${context}=  getCurrentContextSourceAsString
    shouldContain  ${context}  Dialog
    select main window
    run keyword and expect error  Dialog with name or title 'M.s{2}a.*  selectDialog  M.s{2}a.*
    [Teardown]  closeTestDialog

Close Dialog
    [Setup]  openTestDialog
    closeTestDialog
    dialogShouldNotBeOpen  ${dialogTitle}

Close Dialog With RegExp
    [Setup]  openTestDialog
    closeDialog  regexp=M.s{2}a.*
    dialogShouldNotBeOpen  ${dialogTitle}

Dialog Should Not Be Open
    dialogShouldNotBeOpen  Non existent Dialog

Dialog Should Not Be Open Fails If Dialog Is Open
    [Setup]  openTestDialog
    runKeywordAndExpectError  *Dialog '${dialogTitle}' is open*  dialogShouldNotBeOpen  ${dialogTitle}
    [Teardown]  closeTestDialog

Dialog Should Be Open
    [Setup]  openTestDialog
    dialogShouldBeOpen  ${dialogTitle}
    [Teardown]  closeTestDialog

Dialog Should Be Open Fails If Dialog Is Not Open
    runKeywordAndExpectError  *Dialog '${dialogTitle}' is not open*  dialogShouldBeOpen  ${dialogTitle}

Close All Dialogs When No Dialogs Open
    closeAllDialogs

Close All Dialogs When One Non-Modal, DO_NOTHING_ON_CLOSE Dialog Open
    [Setup]  openNonModalTestDialog
    closeAllDialogs
    nonModalTestDialogShouldBeClosed

Close All Dialogs When One Modal Dialog Open
    [Setup]  openTestDialog
    closeAllDialogs
    testDialogShouldBeClosed

Close All Dialogs When Two Dialogs Open
    [Setup]  openNonModalTestDialog
    openTestDialog
    closeAllDialogs
    testDialogShouldBeClosed
    nonModalTestDialogShouldBeClosed

*** Keywords ***
openTestDialog
    selectFromMainMenu  ${dialogMenu}
    dialogShouldBeOpen  ${dialogTitle}

openNonModalTestDialog
    selectFromMainMenu  ${dialogMenu2}
    dialogShouldBeOpen  ${dialogTitle2}

testDialogShouldBeClosed
    dialogShouldNotBeOpen  ${dialogTitle}

nonModalTestDialogShouldBeClosed
    dialogShouldNotBeOpen  ${dialogTitle2}

closeTestDialog
    closeDialog  ${dialogTitle}

