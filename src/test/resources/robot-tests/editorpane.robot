*** Settings ***
Library         TestSwingLibrary

*** Variables ***
${editorPaneName}  testEditorPane
${linkText}  Network Elements
${expectedEventDescription}  DeviceName

*** Test Cases ***
Click Hyper Link
    runKeywordAndExpectError  Link was not clicked  linkShouldHaveBeenClicked
    clickHyperLink  testEditorPane  Network Elements
    linkShouldHaveBeenClicked
    hyperLinkEventDescriptionShouldBe  ${expectedEventDescription}

