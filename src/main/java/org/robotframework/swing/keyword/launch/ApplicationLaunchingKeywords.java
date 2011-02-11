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

package org.robotframework.swing.keyword.launch;

import java.lang.reflect.Method;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

@RobotKeywords
public class ApplicationLaunchingKeywords {
    @RobotKeyword("Launches application with the given arguments.\n\n"
        + "Example:\n"
        + "| Launch Application | _com.acme.myapplication.MyApp_ | _C:\\data.txt_ |\n")
    @ArgumentNames({"className", "*args"})
    public void launchApplication(String className, String[] args) throws Exception {
        Method mainMethod = getMainMethod(className);
        mainMethod.invoke(null, new Object[] { args });
    }

    @RobotKeyword("Alias for `Launch Application` keyword\n")
    @ArgumentNames({"className", "*args"})
    public void startApplication(String className, String[] args) throws Exception {
        launchApplication(className, args);
    }

    @RobotKeyword("Launches application in a separate thread with the given arguments.\n"
        + "This is useful if your application does something that blocks eg. opens up a dialog.\n\n"
        + "Example:\n"
        + "| Start Application In Separate Thread | _com.acme.myapplication.MyApp_ |\n")
    @ArgumentNames({"className", "*args"})
    public void startApplicationInSeparateThread(final String className, final String[] args) throws Exception {
        createThread(new Runnable() {
            public void run() {
                try {
                    launchApplication(className, args);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    Thread createThread(Runnable runnable) {
        return new Thread(runnable);
    }
    
    private Method getMainMethod(String className) throws ClassNotFoundException {
        Class<?> clss = Class.forName(className);
        try {
            return clss.getMethod("main", String[].class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Class '" + className + "' doesn't have a main method.");
        }
    }
}
