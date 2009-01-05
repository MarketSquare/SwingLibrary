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

import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.TestOut;
import org.robotframework.javalib.library.AnnotationLibrary;
import org.robotframework.swing.keyword.timeout.TimeoutKeywords;

public class SwingLibrary extends AnnotationLibrary {
    public SwingLibrary() {
        super("org/robotframework/swing/keyword/**/*.class");
        disableOutput();
        setDefaultTimeouts();
    }

    private void setDefaultTimeouts() {
        new TimeoutKeywords().setJemmyTimeouts("5");
    }

    private void disableOutput() {
        JemmyProperties.setCurrentOutput(TestOut.getNullOutput());
    }
}
