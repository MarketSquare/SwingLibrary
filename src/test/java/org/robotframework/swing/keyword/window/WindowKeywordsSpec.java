package org.robotframework.swing.keyword.window;

import java.awt.Window;

import javax.swing.JFrame;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.window.FrameOperator;


@RunWith(JDaveRunner.class)
public class WindowKeywordsSpec extends MockSupportSpecification<WindowKeywords> {
    private FrameOperator frameOperator = mock(FrameOperator.class);

    public class Any {
        public WindowKeywords create() {
            return new WindowKeywords();
        }

        public void isRobotKeywordAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasSelectMainWindowKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectMainWindow")));
        }

        public void hasSelectWindowKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectWindow")));
        }

        public void hasGetSelectedWindowTitleKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getSelectedWindowTitle")));
        }
    }

    public class SelectingMainWindow {
        public WindowKeywords create() {
            return new WindowKeywords();
        }

        public void selectMainWindowSetsMainWindowAsContext() {
            final IdentifierParsingOperatorFactory<?> operatorFactory = injectMockToContext("operatorFactory", IdentifierParsingOperatorFactory.class);
            checking(new Expectations() {{
                one(operatorFactory).createOperatorByIndex(0);
                will(returnValue(frameOperator));
            }});

            context.selectMainWindow();
            specify(Context.getContext(), must.equal(frameOperator));
        }
    }

    public class OperatingOnWindows {
        private IdentifierParsingOperatorFactory<?> operatorFactory;
        private String windowIdentifier = "title";

        public WindowKeywords create() {
            WindowKeywords windowKeywords = new WindowKeywords();
            injectMockOperatorFactoryTo(windowKeywords);
            return windowKeywords;
        }

        public void destroy() {
            Context.setContext(null);
        }

        public void selectWindowSetsDesignatedWindowAsContext() {
            context.selectWindow(windowIdentifier);
            specify(Context.getContext(), must.equal(frameOperator));
        }

        public void closesWindow() {
            checking(new Expectations() {{
                one(frameOperator).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                one(frameOperator).close();
            }});

            context.closeWindow(windowIdentifier);
        }

        private void injectMockOperatorFactoryTo(WindowKeywords windowKeywords) {
            operatorFactory = injectMockTo(windowKeywords, "operatorFactory", IdentifierParsingOperatorFactory.class);
            checking(new Expectations() {{
                one(operatorFactory).createOperator(windowIdentifier);
                will(returnValue(frameOperator));
            }});
        }
    }

    public class GettingTitle {
        private boolean verifyContextWasCalled;

        public WindowKeywords create() {
            verifyContextWasCalled = false;
            return new WindowKeywords() {
                public void verifyContext() {
                    verifyContextWasCalled = true;
                }
            };
        }

        public void getsSelectedWindowTitle() {
            Context.setContext(frameOperator);
            final String title = "Some Title";
            checking(new Expectations() {{
                one(frameOperator).getTitle(); will(returnValue(title));
            }});

            specify(context.getSelectedWindowTitle(), title);
            specify(verifyContextWasCalled);
        }
    }

    public class VerifyingContext {
        public WindowKeywords create() {
            return new WindowKeywords();
        }

        public void verifiesWindowContext() {
            specify(context.getExpectedClasses(), containsExactly(Window.class));
        }
    }
}
