package org.robotframework.swing.keyword.radiobutton;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.button.AbstractButtonOperator;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.KeywordSupportSpecification;

@RunWith(JDaveRunner.class)
public class RadioButtonKeywordsSpec extends KeywordSupportSpecification<RadioButtonKeywords> {
    public class Any {
        public RadioButtonKeywords create() {
            return new RadioButtonKeywords();
        }
        
        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }
        
        public void hasSelectRadioButtonKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectRadioButton")));
        }
        
        public void hasRadioButtonShouldBeSelectedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("radioButtonShouldBeSelected")));
        }
        
        public void hasRadioButtonShouldNotBeSelectedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("radioButtonShouldNotBeSelected")));
        }
    }
    
    public class OperatingOnRadioButtons {
        private String identifier = "someRadioButton";
        private AbstractButtonOperator operator;
        
        public RadioButtonKeywords create() {
            RadioButtonKeywords radioButtonKeywords = new RadioButtonKeywords();
            injectMockOperatingFactory(radioButtonKeywords);
            return radioButtonKeywords;
        }
        
        public void selectsRadioButton() {
            checking(new Expectations() {{
                one(operator).push();
            }});
            
            context.selectRadioButton(identifier);
        }

        public void radioButtonShouldBeSelectedPassesWhenSelected() throws Throwable {
            checking(new Expectations() {{
                one(operator).isSelected(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.radioButtonShouldBeSelected(identifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void radioButtonShouldNotBeSelectedPassesWhenNotSelected() throws Throwable {
            checking(new Expectations() {{
                one(operator).isSelected(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.radioButtonShouldNotBeSelected(identifier);
                }
            }, must.not().raise(Exception.class));
        }
        
        public void radioButtonShouldBeSelectedFailsWhenNotSelected() throws Throwable {
            checking(new Expectations() {{
                one(operator).isSelected(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.radioButtonShouldBeSelected(identifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Radio Button '" + identifier + "' is not selected."));
        }
        
        public void radioButtonShouldNotBeSelectedFailsWhenSelected() throws Throwable {
            checking(new Expectations() {{
                one(operator).isSelected(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.radioButtonShouldNotBeSelected(identifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Radio Button '" + identifier + "' is selected."));
        }

        private void injectMockOperatingFactory(RadioButtonKeywords radioButtonKeywords) {
            operator = mock(AbstractButtonOperator.class);
            final OperatorFactory operatorFactory = injectMockTo(radioButtonKeywords, OperatorFactory.class);
            checking(new Expectations() {{
                one(operatorFactory).createOperator(identifier);
                will(returnValue(operator));
            }});
        }
    }
}
