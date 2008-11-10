package org.robotframework.swing.keyword.launch;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.keyword.testapp.SomeApplication;

@RunWith(JDaveRunner.class)
public class ApplicationLaunchingKeywordsSpec extends Specification<ApplicationLaunchingKeywords> {
    public class Any {
        public ApplicationLaunchingKeywords create() {
            return new ApplicationLaunchingKeywords();
        }

        public void isRobotKeywordAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasLaunchApplicationKeyword() {
            specify(context, satisfies(new RobotKeywordContract("launchApplication")));
        }

        public void hasStartApplicationKeyword() {
            specify(context, satisfies(new RobotKeywordContract("startApplication")));
        }

        public void hasStartApplicationInSeparateThreadKeyword() {
            specify(context, satisfies(new RobotKeywordContract("startApplicationInSeparateThread")));
        }

        public void launchesApplications() throws Exception {
            String[] args = new String[] { "arg1", "arg2", "arg3" };
            context.launchApplication("org.robotframework.swing.keyword.testapp.SomeApplication", args);
            SomeApplication testApp = new SomeApplication();
            testApp.assertApplicationWasCalled();
            specify(testApp.getReceivedArguments(), containsInOrder(args));
        }
    }

    public class Aliases {
        protected boolean wasCalled = false;

        public void startApplicationKeywordIsAnAliasForLaunchApplication() throws Exception {
            final String applicationClass = "someClass";

            new ApplicationLaunchingKeywords() {
                @Override
                public void launchApplication(String className, String[] args) throws Exception {
                    if (className.equals(applicationClass)) {
                        wasCalled = true;
                    }
                }
            }.startApplication(applicationClass, null);

            specify(wasCalled);
        }
    }

    public class StartingApplicationsInAnotherThread {
        public ApplicationLaunchingKeywords create() {
            return new ApplicationLaunchingKeywords();
        }

        public void startsApplicationInAnotherThread() throws Exception {
            context.startApplicationInSeparateThread("org.robotframework.swing.keyword.launch.ApplicationLaunchingKeywordsSpec$TestApplication", null);
            specifyTestApplicationWasCalledInAnotherThread();
        }

        private void specifyTestApplicationWasCalledInAnotherThread() throws InterruptedException {
            Thread.sleep(10);

            specify(!TestApplication.wasCalled);

            while(!TestApplication.wasCalled) {
                synchronized (TestApplication.class) {
                    TestApplication.class.wait();
                }
            }

            specify(TestApplication.wasCalled);
        }
    }

    public class HandlingExceptions {
        private boolean exceptionWasThrown = false;
        private String errorMessage = "error";

        public ApplicationLaunchingKeywords create() {
            return new ApplicationLaunchingKeywords() {
                @Override
                public void launchApplication(String className, String[] args) throws Exception {
                    throw new Exception(errorMessage);
                }

                @Override
                Thread createThread(Runnable runnable) {
                    return new Thread(new ThreadGroup("test") {
                        public void uncaughtException(Thread t, Throwable e) {
                            synchronized (HandlingExceptions.class) {
                                if (e.getCause().getMessage().equals(errorMessage)) {
                                    exceptionWasThrown = true;
                                }
                                HandlingExceptions.class.notify();
                            }
                        }
                    }, runnable);
                }
            };
        }

        public void rethrowsCaughtExceptionsAsRuntimeExceptions() throws Exception {
            context.startApplicationInSeparateThread("someClass", null);
            synchronized (HandlingExceptions.class) {
                HandlingExceptions.class.wait(10);
            }

            specify(exceptionWasThrown);
        }
    }
    
    public class ReportingErrors {
        private String className = ApplicationLaunchingKeywordsSpec.class.getName() + "$ClassWithoutMainMethod";
        public ApplicationLaunchingKeywords create() {
            return new ApplicationLaunchingKeywords();
        }
        
        public void failsWithNiceErrorMessageWhenClassHasNoMainMethod() {
            specify(new Block() {
                public void run() throws Throwable {
                    context.launchApplication(className, null);
                }
            }, must.raiseExactly(RuntimeException.class, "Class '" + className + "' doesn't have a main method."));
        }
    }

    @SuppressWarnings("unused")
    private static class ClassWithoutMainMethod {    
    }
    
    private static class TestApplication {
        public static boolean wasCalled = false;
        public static void main(String[] args) throws InterruptedException {
            Thread.sleep(150);

            wasCalled = true;

            synchronized (TestApplication.class) {
                TestApplication.class.notifyAll();
            }
        }
    }
}

