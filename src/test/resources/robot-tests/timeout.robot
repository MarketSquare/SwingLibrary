*** Settings ***
Library         TestSwingLibrary

*** Variables ***
${tolerance}  3

*** Test Cases ***
Set Jemmy Timeout
    setJemmyTimeout  DialogWaiter.WaitDialogTimeout  1
    ${elapsedTime}=  runKeywordAndMeasureTime  dialogShouldNotBeOpen  Nonexistent Dialog
    elapsedTimeWasLessThanLimit  ${elapsedTime}  ${tolerance}

Set Jemmy Timeout Units
    setJemmyTimeout  DialogWaiter.WaitDialogTimeout  2
    ${prevms}=  setJemmyTimeout  DialogWaiter.WaitDialogTimeout  3000 ms
    shouldBeEqualAsIntegers  ${prevms}  2000
    ${prev}=  setJemmyTimeout  DialogWaiter.WaitDialogTimeout  4
    shouldBeEqualAsIntegers  ${prev}  3

*** Keywords ***
runKeywordAndMeasureTime  [Arguments]  ${keyword}  @{arguments}
    ${startTime}=   getTime  epoch
    runKeyword  ${keyword}  @{arguments}
    ${endTime}=  getTime  epoch
    ${elapsedTime}=  evaluate  ${endTime}-${startTime}
    log  ${elapsedTime}
    [return]  ${elapsedTime}

elapsedTimeWasLessThanLimit  [Arguments]  ${elapsedTime}  ${toleranceLimit}
    ${elapsedWithinLimits}  evaluate    ${elapsedTime}<=${toleranceLimit}
    shouldNotBeEqualAsIntegers  ${elapsedWithinLimits}  0

elapsedTimeIsWithinLimits  [Arguments]  ${elapsedTime}  ${lowerLimit}  ${upperLimit}
    ${elapsedWithinLimits}=  evaluate  ${lowerLimit}<=${elapsedTime}<=${upperLimit}
    shouldNotBeEqualAsIntegers  ${elapsedWithinLimits}  0

