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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TestOut;
import org.robotframework.javalib.library.AnnotationLibrary;
import org.robotframework.swing.keyword.timeout.TimeoutKeywords;

public class SwingLibrary extends AnnotationLibrary {
    public static final String ROBOT_LIBRARY_SCOPE = "GLOBAL";
    private final AnnotationLibrary annotationLibrary = new AnnotationLibrary("org/robotframework/swing/keyword/**/*.class");
    public static SwingLibrary instance;
    public static final String LIBRARY_DOCUMENTATION =
            "SwingLibrary is a Robot Framework test library for testing Java Swing user interfaces.\n" +
            "It uses a tool called Jemmy (http://java.net/projects/jemmy/) internally to operate on the Swing components.\\nn" +
            "*Locating components*\n\n  " +
            "Most of the keywords that operate on a visible component take an argument named `identifier`." +
            "This argument is used to locate the element. The `identifier` can be either:\n\n" +
            "* the internal name of the component (set using `setName` method in Java code)\n" +
            "* the index of the component in the component hierarchy. Using indices is strongly discouraged\n" +
            "* for components that have visible text, such as buttons, the visible text can also be used as `identifier`\n\n";


    public SwingLibrary() {
        this(Collections.<String>emptyList());
    }

    protected SwingLibrary(final String keywordPattern) {
        this(new ArrayList<String>() {{ add(keywordPattern); }});
    }

    protected SwingLibrary(Collection<String>  keywordPatterns) {
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

    public Object runKeyword(String keywordName, Object[] args) {
        return annotationLibrary.runKeyword(keywordName, toStrings(args));
    }

    public String[] getKeywordArguments(String keywordName) {
        return annotationLibrary.getKeywordArguments(keywordName);
    }

    public String getKeywordDocumentation(String keywordName) {
        if (keywordName.equals("__intro__"))
            return LIBRARY_DOCUMENTATION;
        return annotationLibrary.getKeywordDocumentation(keywordName);
    }

    public String[] getKeywordNames() {
        return annotationLibrary.getKeywordNames();
    }

    private void setDefaultTimeouts() {
        new TimeoutKeywords().setJemmyTimeouts("10");
    }

    private void disableOutput() {
        JemmyProperties.setCurrentOutput(TestOut.getNullOutput());
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
