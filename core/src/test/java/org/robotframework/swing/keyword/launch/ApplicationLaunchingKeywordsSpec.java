package org.robotframework.swing.keyword.launch;

import java.util.concurrent.CountDownLatch;

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
    }

    public class Aliases {
        protected boolean wasCalled = false;

        public void startApplicationKeywordIsAnAliasForLaunchApplication() throws Exception {
            final String applicationClass = "someClass";

            new ApplicationLaunchingKeywords() {
                public void launchApplication(String className, String[] args) throws Exception {
                    if (className.equals(applicationClass)) {
                        wasCalled = true;
                    }
                }
            }.startApplication(applicationClass, null);

            specify(wasCalled);
        }
    }
    public class StartingApplications {
        public ApplicationLaunchingKeywords create() {
            return new ApplicationLaunchingKeywords();
        }

        public void launchesApplication() throws Exception {
            String[] args = new String[] { "arg1", "arg2", "arg3" };
            context.launchApplication("org.robotframework.swing.keyword.testapp.SomeApplication", args);
            SomeApplication testApp = new SomeApplication();
            testApp.assertApplicationWasCalled();
            specify(testApp.getReceivedArguments(), containsInOrder("arg1", "arg2", "arg3"));
        }
    }
    
    public class StartingApplicationsInAnotherThread {
        public ApplicationLaunchingKeywords create() {
            return new ApplicationLaunchingKeywords();
        }

        public void startsApplicationInAnotherThread() throws Exception {
            TestApplication.startSignal = new CountDownLatch(1);
            TestApplication.doneSignal = new CountDownLatch(1);
            
            context.startApplicationInSeparateThread("org.robotframework.swing.keyword.launch.ApplicationLaunchingKeywordsSpec$TestApplication", null);
            
            specify(!TestApplication.wasCalled);
            unleashThreadAndWaitForFinish();
            specify(TestApplication.wasCalled);
        }

        private void unleashThreadAndWaitForFinish() throws InterruptedException {
            TestApplication.startSignal.countDown();
            TestApplication.doneSignal.await();
        }
    }

    public class HandlingExceptions {
        private boolean exceptionWasThrown = false;
        private String errorMessage = "error";
        private CountDownLatch doneSignal;

        public ApplicationLaunchingKeywords create() {
            doneSignal = new CountDownLatch(1);
            
            return new ApplicationLaunchingKeywords() {
                public void launchApplication(String className, String[] args) throws Exception {
                    throw new Exception(errorMessage);
                }

                Thread createThread(Runnable runnable) {
                    ThreadGroup exceptionCheckingGroup = new ThreadGroup("test") {
                        public void uncaughtException(Thread t, Throwable e) {
                            if (e.getCause().getMessage().equals(errorMessage)) {
                                exceptionWasThrown = true;
                            }
                            doneSignal.countDown();
                        }
                    };
                    
                    return new Thread(exceptionCheckingGroup, runnable);
                }
            };
        }

        public void rethrowsCaughtExceptionsAsRuntimeExceptions() throws Exception {
            context.startApplicationInSeparateThread("someClass", null);
            doneSignal.await();
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
        static CountDownLatch startSignal;
        static CountDownLatch doneSignal;
        static boolean wasCalled = false;
        public static void main(String[] args) throws InterruptedException {
            startSignal.await();
            wasCalled = true;
            doneSignal.countDown();
        }
    }
}

