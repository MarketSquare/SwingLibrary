package org.robotframework.swing.security;

import java.security.Permission;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.JemmyException;
import org.netbeans.jemmy.QueueTool;

@RunWith(JDaveRunner.class)
public class SystemExitCatcherSpec extends Specification<SystemExitCatcher> {
    public class Any {
        public SystemExitCatcher create() {
            return new SystemExitCatcher();
        }

        public void replacesSystemSecurityManager() {
            context.replaceSystemSecurityManager();
            specify(System.getSecurityManager(), must.equal(context));
        }
    }
    
    public class HandlingNullSecurityManager {
        public SystemExitCatcher create() {
            System.setSecurityManager(null);
            return new SystemExitCatcher();
        }
        
        public void usesNullSystemSecurityManagerIfSystemSecurityManagerIsNull() throws Throwable {
            context.replaceSystemSecurityManager();
            
            specify(new Block() {
                public void run() throws Throwable {
                    System.getSecurityManager().checkPermission(null);
                }
            }, must.not().raise(NullPointerException.class));
        }
    }
    
    public class CheckingPermissions {
        private SecurityManager securityManager;

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
        
        public void checksExitWithCurrentSecurityManagerWhenCalledFromThisThread() {
            checking(new Expectations() {{
                one(securityManager).checkExit(0);
            }});
            
            context.checkExit(0);
        }
        
        public void throwsSecurityExceptionWhenCalledFromAWTThread() throws Throwable {
            final int status = 0;
            specify(new Block() {
                public void run() throws Throwable {
                    new AWTThreadInvoker() {
                        public void run() {
                            context.checkExit(status);
                        }
                    }.invokeInAWTThread();
                }
            }, should.raiseExactly(SecurityException.class, "System.exit(" + status + ") was prevented")); 
        }
    }
    
    private abstract static class AWTThreadInvoker implements Runnable {
        public void invokeInAWTThread() throws Throwable {
            try {
                new QueueTool().invokeAndWait(this);
            } catch (JemmyException e) {
                throw e.getInnerThrowable();
            }
        }
        
        public abstract void run();
    }
}
