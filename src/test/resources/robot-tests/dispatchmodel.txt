*** Settings ***
Library         TestSwingLibrary
Test teardown     Set jemmy dispatch model     QUEUE_SHORTCUT


*** Test Cases ***
Dispatch model is QUEUE_SHORTCUT by default
    ${old dispatch model} =     Set jemmy dispatch model     ROBOT
    Should be equal     ${old dispatch model}    QUEUE_SHORTCUT

Dispatch model changes
    Set jemmy dispatch model     ROBOT
    ${old dispatch model} =     Set jemmy dispatch model     ROBOT_SMOOTH
    Should be equal     ${old dispatch model}    ROBOT

Unknown dispatch model
    Run keyword and expect error    Unknown Jemmy dispatch model FOO.\nSupported models are *    Set jemmy dispatch model     FOO

All dispatch models
    Set jemmy dispatch model     QUEUE
    Set jemmy dispatch model     QUEUE_SHORTCUT
    Set jemmy dispatch model     ROBOT
    Set jemmy dispatch model     ROBOT_SMOOTH
