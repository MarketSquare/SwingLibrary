/*
 * Copyright 2008 Nokia Siemens Networks Oyj
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.robotframework.swing.security;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

import org.netbeans.jemmy.QueueTool;

public class SystemExitCatcher extends SecurityManager {
    private final StackTraceMatcher systemExitMatcher = new StackTraceMatcher("java.lang.Runtime", "exit");
    private final SecurityManager currentSecurityManager;

    public SystemExitCatcher() {
        currentSecurityManager = getCurrentSecurityManager();
    }

    public void catchSystemExitsFromAWTThread() {
        System.setSecurityManager(this);
    }

    @Override
    public void checkExit(int status) {
        if (isAWTThread() && isCalledFromRuntimeExit()) {
            throw new SecurityException("System.exit(" + status + ") was prevented");
        } else {
            currentSecurityManager.checkExit(status);
        }
    }

    private boolean isCalledFromRuntimeExit() {
        return systemExitMatcher.checkCalled();
    }

    private boolean isAWTThread() {
        return QueueTool.isDispatchThread();
    }

    SecurityManager getCurrentSecurityManager() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null)
            return securityManager;

        return new NullSecurityManager();
    }
    
    @Override
    public void checkPermission(Permission perm) {
        currentSecurityManager.checkPermission(perm);
    }

    @Override
    public void checkAccept(String host, int port) {
        currentSecurityManager.checkAccept(host, port);
    }

    @Override
    public void checkAccess(Thread t) {
        currentSecurityManager.checkAccess(t);
    }

    @Override
    public void checkAccess(ThreadGroup g) {
        currentSecurityManager.checkAccess(g);
    }

    @Override
    public void checkAwtEventQueueAccess() {
        currentSecurityManager.checkAwtEventQueueAccess();
    }

    @Override
    public void checkConnect(String host, int port, Object context) {
        currentSecurityManager.checkConnect(host, port, context);
    }

    @Override
    public void checkConnect(String host, int port) {
        currentSecurityManager.checkConnect(host, port);
    }

    @Override
    public void checkCreateClassLoader() {
        currentSecurityManager.checkCreateClassLoader();
    }

    @Override
    public void checkDelete(String file) {
        currentSecurityManager.checkDelete(file);
    }

    @Override
    public void checkExec(String cmd) {
        currentSecurityManager.checkExec(cmd);
    }

    @Override
    public void checkLink(String lib) {
        currentSecurityManager.checkLink(lib);
    }

    @Override
    public void checkListen(int port) {
        currentSecurityManager.checkListen(port);
    }

    @Override
    public void checkMemberAccess(Class<?> clazz, int which) {
        currentSecurityManager.checkMemberAccess(clazz, which);
    }

    @Override
    public void checkMulticast(InetAddress maddr, byte ttl) {
        currentSecurityManager.checkMulticast(maddr, ttl);
    }

    @Override
    public void checkMulticast(InetAddress maddr) {
        currentSecurityManager.checkMulticast(maddr);
    }

    @Override
    public void checkPackageAccess(String pkg) {
        currentSecurityManager.checkPackageAccess(pkg);
    }

    @Override
    public void checkPackageDefinition(String pkg) {
        currentSecurityManager.checkPackageDefinition(pkg);
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
        currentSecurityManager.checkPermission(perm, context);
    }

    @Override
    public void checkPrintJobAccess() {
        currentSecurityManager.checkPrintJobAccess();
    }

    @Override
    public void checkPropertiesAccess() {
        currentSecurityManager.checkPropertiesAccess();
    }

    @Override
    public void checkPropertyAccess(String key) {
        currentSecurityManager.checkPropertyAccess(key);
    }

    @Override
    public void checkRead(FileDescriptor fd) {
        currentSecurityManager.checkRead(fd);
    }

    @Override
    public void checkRead(String file, Object context) {
        currentSecurityManager.checkRead(file, context);
    }

    @Override
    public void checkRead(String file) {
        currentSecurityManager.checkRead(file);
    }

    @Override
    public void checkSecurityAccess(String target) {
        currentSecurityManager.checkSecurityAccess(target);
    }

    @Override
    public void checkSetFactory() {
        currentSecurityManager.checkSetFactory();
    }

    @Override
    public void checkSystemClipboardAccess() {
        currentSecurityManager.checkSystemClipboardAccess();
    }

    @Override
    public boolean checkTopLevelWindow(Object window) {
        return currentSecurityManager.checkTopLevelWindow(window);
    }

    @Override
    public void checkWrite(FileDescriptor fd) {
        currentSecurityManager.checkWrite(fd);
    }

    @Override
    public void checkWrite(String file) {
        currentSecurityManager.checkWrite(file);
    }

    @Override
    public boolean getInCheck() {
        return currentSecurityManager.getInCheck();
    }

    @Override
    public Object getSecurityContext() {
        return currentSecurityManager.getSecurityContext();
    }

    @Override
    public ThreadGroup getThreadGroup() {
        return currentSecurityManager.getThreadGroup();
    }
}
