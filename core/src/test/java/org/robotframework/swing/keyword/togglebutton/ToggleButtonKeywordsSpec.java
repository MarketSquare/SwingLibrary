package org.robotframework.swing.keyword.togglebutton;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.swing.button.AbstractButtonOperator;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.jdave.mock.MockSupportSpecification;

@RunWith(JDaveRunner.class)
public class ToggleButtonKeywordsSpec extends MockSupportSpecification<ToggleButtonKeywords> {
    public class Any {
        public ToggleButtonKeywords create() {
            return new ToggleButtonKeywords();
        }
        
        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }
        
        public void hasToggleButtonShouldBeSelectedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("toggleButtonShouldBeSelected")));
        }
        
        public void hasSelectToggleButtonKeyword() {
            specify(context, satisfies(new RobotKeywordContract("pushToggleButton")));
        }
    }
    
    public class OperatingOnToggleButtons {
        private String identifier = "someToggleButton";
        private AbstractButtonOperator operator;
        
        public ToggleButtonKeywords create() {
            ToggleButtonKeywords toggleButtonKeywords = new ToggleButtonKeywords();
            injectMockOperatingFactory(toggleButtonKeywords);
            return toggleButtonKeywords;
        }
        
        public void toggleButtonShouldBeSelectedPassesWhenSelected() throws Throwable {
            checking(new Expectations() {{
                one(operator).isSelected(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.toggleButtonShouldBeSelected(identifier);
                }
            }, must.not().raise(Exception.class));
        }

        public void toggleButtonShouldNotBeSelectedPassesWhenNotSelected() throws Throwable {
            checking(new Expectations() {{
                one(operator).isSelected(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.toggleButtonShouldNotBeSelected(identifier);
                }
            }, must.not().raise(Exception.class));
        }
        
        public void toggleButtonShouldBeSelectedFailsWhenNotSelected() throws Throwable {
            checking(new Expectations() {{
                one(operator).isSelected(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.toggleButtonShouldBeSelected(identifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Toggle Button '" + identifier + "' is not selected."));
        }
        
        public void toggleButtonShouldNotBeSelectedFailsWhenSelected() throws Throwable {
            checking(new Expectations() {{
                one(operator).isSelected(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.toggleButtonShouldNotBeSelected(identifier);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Toggle Button '" + identifier + "' is selected."));
        }

        public void pushesToggleButton() {
            checking(new Expectations() {{
                one(operator).push();
            }});
            
            context.pushToggleButton(identifier);
        }
        
        private void injectMockOperatingFactory(ToggleButtonKeywords toggleButtonKeywords) {
            operator = mock(AbstractButtonOperator.class);
            final OperatorFactory operatorFactory = injectMockTo(toggleButtonKeywords, OperatorFactory.class);
            checking(new Expectations() {{
                one(operatorFactory).createOperator(identifier);
                will(returnValue(operator));
            }});
        }
    }
}
