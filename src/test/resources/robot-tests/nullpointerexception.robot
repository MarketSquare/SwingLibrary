*** Settings ***
Suite Setup     captureOriginalButtonText
Test Setup      resetButton
Library         TestSwingLibrary

*** Variables ***
${buttonName}  testButton
${buttonText}  Test NullPointerException
${toolTipText}  testToolTip
${buttonTextAfterPush}  Button Was Pushed1
${buttonIndex}  0

*** Test Cases ***
Report For NullPointerException
    Run Keyword And Expect Error  *  pushButton  ${buttonText}
    # buttonWasPushed  ${buttonName}

*** Keywords ***
buttonWasPushed
    [Arguments]  ${buttonIdentifier}
    ${buttonText}=  getButtonText  ${buttonIdentifier}
    shouldBeEqual  ${buttonText}  ${buttonTextAfterPush}

captureOriginalButtonText
    ${text}=  getButtonText  ${buttonName}
    setSuiteVariable  \${originalButtonText}  ${text}

resetButton
    setButtonText  ${buttonName}  ${originalButtonText}
    enableButton  ${buttonName}

