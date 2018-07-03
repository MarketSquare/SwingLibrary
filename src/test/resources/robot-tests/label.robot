*** Settings ***
Library         TestSwingLibrary

*** Variables ***
${labelName}  testLabel
${labelIndex}  0
${labelContent}  Test Label

*** Test Cases ***
Label Should Exist By Index
    labelShouldExist  0

Label Should Exist By Name
    labelShouldExist  ${labelName}
    
Label Should Exist By Text
	labelShouldExist  ${labelContent}

Label Should Exist Fails If Label Doesn't Exist
    runKeywordAndExpectError  *Label 'unexistingLabel' doesn't exist*  labelShouldExist  unexistingLabel

Label Should Not Exist By Index
    labelShouldNotExist  199

Label Should Not Exist By Name
    labelShouldNotExist  UnexistingLabel
    
Label Should Not Exist By Text
	labelShouldNotExist  UnexistingLabel

Label Should Not Exist Fails If Label Exists
    runKeywordAndExpectError  *Label '${labelName}' exists*  labelShouldNotExist  ${labelName}

Label Should Not Exist By Text Fails If Label Exists
    runKeywordAndExpectError  *Label '${labelContent}' exists*  labelShouldNotExist  ${labelContent}

Get Label Content By Name
    ${retrievedContent}=  getLabelContent  ${labelName}
    shouldBeEqual  ${labelContent}  ${retrievedContent}

Get Label Content By Index
    ${retrievedContent}=  getLabelContent  ${labelIndex}
    shouldBeEqual  ${labelContent}  ${retrievedContent}

Label text Should Be Passing
    labelTextShouldBe  ${labelName}  ${labelContent}

Label text Should Be Failing
    runKeywordAndExpectError  Expected label 'testLabel' value to be 'INVALID', but was 'Test Label'  labelTextShouldBe  ${labelName}  INVALID

Label Keywords Should fail if context is not a window
    [Setup]  selectEmptyContext
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  labelShouldExist  ${labelName}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  labelShouldNotExist  ${labelName}
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  getLabelContent  ${labelName}
    [Teardown]  selectMainWindow

