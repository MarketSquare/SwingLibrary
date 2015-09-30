*** Settings ***
Suite Setup     setTimeouts
Library         TestSwingLibrary

*** Variables ***
${sliderName}  testSlider

*** Test Cases ***
Slider Should Exist By Name
    sliderShouldExist  ${sliderName}
    runkeywordAndExpectError  Slider 'nonexisting' doesn't exist.  sliderShouldExist  nonexisting

Slider Should Not Exist By Name
    sliderShouldNotExist  nonexisting
    runkeywordAndExpectError  Slider '${sliderName}' exists.  sliderShouldNotExist  ${sliderName}

Slider Should Exist By Index
    sliderShouldExist  0
    runkeywordAndExpectError  Slider '666' doesn't exist.  sliderShouldExist  666

Slider Should Not Exist By Index
    sliderShouldNotExist  666
    runkeywordAndExpectError  Slider '0' exists.  sliderShouldNotExist  0

Get Slider Value By Name
    ${sliderValue}=  Get Slider Value  ${sliderName}
    shouldBeEqualAsIntegers  2  ${sliderValue}

Get Slider Value By Index
    ${sliderValue}=  Get Slider Value  0
    shouldBeEqualAsIntegers  2  ${sliderValue}

Set Slider Value By Name
    ${oldValue}=  Get Slider Value  ${sliderName}
    setSliderValue  ${sliderName}  4
    ${sliderValue}=  Get Slider Value  ${sliderName}
    shouldBeEqualAsIntegers  4  ${sliderValue}
    [teardown]  setSliderValue  ${sliderName}  ${oldValue}

Set Slider Value By Index
    ${oldValue}=  Get Slider Value  0
    setSliderValue  0  4
    ${sliderValue}=  Get Slider Value  0
    shouldBeEqualAsIntegers  4  ${sliderValue}
    [teardown]  setSliderValue  0  ${oldValue}

Slider Keywords Should Fail Context Is Not Correct
    selectEmptyContext
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  sliderShouldExist  someSlider
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  sliderShouldNotExist  someSlider
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  getSliderValue  deeSlider
    runKeywordAndExpectError  *To use this keyword you must first select a correct context*  setSliderValue  deeSlider  1
    [Teardown]  selectMainWindow

*** Keywords ***
setTimeouts
    setJemmyTimeouts  1
