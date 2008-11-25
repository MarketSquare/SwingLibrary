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
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class FileChooserKeywords {
//    private StringComparator comparator = new EqualsStringComparator();
    
    @RobotKeyword
    public void chooseFile(String fileName) {
        System.out.println("Choosing file '" + fileName + "'");
        JFileChooserOperator fileChooserOperator = new JFileChooserOperator();
        fileChooserOperator.chooseFile(fileName);
//        Assert.assertEquals(fileName, fileChooserOperator.getSelectedFile());
//        fileChooserOperator.clickOnFile(fileName, new EqualsStringComparator(), 2);
    }
}
