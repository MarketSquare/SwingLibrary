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

package org.robotframework.swing.keyword.tree;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.tree.TreeSupport;

@RobotKeywords
public class TreeNodeSelectionKeywords extends TreeSupport {
    @RobotKeyword("Fails if the tree node is not selected.\n\n"
        + "Example:\n"
        + "| Tree Node Should Be Selected | _myTree_ | _Root|Folder_ |\n")
    public void treeNodeShouldBeSelected(String identifier, String nodeIdentifier) {
        boolean isSelected = treeOperator(identifier).isPathSelected(nodeIdentifier);
        Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not selected.", isSelected);
    }

    @RobotKeyword("Fails if the tree node is selected.\n\n"
        + "Example:\n"
        + "| Tree Node Should Be Selected | _myTree_ | _Root|Folder_ |\n")
    public void treeNodeShouldNotBeSelected(String identifier, String nodeIdentifier) {
        boolean isSelected = treeOperator(identifier).isPathSelected(nodeIdentifier);
        Assert.assertFalse("Tree node '" + nodeIdentifier + "' is selected.", isSelected);
    }
}
