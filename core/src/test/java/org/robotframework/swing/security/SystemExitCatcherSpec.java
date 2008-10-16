package org.robotframework.swing.security;

import java.io.FileDescriptor;
import java.net.InetAddress;
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
    
    public class DelegatingCalls {
        private SecurityManager securityManager;
        
        public SystemExitCatcher create() {
            SystemExitCatcher systemExitCatcher = new SystemExitCatcher();
            securityManager = injectMockTo(systemExitCatcher, "currentSecurityManager", SecurityManager.class);
            return systemExitCatcher;
        }
        

        public void delegatesCheckExit() {
            final int status = 0;
            checking(new Expectations() {{
                one(securityManager).checkExit(status); 
            }});
            context.checkExit(status);
        }

        public void delegatesCheckPermission() {
            final Permission perm = dummy(Permission.class);
            checking(new Expectations() {{
                one(securityManager).checkPermission(perm); 
            }});
            context.checkPermission(perm);
        }

        public void delegatesCheckAccept() {
            final int port = 0;
            final String host = "";
            checking(new Expectations() {{
                one(securityManager).checkAccept(host, port); 
            }});
            context.checkAccept(host, port);
        }

        public void delegatesCheckAccess() {
            final Thread t = dummy(Thread.class);
            checking(new Expectations() {{
                one(securityManager).checkAccess(t); 
            }});
            context.checkAccess(t);
        }

        public void delegatesCheckAccess2() {
            final ThreadGroup g = dummy(ThreadGroup.class);
            checking(new Expectations() {{
                one(securityManager).checkAccess(g); 
            }});
            context.checkAccess(g);
        }

        public void delegatesCheckAwtEventQueueAccess() {
            checking(new Expectations() {{
                one(securityManager).checkAwtEventQueueAccess(); 
            }});
            context.checkAwtEventQueueAccess();
        }

        public void delegatesCheckConnect() {
            final int port = 0;
            final Object ctx = dummy(Object.class);
            final String host = "";
            checking(new Expectations() {{
                one(securityManager).checkConnect(host, port, ctx); 
            }});
            context.checkConnect(host, port, ctx);
        }

        public void delegatesCheckConnect2() {
            final int port = 0;
            final String host = "";
            checking(new Expectations() {{
                one(securityManager).checkConnect(host, port); 
            }});
            context.checkConnect(host, port);
        }

        public void delegatesCheckCreateClassLoader() {
            checking(new Expectations() {{
                one(securityManager).checkCreateClassLoader(); 
            }});
            context.checkCreateClassLoader();
        }

        public void delegatesCheckDelete() {
            final String file = "";
            checking(new Expectations() {{
                one(securityManager).checkDelete(file); 
            }});
            context.checkDelete(file);
        }

        public void delegatesCheckExec() {
            final String cmd = "";
            checking(new Expectations() {{
                one(securityManager).checkExec(cmd); 
            }});
            context.checkExec(cmd);
        }

        public void delegatesCheckLink() {
            final String lib = "";
            checking(new Expectations() {{
                one(securityManager).checkLink(lib); 
            }});
            context.checkLink(lib);
        }

        public void delegatesCheckListen() {
            final int port = 0;
            checking(new Expectations() {{
                one(securityManager).checkListen(port); 
            }});
            context.checkListen(port);
        }

        public void delegatesCheckMemberAccess() {
            final int which = 0;
            final Class<?> clazz = this.getClass();
            checking(new Expectations() {{
                one(securityManager).checkMemberAccess(clazz, which); 
            }});
            context.checkMemberAccess(clazz, which);
        }

        public void delegatesCheckMulticast() {
            final InetAddress maddr = dummy(InetAddress.class);
            final byte ttl = 0;
            checking(new Expectations() {{
                one(securityManager).checkMulticast(maddr, ttl); 
            }});
            context.checkMulticast(maddr, ttl);
        }

        public void delegatesCheckMulticast2() {
            final InetAddress maddr = dummy(InetAddress.class);
            checking(new Expectations() {{
                one(securityManager).checkMulticast(maddr); 
            }});
            context.checkMulticast(maddr);
        }

        public void delegatesCheckPackageAccess() {
            final String pkg = "";
            checking(new Expectations() {{
                one(securityManager).checkPackageAccess(pkg); 
            }});
            context.checkPackageAccess(pkg);
        }

        public void delegatesCheckPackageDefinition() {
            final String pkg = "";
            checking(new Expectations() {{
                one(securityManager).checkPackageDefinition(pkg); 
            }});
            context.checkPackageDefinition(pkg);
        }

        public void delegatesCheckPermission2() {
            final Permission perm = dummy(Permission.class);
            final Object ctx = dummy(Object.class);
            checking(new Expectations() {{
                one(securityManager).checkPermission(perm, ctx); 
            }});
            context.checkPermission(perm, ctx);
        }

        public void delegatesCheckPrintJobAccess() {
            checking(new Expectations() {{
                one(securityManager).checkPrintJobAccess(); 
            }});
            context.checkPrintJobAccess();
        }

        public void delegatesCheckPropertiesAccess() {
            checking(new Expectations() {{
                one(securityManager).checkPropertiesAccess(); 
            }});
            context.checkPropertiesAccess();
        }

        public void delegatesCheckPropertyAccess() {
            final String key = "";
            checking(new Expectations() {{
                one(securityManager).checkPropertyAccess(key); 
            }});
            context.checkPropertyAccess(key);
        }

        public void delegatesCheckRead() {
            final FileDescriptor fd = new FileDescriptor();
            checking(new Expectations() {{
                one(securityManager).checkRead(fd); 
            }});
            context.checkRead(fd);
        }

        public void delegatesCheckRead2() {
            final Object ctx = dummy(Object.class);
            final String file = "";
            checking(new Expectations() {{
                one(securityManager).checkRead(file, ctx); 
            }});
            context.checkRead(file, ctx);
        }

        public void delegatesCheckRead23() {
            final String file = "";
            checking(new Expectations() {{
                one(securityManager).checkRead(file); 
            }});
            context.checkRead(file);
        }

        public void delegatesCheckSecurityAccess() {
            final String target = "";
            checking(new Expectations() {{
                one(securityManager).checkSecurityAccess(target); 
            }});
            context.checkSecurityAccess(target);
        }

        public void delegatesCheckSetFactory() {
            checking(new Expectations() {{
                one(securityManager).checkSetFactory(); 
            }});
            context.checkSetFactory();
        }

        public void delegatesCheckSystemClipboardAccess() {
            checking(new Expectations() {{
                one(securityManager).checkSystemClipboardAccess(); 
            }});
            context.checkSystemClipboardAccess();
        }

        public void delegatesCheckTopLevelWindow() {
            final Object window = dummy(Object.class);
            final boolean expectedBoolean = false;
            checking(new Expectations() {{
                one(securityManager).checkTopLevelWindow(window); will(returnValue(expectedBoolean));
            }});
            specify(context.checkTopLevelWindow(window), must.equal(expectedBoolean));
        }

        public void delegatesCheckWrite() {
            final FileDescriptor fd = new FileDescriptor();
            checking(new Expectations() {{
                one(securityManager).checkWrite(fd); 
            }});
            context.checkWrite(fd);
        }

        public void delegatesCheckWrite2() {
            final String file = "";
            checking(new Expectations() {{
                one(securityManager).checkWrite(file); 
            }});
            context.checkWrite(file);
        }

        public void delegatesGetInCheck() {
            final boolean expectedBoolean = false;
            checking(new Expectations() {{
                one(securityManager).getInCheck(); will(returnValue(expectedBoolean));
            }});
            specify(context.getInCheck(), must.equal(expectedBoolean));
        }

        public void delegatesGetSecurityContext() {
            final Object expectedObject = dummy(Object.class);
            checking(new Expectations() {{
                one(securityManager).getSecurityContext(); will(returnValue(expectedObject));
            }});
            specify(context.getSecurityContext(), must.equal(expectedObject));
        }

        public void delegatesGetThreadGroup() {
            final ThreadGroup expectedThreadGroup = dummy(ThreadGroup.class);
            checking(new Expectations() {{
                one(securityManager).getThreadGroup(); will(returnValue(expectedThreadGroup));
            }});
            specify(context.getThreadGroup(), must.equal(expectedThreadGroup));
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
