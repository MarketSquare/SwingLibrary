package org.robotframework.swing.security;

import java.security.Permission;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;

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
    
    public class CheckingPermission {
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
    }
}
