package org.robotframework.swing.security;

import java.security.Permission;

import jdave.Block;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.JemmyException;
import org.netbeans.jemmy.QueueTool;
import org.robotframework.swing.keyword.MockSupportSpecification;

@RunWith(JDaveRunner.class)
public class SystemExitCatcherSpec extends MockSupportSpecification<SystemExitCatcher> {
    public class Any {
        public SystemExitCatcher create() {
            return new SystemExitCatcher();
        }

        public void replacesSystemSecurityManager() {
            context.catchSystemExitsFromAWTThread();
            specify(System.getSecurityManager(), must.equal(context));
        }
    }
    
    public class HandlingNullSecurityManager {
        public SystemExitCatcher create() {
            System.setSecurityManager(null);
            return new SystemExitCatcher();
        }
        
        public void usesNullSystemSecurityManagerIfSystemSecurityManagerIsNull() throws Throwable {
            context.catchSystemExitsFromAWTThread();
            
            specify(new Block() {
                public void run() throws Throwable {
                    System.getSecurityManager().checkPermission(null);
                }
            }, must.not().raise(NullPointerException.class));
        }
    }
    
    public class CheckingPermissions {
        private SecurityManager securityManager;
        private int status = 0;

        public SystemExitCatcher create() {
            securityManager = mock(SecurityManager.class);
            
            return new SystemExitCatcher() {
                SecurityManager getCurrentSecurityManager() {
                    return securityManager;
                }
            };
        }
        
        public void checksPermissionWithCurrentSecurityManager() {
            checking(new Expectations() {{
                one(securityManager).checkPermission(with(any(Permission.class)));
            }});
            
            context.checkPermission(null);
        }
        
        public void passesWhenCalledFromThisThread() throws Throwable {
            checking(new Expectations() {{
                one(securityManager).checkExit(0);
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.checkExit(0);
                }
            }, should.not().raise(Exception.class));
        }
        
        public void passesIfNotInvokedFromRuntimeExit() throws Throwable {
            specify(new Block() {
                public void run() throws Throwable {
                    invokeInAWTThread(new Runnable() {
                        public void run() {
                            context.checkExit(status);    
                        }
                    });
                }
            }, should.not().raise(SecurityException.class)); 
        }

        public void failsWhenCalledFromAWTThreadAndInvokedFromRuntimeExit() throws Throwable {
            final StackTraceMatcher systemExitMatcher = injectMockToContext("systemExitMatcher", StackTraceMatcher.class);
            checking(new Expectations() {{
                one(systemExitMatcher).checkCalled(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    invokeInAWTThread(new Runnable() {
                        public void run() {
                            context.checkExit(status);    
                        }
                    });
                }
            }, should.raiseExactly(SecurityException.class, "System.exit(" + status + ") was prevented"));
        }
    }
    
    private void invokeInAWTThread(Runnable runnable) throws Throwable {
        try {
            new QueueTool().invokeAndWait(runnable);
        } catch (JemmyException e) {
            throw e.getInnerThrowable();
        }
    }
}
