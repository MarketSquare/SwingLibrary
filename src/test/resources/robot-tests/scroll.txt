*** Settings ***
Library         TestSwingLibrary
Library         Dialogs

*** Test Cases ***
Component should be visible
    Component should be visible        combo_0
Component should not be visible
    Component should not be visible    combo_9
Component scrolling
    [Setup]    Scroll component to view              combo_0
    Component should not be visible    combo_9
    Scroll component to view           combo_9
    Component should be visible        combo_9
    [Teardown]    Scroll component to view           combo_0
