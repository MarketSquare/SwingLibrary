*** Settings ***
Library         TestSwingLibrary

*** Variables ***
${testApplicationName}  org.robotframework.swing.testapp.keyword.testapp.SomeApplication
${classWithoutMainMethod}  org.robotframework.swing.keyword.launch.ApplicationLaunchingKeywords

*** Test Cases ***
Launch Application
    runKeywordAndExpectError  Application was not called  assertApplicationWasCalled
    launchApplication  ${testApplicationName}
    assertApplicationWasCalled

Launch Application Gives Descriptive Error Message If Main Method Is Not Found
    runKeywordAndExpectError  Class '${classWithoutMainMethod}' doesn't have a main method.  launchApplication  ${classWithoutMainMethod}

*** Keywords ***
shouldNotHaveBeenCalled
    ${callStatus}=  getTestAppCallStatus
    shouldBeEqual  false  ${callStatus}

