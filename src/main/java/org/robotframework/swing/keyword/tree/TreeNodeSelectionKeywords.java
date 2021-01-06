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

import javax.swing.tree.TreePath;

@RobotKeywords
public class TreeNodeSelectionKeywords extends TreeSupport {
    public TimeoutKeywords timeout = new TimeoutKeywords();
    long old_time = 0;

    @RobotKeyword("Fails if the tree node is not selected.\n"
            + "Optionally, you can set jemmy timeout, default value being None. It will automatically select the right timeout.\n"
            + "See `Set Jemmy Timeout` keyword for more information about jemmy timeouts.\n\n"
            + "Example:\n"
            + "| `Tree Node Should Be Selected` | myTree | Root|Folder |\n"
            + "| `Tree Node Should Be Selected` | myTree | Root|Folder | 4 |\n"
            + "In case of existence of multiple nodes with the same node identifier, ``nodeInstance`` "
            + "can be used to check the selection of the desired node. If ``nodeInstance`` is not "
            + "specified, first node found with desired ``nodeIdentifier`` is checked. \n\n"
            + "Node instance must be specified using ``#`` before the desired number.\"\n"
            + "*N.B.* ``#`` is a special character and must be escaped using ``\\``.\n\n"
            + "Example:\n"
            + "| `Tree Node Should Be Selected` | mytree | Root|Folder | \\#3 |   | # check if 4th node is selected with specified ``nodeIdentifier`` | \n"
            + "| `Tree Node Should Be Selected` | mytree | Root|Folder | \\#2 | 4 | # wait checking of 3rd node with specified ``nodeIdentifier`` in 4 seconds |\n\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "nodeInstance=", "jemmyTimeout="})
    public void treeNodeShouldBeSelected(String identifier, String nodeIdentifier, String nodeInstance, String jemmyTimeout) {
        if (jemmyTimeout != null && jemmyTimeout != "None") {
            old_time = timeout.setJemmyTimeout("JTreeOperator.WaitNodeVisibleTimeout", jemmyTimeout);
        }
        try {
            Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not selected.", isSelected(identifier, nodeIdentifier, nodeInstance));
        } finally {
            if (jemmyTimeout != null && jemmyTimeout != "None")
                timeout.setJemmyTimeout("JTreeOperator.WaitNodeVisibleTimeout", Long.toString(old_time));
        }
    }

    public boolean isSelected(String identifier, String nodeIdentifier, String nodeInstance) {
        if (nodeInstance != null && !nodeInstance.equals("None")) {
            TreePath selectionPath = treeOperator(identifier).getDuplicatedNodeInstance(nodeIdentifier, Integer.parseInt(nodeInstance.split("#")[1]));
            return treeOperator(identifier).isPathSelected(selectionPath);
        } else {
            return treeOperator(identifier).isPathSelected(nodeIdentifier);
        }
    }

    @RobotKeywordOverload
    public void treeNodeShouldBeSelected(String identifier, String nodeIdentifier, String arg) {
        if (arg.contains("#")) {
            treeNodeShouldBeSelected(identifier, nodeIdentifier, arg, "None");
        } else {
            treeNodeShouldBeSelected(identifier, nodeIdentifier, "None", arg);
        }
    }

    @RobotKeyword("Fails if the tree node is selected.\n"
            + "Optionally, you can set jemmy timeout, default value being None. It will automatically select the right timeout.\n"
            + "See `Set Jemmy Timeout` keyword for more information about jemmy timeouts.\n\n"
            + "Example:\n"
            + "| `Tree Node Should Be Selected` | myTree | Root|Folder |\n"
            + "| `Tree Node Should Be Selected` | myTree | Root|Folder | 4 |\n"
            + "In case of existence of multiple nodes with the same node identifier ``nodeInstance`` "
            + "can be used to check the selection of the desired node. If ``nodeInstance`` is not "
            + "specified first node found with desired ``nodIdentifier`` is checked.\n\n"
            + "Node instance must be specified using ``#`` before the desired number.\"\n"
            + "*N.B.* ``#`` is a special character and must be escaped using ``\\``.\n\n"
            + "Example:\n"
            + "| `Tree Node Should Not Be Selected` | mytree | Root|Folder | \\#2 |   | # check if 3rd occurrence of "
            + "``nodeIdentifier`` is not selected |\n"
            + "| `Tree Node Should Not Be Selected` | mytree | Root|Folder | \\#2 | 4 | # wait checking the "
            + "selection of the 3rd node with specified ``nodeIdentifier`` in 4 seconds |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "nodeInstance=", "jemmyTimeout="})
    public void treeNodeShouldNotBeSelected(String identifier, String nodeIdentifier, String nodeInstance, String jemmyTimeout) {
        if (jemmyTimeout != null && !jemmyTimeout.equals("None")) {
            old_time = timeout.setJemmyTimeout("JTreeOperator.WaitNodeVisibleTimeout", jemmyTimeout);
        }
        try {
            Assert.assertFalse("Tree node '" + nodeIdentifier + "' is selected.", isSelected(identifier, nodeIdentifier, nodeInstance));
        } finally {
            if (jemmyTimeout != null && !jemmyTimeout.equals("None"))
                timeout.setJemmyTimeout("JTreeOperator.WaitNodeVisibleTimeout", Long.toString(old_time));
        }
    }

    @RobotKeywordOverload
    public void treeNodeShouldNotBeSelected(String identifier, String nodeIdentifier, String arg) {
        if (arg.contains("#")) {
            treeNodeShouldNotBeSelected(identifier, nodeIdentifier, arg, "None");
        } else {
            treeNodeShouldNotBeSelected(identifier, nodeIdentifier, "None", arg);
        }
    }

}
