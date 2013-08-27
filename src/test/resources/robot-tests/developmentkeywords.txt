*** Settings ***
Library         TestSwingLibrary
Library         org.robotframework.javalib.library.ClassPathLibrary  org/robotframework/swing/**/*.class

*** Variables ***
${UNFORMATTED LINE}=          org.robotframework.swing.testapp.TestApplication$2
${FORMATTED LINE}=            Level: 0 Component: org.robotframework.swing.testapp.TestApplication$2 Index: 0 Name: Main Frame
${SECOND FORMATTED LINE}=     Level: 5 Component: org.robotframework.swing.testapp.TestTextField Index: 0 Name: testTextField


*** Test Cases ***
List Components In Context
    selectMainWindow
    ${output}=  recordStdout  listComponentsInContext
    shouldContain  ${output}  ${FORMATTED LINE}
    shouldContain  ${output}  ${SECOND FORMATTED LINE}

Return Value Of List Components In Context Without Formatting
    selectMainWindow
    ${output}=  listComponentsInContext
    shouldContain  ${output}  ${UNFORMATTED LINE}
    shouldNotContain  ${output}  ${FORMATTED LINE}

Return Value Of List Components In Context With Formatting
    selectMainWindow
    ${output}=  listComponentsInContext      FORMATTED
    shouldContain  ${output}  ${FORMATTED LINE}
