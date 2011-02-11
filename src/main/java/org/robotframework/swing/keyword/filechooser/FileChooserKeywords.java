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
package org.robotframework.swing.keyword.filechooser;

import org.netbeans.jemmy.operators.JFileChooserOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.filechooser.FileChooserOperatorFactory;

@RobotKeywords
public class FileChooserKeywords {
    private FileChooserOperatorFactory fileChooserOperatorFactory = new FileChooserOperatorFactory();
    
    @RobotKeyword("Chooses from a filechooser by pushing the approve button.\n"
        + "An optional file name can be provided, which is inserted to the file name textfield.\n"
        + "Expects the file chooser to be open.\n\n"
        + "Example:\n"
        + "| Push Button | _Open File_    |\n"
        + "| Choose From File Chooser | _/tmp/elements.xml_ |\n")
    @ArgumentNames({"*fileName"})
    public void chooseFromFileChooser(String[] fileName) {
        if (fileName.length > 0) {
            fileChooserOperator().chooseFile(fileName[0]);
        } else {
            fileChooserOperator().approve();
        }
    }
    
    @RobotKeyword("Cancels and closes file chooser.\n"
        + "Expects the file chooser to be open.\n\n"
        + "Example:\n"
        + "| Push Button         | _Open File_ |\n"
        + "| Cancel File Chooser |             |\n")
    public void cancelFileChooser() {
        fileChooserOperator().cancelSelection();
    }

    private JFileChooserOperator fileChooserOperator() {
        return fileChooserOperatorFactory.createFileChooserOperator();
    }
}

