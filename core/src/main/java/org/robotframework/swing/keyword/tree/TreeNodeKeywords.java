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

/**
 * @author Heikki Hulkko
 */
@RobotKeywords
public class TreeNodeKeywords extends TreeSupport {
    @RobotKeyword("Collapses a node in a tree.\n\n"
        + "Examples:\n"
        + "| Collapse Tree Node | _myTree_ | _Root|Folder_ |\n"
        + "| Collapse Tree Node | _myTree_ | _3_ |\n")
    public void collapseTreeNode(String identifier, String nodeIdentifier) {
        createTreeOperator(identifier).collapse(nodeIdentifier);
    }

    @RobotKeyword("Expands a node in a tree.\n\n"
        + "Examples:\n"
        + "| Expand Tree Node | _myTree_ | _Root|Folder_ |\n"
        + "| Expand Tree Node | _myTree_ | _3_ |\n")
    public void expandTreeNode(String identifier, String nodeIdentifier) {
        createTreeOperator(identifier).expand(nodeIdentifier);
    }

    @RobotKeyword("Sets a node as selected in a tree.\n"
        + "Does not clear earlier selections.\n\n"
        + "Example:\n"
        + "| Select Tree Node | _myTree_ | _Root|Folder_ |\n")
    public void selectTreeNode(String identifier, String nodeIdentifier) {
        createTreeOperator(identifier).addSelection(nodeIdentifier);
    }

    @RobotKeyword("Fails if the tree node is collapsed.\n\n"
        + "Example:\n"
        + "| Tree Node Should Be Expanded | _myTree_ | _Root|Folder_ |\n")
    public void treeNodeShouldBeExpanded(String identifier, String nodeIdentifier) {
        boolean isExpanded = createTreeOperator(identifier).isExpanded(nodeIdentifier);
        Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not expanded.", isExpanded);
    }

    @RobotKeyword("Fails if the tree node is expanded.\n\n"
        + "Example:\n"
        + "| Tree Node Should Be Collapsed | _myTree_ | _Root|Folder_ |\n")
    public void treeNodeShouldBeCollapsed(String identifier, String nodeIdentifier) {
        boolean isCollapsed = createTreeOperator(identifier).isCollapsed(nodeIdentifier);
        Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not collapsed.", isCollapsed);
    }

    @RobotKeyword("Sets a tree node as unselected.\n\n"
        + "Example:\n"
        + "| Unselect Tree Node | _myTree_ | _Root|Folder_ |\n")
    public void unselectTreeNode(String identifier, String nodeIdentifier) {
        createTreeOperator(identifier).removeSelection(nodeIdentifier);
    }

    @RobotKeyword("Fails if the node has child nodes.\n"
        + "Assumes current context is a tree.\n"
        + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`\n\n"
        + "Example:\n"
        + "| Tree Node Should Be Leaf | _myTree_ | _Root|Folder_ |")
    public void treeNodeShouldBeLeaf(String identifier, String nodeIdentifier) {
        boolean isLeaf = createTreeOperator(identifier).isLeaf(nodeIdentifier);
        Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not leaf.", isLeaf);
    }

    @RobotKeyword("Fails if the node doesn't have child nodes.\n"
        + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`\n\n"
        + "Example:\n"
        + "| Tree Node Should Not Be Leaf | _myTree_ | _Root|Folder_ |")
    public void treeNodeShouldNotBeLeaf(String identifier, String nodeIdentifier) {
        boolean isLeaf = createTreeOperator(identifier).isLeaf(nodeIdentifier);
        Assert.assertFalse("Tree node '" + nodeIdentifier + "' is leaf.", isLeaf);
    }
}
