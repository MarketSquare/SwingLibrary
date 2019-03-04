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

import org.junit.Assert;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.tree.TreeSupport;
import org.robotframework.swing.keyword.timeout.TimeoutKeywords;


@RobotKeywords
public class TreeNodeVisibilityKeywords extends TreeSupport {
    public TimeoutKeywords timeout = new TimeoutKeywords();
    long old_time = 0;

    @RobotKeyword("Fails if the tree node is not visible.\n"
            + "Optionally, you can set jemmy timeout, default value being None. It will automatically select the right timeout.\n"
            + "See `Set Jemmy Timeout` keyword for more information about jemmy timeouts.\n\n"
            + "Example:\n"
            + "| `Tree Node Should Be Visible` | myTree | Root|Folder |\n"
            + "| `Tree Node Should Be Visible` | myTree | Root|Folder | 4 |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "jemmyTimeout="})
    public void treeNodeShouldBeVisible(String identifier, String nodePath, String jemmyTimeout) {
        if (jemmyTimeout != "None") {
            old_time = timeout.setJemmyTimeout("JTreeOperator.WaitNodeVisibleTimeout", jemmyTimeout);
        }
        try {
            Assert.assertTrue("Tree node '" + nodePath + "' is not visible.", isVisible(identifier, nodePath));
        } finally {
            if (jemmyTimeout != "None") timeout.setJemmyTimeout("JTreeOperator.WaitNodeVisibleTimeout", Long.toString(old_time));
        }
    }

    @RobotKeywordOverload
    public void treeNodeShouldBeVisible(String identifier, String nodeIdentifier) {
        treeNodeShouldBeVisible(identifier, nodeIdentifier, "None");
    }

    @RobotKeyword("Fails if the tree node is visible.\n"
            + "Optionally, you can set jemmy timeout, default value being None. It will automatically select the right timeout.\n"
            + "See `Set Jemmy Timeout` keyword for more information about jemmy timeouts.\n\n"
            + "Example:\n"
            + "| `Tree Node Should Not Be Visible` | myTree | Root|Folder |\n"
            + "| `Tree Node Should Not Be Visible` | myTree | Root|Folder | 4 |\n")

    @ArgumentNames({"identifier", "nodeIdentifier", "jemmyTimeout=5"})
    public void treeNodeShouldNotBeVisible(String identifier, String nodePath, String jemmyTimeout) {
        if (jemmyTimeout != "None") {
            old_time = timeout.setJemmyTimeout("JTreeOperator.WaitNodeVisibleTimeout", jemmyTimeout);
        }
        try {
            Assert.assertFalse("Tree node '" + nodePath + "' is visible.", isVisible(identifier, nodePath));
        } finally {
            if (jemmyTimeout != "None") timeout.setJemmyTimeout("JTreeOperator.WaitNodeVisibleTimeout", Long.toString(old_time));
        }
    }

    @RobotKeywordOverload
    public void treeNodeShouldNotBeVisible(String identifier, String nodeIdentifier) {
        treeNodeShouldNotBeVisible(identifier, nodeIdentifier, "None");
    }

    private boolean isVisible(String identifier, String nodePath) {
        verifyPath(nodePath);
        return treeOperator(identifier).isVisible(nodePath);
    }

    private void verifyPath(String nodePath) {
        if (isIndex(nodePath))
            throw new IllegalArgumentException("Node's visibility cannot be checked by it's index.");
    }
}
