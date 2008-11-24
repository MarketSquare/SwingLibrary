package org.robotframework.swing.keyword.textcomponent;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.keyword.textcomponent.TextComponentKeywords;
import org.robotframework.swing.textcomponent.TextComponentOperator;

@RunWith(JDaveRunner.class)
public class TextComponentKeywordsSpec extends MockSupportSpecification<TextComponentKeywords> {
    private TextComponentKeywords textFieldKeywords = new TextComponentKeywords();

    public class Any {
        public void isRobotKeywordsAnnotated() {
            specify(textFieldKeywords, satisfies(new RobotKeywordsContract()));
        }

        public void hasOperatorFactory() throws Throwable {
            specify(textFieldKeywords, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void hasInsertIntoTextFieldKeyword() {
            specify(textFieldKeywords, satisfies(new RobotKeywordContract("insertIntoTextField")));
        }

        public void hasGetTextFieldValueKeyword() {
            specify(textFieldKeywords, satisfies(new RobotKeywordContract("getTextFieldValue")));
        }

        public void hasTypeIntoTextFieldKeyword() {
            specify(textFieldKeywords, satisfies(new RobotKeywordContract("typeIntoTextField")));
        }

        public void hasClearTextFieldKeyword() {
            specify(textFieldKeywords, satisfies(new RobotKeywordContract("clearTextField")));
        }
    }

    public class Operating {
        private OperatorFactory<?> operatorFactory;
        private String textFieldIdentifier = "someTextField";
        private String someText = "someText";
        private TextComponentOperator operator;

        public TextComponentKeywords create() {
            operator = mock(TextComponentOperator.class);
            injectMockOperatorFactoryToTextFieldKeywords();
            return textFieldKeywords;
        }

        public void insertsIntoTextField() {
            checking(new Expectations() {{
                one(operator).setText(someText);
            }});

            context.insertIntoTextField(textFieldIdentifier, someText);
        }

        public void typesIntoTextField() {
            checking(new Expectations() {{
                one(operator).typeText(someText);
            }});

            context.typeIntoTextField(textFieldIdentifier, someText);
        }

        public void clearTextField() {
            checking(new Expectations() {{
                one(operator).clearText();
            }});

            context.clearTextField(textFieldIdentifier);
        }

        public void getsTextFieldValue() {
            checking(new Expectations() {{
                one(operator).getText(); will(returnValue(someText));
            }});

            specify(context.getTextFieldValue(textFieldIdentifier), must.equal(someText));
        }

        private void injectMockOperatorFactoryToTextFieldKeywords() {
            operatorFactory = injectMockTo(textFieldKeywords, OperatorFactory.class);
            checking(new Expectations() {{
                one(operatorFactory).createOperator(textFieldIdentifier);
                will(returnValue(operator));
            }});
        }
    }
}
