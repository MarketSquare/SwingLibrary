/*
 * Copyright 2008-2011 Nokia Siemens Networks Oyj
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
package org.robotframework.swing;

import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TestOut;
import org.robotframework.javalib.library.AnnotationLibrary;
import org.robotframework.javalib.library.KeywordDocumentationRepository;
import org.robotframework.javalib.library.RobotJavaLibrary;
import org.robotframework.swing.keyword.timeout.TimeoutKeywords;
import org.robotframework.swing.util.StandardOutOutput;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SwingLibrary implements KeywordDocumentationRepository, RobotJavaLibrary {
    public static final String ROBOT_LIBRARY_SCOPE = "GLOBAL";
    public static SwingLibrary instance;
    private final AnnotationLibrary annotationLibrary = new AnnotationLibrary(
            "org/robotframework/swing/keyword/**/*.class");
    private static final String LIBRARY_DOCUMENTATION = "SwingLibrary is a Robot Framework test library for testing Java Swing user interfaces.\n\n"
            + "This document explains how to use keywords provided by SwingLibrary. For information about installation, support, and more please visit the "
            + "[https://github.com/robotframework/SwingLibrary|project page]."
            + "For more information about Robot Framework, see http://robotframework.org.\n\n"
            + "Internally, SwingLibrary uses a tool called [http://java.net/projects/jemmy/|Jemmy] to operate on Swing components.\n"
            + "= Getting Started =\n"
            + "SwingLibrary only runs under Jython. For using SwingLibrary keywords with Python, check out "
            + "[https://github.com/robotframework/remoteswinglibrary | RemoteSwingLibrary]. "
            + "Before using it, both the SwingLibrary and the application and all its dependencies need to be available in the CLASSPATH.\n\n"
            + "In Windows, this can be done like:\n\n"
            + "``set CLASSPATH=swinglibrary-<version>.jar;myApp.jar``\n\n"
            + "and in Unix based OS like this:\n\n"
            + "``CLASSPATH=swinglibrary-<version>.jar:myApp.jar``\n\n"
            + "When writing the tests, the SwingLibrary needs to be taken into use in the settings table:\n"
            + "| *Settings * | *Value* |\n"
            + "| Library | SwingLibrary |\n"
            + ""
            + "The tested application can be started with keyword `Start Application`, using the name "
            + "of the main application class as an argument:\n"
            + "| `Start Application` | com.acme.TheApplication |\n"
            + "| `Select Window`     | TheApplication Window   |\n"
            + "| `Push Button`       | AcmeButton              |\n"
            + "To run the test with Robot Framework, use the following command: \n\n"
            + "``jython -m robot my_test.robot``\n"

            + "= Contexts =\n"
            + "Keywords that operate on a component always search for the component in some context, "
            + "which has to explicitly set. "
            + "Allowed contexts are windows, dialogs, internal frames, tabbed panes and layers. "
            + "After a context has been selected, all subsequent keywords search for components in that context "
            + "until a new context is selected. Keywords that can be used to select a context are "
            + "`Select Window`, `Select Dialog` and `Select Context`. For example:\n"
            + "| `Select Window` | My App |\n"
            + "| `Select From Main Menu` | File|Exit |\n"
            + "| `Select Dialog` | Confirm |\n"
            + "| `Push Button`   | No      |\n"
            + "| `Select Window` | My App  |\n"

            + "= Locating components =\n"
            + "Most of the keywords that operate on a visible component take an argument named ``identifier``, "
            + "which is used to locate the element. The first matching element is operated on, according to these rules:\n"
            + "- If the ``identifier`` is a number, it is used as a zero-based index for the particular component type in "
            + "the current context. Using indices is, however, fragile and is strongly discouraged.\n"
            + "- If the ``identifier`` matches to internal name of a component (set using ``setName`` method in Java code), that component is chosen.\n"
            + "- For components that have visible text (e.g. buttons), ``identifier`` is also matched against that.\n"
            + "- Text field keywords also support accessing awt-text fields by prefixing the identifier with ``awt=``.\n"
            + "Keyword `List Components in Context` lists all components and their names and indices in a given context.\n"

            + "= Running keywords in separate threads =\n"
            + "Some actions may cause dialogs or other components to pop up and the keyword would then not return until\n"
            + "the new dialog is closed. In these situations the keyword should be executed in a separate thread,\n"
            + "`Run Keyword In Separate Thread` that test execution can continue.\n"

            + "= Logging =\n"
            + "The internal logging of Jemmy is available on Robot logs, when using ``DEBUG`` log level.\n"

            + "= Regular expressions =\n"
            + "More information about Java regular expressions and patterns can be found here: http://java.sun.com/docs/books/tutorial/essential/regex/ and here: http://java.sun.com/javase/7/docs/api/java/util/regex/Pattern.html";
    public SwingLibrary() {
        this(Collections.<String> emptyList());
    }

    protected SwingLibrary(final String keywordPattern) {
        this(new ArrayList<String>() {
            {
                add(keywordPattern);
            }
        });
    }

    protected SwingLibrary(Collection<String> keywordPatterns) {
        addKeywordPatterns(keywordPatterns);
        disableOutput();
        setDefaultTimeouts();
        instance = this;
    }

    private void addKeywordPatterns(Collection<String> keywordPatterns) {
        for (String pattern : keywordPatterns) {
            annotationLibrary.addKeywordPattern(pattern);
        }
    }

    @Override
    public Object runKeyword(String keywordName, Object[] args) {
        return annotationLibrary.runKeyword(keywordName, toStrings(args));
    }

    @Override
    public String[] getKeywordArguments(String keywordName) {
        return annotationLibrary.getKeywordArguments(keywordName);
    }

    @Override
    public String getKeywordDocumentation(String keywordName) {
        if (keywordName.equals("__intro__"))
            return LIBRARY_DOCUMENTATION;
        return annotationLibrary.getKeywordDocumentation(keywordName);
    }

    @Override
    public String[] getKeywordNames() {
        return annotationLibrary.getKeywordNames();
    }

    private void setDefaultTimeouts() {
        new TimeoutKeywords().setJemmyTimeouts("10");
    }

    private void disableOutput() {
        TestOut out = new StandardOutOutput();
        JemmyProperties.setCurrentOutput(out);
    }

    private Object[] toStrings(Object[] args) {
        Object[] newArgs = new Object[args.length];
        for (int i = 0; i < newArgs.length; i++) {
            if (args[i].getClass().isArray()) {
                newArgs[i] = args[i];
            } else {
                newArgs[i] = args[i].toString();
            }
        }
        return newArgs;
    }
}
