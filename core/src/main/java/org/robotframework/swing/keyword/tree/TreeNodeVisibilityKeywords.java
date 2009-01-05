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

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.tree.TreeSupport;

@RobotKeywords
public class TreeNodeVisibilityKeywords extends TreeSupport {
    @RobotKeyword("Fails if the tree node is not visible.\n\n"
        + "Example:\n"
        + "| Tree Node Should Be Visible | _myTree_ | _Root|Folder_ |\n")
    public void treeNodeShouldBeVisible(String identifier, String nodePath) {
        Assert.assertTrue("Tree node '" + nodePath + "' is not visible.", isVisible(identifier, nodePath));
    }

    @RobotKeyword("Fails if the tree node is visible.\n\n"
        + "Example:\n"
        + "| Tree Node Should Not Be Visible | _myTree_ | _Root|Folder_ |\n")
    public void treeNodeShouldNotBeVisible(String identifier, String nodePath) {
        Assert.assertFalse("Tree node '" + nodePath + "' is visible.", isVisible(identifier, nodePath));
    }

    private boolean isVisible(String identifier, String nodePath) {
        verifyPath(nodePath);
        return createTreeOperator(identifier).isVisible(nodePath);
    }

    private void verifyPath(String nodePath) {
        if (isIndex(nodePath))
            throw new IllegalArgumentException("Node's visibility cannot be checked by it's index.");
    }
}
