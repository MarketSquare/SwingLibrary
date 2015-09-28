*** Settings ***
Suite Setup     launchApplicationAndSetTimeouts
Suite teardown  closeWindow  Test App
Library         TestSwingLibrary
Force Tags      regression

*** Keywords ***
launchApplicationAndSetTimeouts
    setSystemProperty    testApp.secret    Test Text Field
    startApplication  org.robotframework.swing.testapp.TestApplication
    selectMainWindow
    setJemmyTimeouts  1


