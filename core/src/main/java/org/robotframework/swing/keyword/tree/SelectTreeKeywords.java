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

package org.robotframework.swing.keyword.tree;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.tree.TreeSupport;

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class SelectTreeKeywords extends TreeSupport {
    @RobotKeyword("Clears selections from a tree.\n\n"
        + "Example:\n"
        + "| Clear Tree Selection | _myTree_ |\n")
    public void clearTreeSelection(String identifier) {
        createTreeOperator(identifier).clearSelection();
    }
}
