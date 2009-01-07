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

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.tree.TreeOperator;
import org.robotframework.swing.tree.TreeSupport;

@RobotKeywords
public class TreeNodeKeywords extends TreeSupport {
    @RobotKeyword("Clears selections from a tree.\n\n"
        + "Example:\n"
        + "| Clear Tree Selection | _myTree_ |\n")
    public void clearTreeSelection(String identifier) {
        createTreeOperator(identifier).clearSelection();
    }
    
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
        + "Does not clear earlier selections.\n"
        + "If several nodes have the same path then *only the first* of those nodes is selected.\n\n"
        + "Example:\n"
        + "| Select Tree Node | _myTree_ | _Root|Folder_ |\n"
        + "Any number of node identifiers can be provided to select multiple nodes at once:\n"
        + "| Select Tree Node | _myTree_ | _Root|Folder_ | _Root|Folder2_ | _Root|Folder3_ |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "*additionalNodeIdentifiers"})
    public void selectTreeNode(String identifier, String nodeIdentifier, String[] additionalNodeIdentifiers) {
        TreeOperator treeOperator = createTreeOperator(identifier);
        treeOperator.addSelection(nodeIdentifier);
        for (String node : additionalNodeIdentifiers) {
            treeOperator.addSelection(node);
        }
    }
    
    @RobotKeyword("Clicks on a tree nodet.\n\n"
        + "Examples:\n"
        + "| Click On Tree Node | _myTree_ | _Root|Folder_ |\n"
        + "| Click On Tree Node | _myTree_ | _0_      | \n\n"
        + "An optional _click count_ parameter can be provided for example if a double click is required.\n"
        + "Default click count is one:\n"
        + "| Click On Tree Node | _myTree_ | Root|Folder_ | _2_ | # doubleclicks on node |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "*clickCount"})
    public void clickOnTreeNode(String identifier, String nodeIdentifier, String[] clickCount) {
        
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
        + "| Tree Node Should Be Leaf | _myTree_ | _Root|Folder_ |\n")
    public void treeNodeShouldBeLeaf(String identifier, String nodeIdentifier) {
        boolean isLeaf = createTreeOperator(identifier).isLeaf(nodeIdentifier);
        Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not leaf.", isLeaf);
    }

    @RobotKeyword("Fails if the node doesn't have child nodes.\n"
        + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`\n\n"
        + "Example:\n"
        + "| Tree Node Should Not Be Leaf | _myTree_ | _Root|Folder_ |\n")
    public void treeNodeShouldNotBeLeaf(String identifier, String nodeIdentifier) {
        boolean isLeaf = createTreeOperator(identifier).isLeaf(nodeIdentifier);
        Assert.assertFalse("Tree node '" + nodeIdentifier + "' is leaf.", isLeaf);
    }
    
    @RobotKeyword("Returns the count of all visible nodes.\n\n"
        + "Example:\n"
        + "| ${nodeCount}= | Get Tree Node Count | _myTree_ |\n"
        + "| Should Be Equal As Integers | _3_ | _${nodeCount}_ |\n")
    public int getTreeNodeCount(String identifier) {
        return createTreeOperator(identifier).getRowCount();
    }
    
    @RobotKeyword("Returns the node name.\n"
    	+ "NodeIndex must be a number and it is counted from visible nodes (index starts from 0).\n\n"
        + "Example:\n"
        + "| ${nodeLabel}= | Get Tree Node Label | _3_ |\n"
        + "| Should Be Equal | _Element1_ | _${nodeLabel}_ |\n")
    public String getTreeNodeLabel(String identifier, String nodeIndex) {
        TreeOperator treeOperator = createTreeOperator(identifier);
        return treeOperator.getTreeNodeLabel(asIndex(nodeIndex));
    }
    
    @RobotKeyword("Returns the node index.\n"
        + "NodePath must be a string of form _path|to|node_.\n\n"
        + "Example:\n"
        + "| ${nodeIndex}= | Get Tree Node Index | _Root|Folder|ElementX_ |\n"
        + "| Should Be Equal As Integers | _4_ | _${nodeIndex}_ |\n")
    public int getTreeNodeIndex(String identifier, String nodePath) {
        return createTreeOperator(identifier).getTreeNodeIndex(nodePath);
    }
}
