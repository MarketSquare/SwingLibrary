package org.robotframework.swing.keyword.radiobutton;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.swing.button.AbstractButtonOperator;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;

@RunWith(JDaveRunner.class)
public class RadioButtonKeywordsSpec extends MockSupportSpecification<RadioButtonKeywords> {
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
        
        public void hasPushRadioButtonKeyword() {
            specify(context, satisfies(new RobotKeywordContract("pushRadioButton")));
        }
        
        public void hasRadioButtonShouldBeSelectedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("radioButtonShouldBeSelected")));
        }
        
        public void hasRadioButtonShouldNotBeSelectedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("radioButtonShouldNotBeSelected")));
        }
        
        public void hasRadioButtonShouldBeEnabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("radioButtonShouldBeEnabled")));
        }
        
        public void hasRadioButtonShouldBeDisabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("radioButtonShouldBeDisabled")));
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
        
        public void pushesRadioButton() {
            checking(new Expectations() {{
                one(operator).push();
            }});
            
            context.pushRadioButton(identifier);
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
        
        public void radioButtonShouldBeEnabedPassesWhenEnabled() throws Throwable {
            checking(new Expectations() {{
                one(operator).isEnabled(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.radioButtonShouldBeEnabled(identifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void radioButtonShouldBeDisabledPassesWhenDisabled() throws Throwable {
            checking(new Expectations() {{
                one(operator).isEnabled(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.radioButtonShouldBeDisabled(identifier);
                }
            }, must.not().raise(Exception.class));
        }
        
        public void radioButtonShouldBeEnabledFailsWhenDisabled() throws Throwable {
            checking(new Expectations() {{
                one(operator).isEnabled(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.radioButtonShouldBeEnabled(identifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Radio Button '" + identifier + "' is disabled."));
        }
        
        public void radioButtonShouldBeDisabledFailsWhenEnabled() throws Throwable {
            checking(new Expectations() {{
                one(operator).isEnabled(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.radioButtonShouldBeDisabled(identifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Radio Button '" + identifier + "' is enabled."));
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
