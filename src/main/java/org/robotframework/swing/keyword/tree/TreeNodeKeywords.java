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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.tree.TreePath;

import org.junit.Assert;

import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.tree.TreeOperator;
import org.robotframework.swing.tree.TreePathAction;
import org.robotframework.swing.tree.TreeSupport;
import org.robotframework.swing.util.ComponentUtils;

@RobotKeywords
public class TreeNodeKeywords extends TreeSupport {
    @RobotKeyword("Clears selections from a tree.\n\n"
            + "Example:\n"
            + "| `Clear Tree Selection` | myTree |\n")
    @ArgumentNames({"identifier"})
    public void clearTreeSelection(String identifier) {
        treeOperator(identifier).clearSelection();
    }

    @RobotKeyword("Collapses a node in a tree.\n\n"
            + "See `Expand Tree Node` for information about ``nodeIdentifier``.\n\n"
            + "Examples:\n"
            + "| `Collapse Tree Node` | myTree | Root|Folder |\n"
            + "| `Collapse Tree Node` | myTree | 3 |\n")
    @ArgumentNames({"identifier", "nodeIdentifier"})
    public void collapseTreeNode(String identifier, String nodeIdentifier) {
        treeOperator(identifier).collapse(nodeIdentifier);
    }

    @RobotKeyword("Expands a node in a tree.\n\n"
            + "Argument ``nodeIdentifier`` can be either tree path (i.e visible labels of nodes to be expanded), "
            + "or index of the expanded node element. Nested structures can only be expanded "
            + "using the tree path syntax.\n\nExamples:\n"
            + "| `Expand Tree Node` | myTree | Root|Folder |\n"
            + "| `Expand Tree Node` | myTree | 3 |\n")
    @ArgumentNames({"identifier", "nodeIdentifier"})
    public void expandTreeNode(String identifier, String nodeIdentifier) {
        treeOperator(identifier).expand(nodeIdentifier);
    }

    @RobotKeyword("Collapses all nodes in a tree.\n\n"
            + "Example:\n"
            + "| `Collapse All Tree Nodes` | myTree |\n")
    @ArgumentNames({"identifier"})
    public void collapseAllTreeNodes(String identifier) {
        final TreeOperator treeOperator = treeOperator(identifier);
        treeOperator.operateOnAllNodes(new TreePathAction() {
            public void operate(TreePath path) {
                treeOperator.collapse(path);
            }
        });
    }

    @RobotKeyword("Expands all nodes in a tree.\n\n"
            + "Example:\n"
            + "| `Expand All Tree Nodes` | myTree |\n")
    @ArgumentNames({"identifier"})
    public void expandAllTreeNodes(String identifier) {
        final TreeOperator treeOperator = treeOperator(identifier);
        treeOperator.operateOnAllNodes(new TreePathAction() {
            public void operate(TreePath path) {
                treeOperator.expand(path);
            }
        });
    }

    @RobotKeyword("Sets a node as selected in a tree.\n"
            + "Does not clear earlier selections.\n"
            + "If several nodes have the same path then *only the first* of those nodes is selected.\n\n"
            + "Example:\n"
            + "| `Select Tree Node` | myTree | Root|Folder |\n"
            + "Any number of node identifiers can be provided to select multiple nodes at once:\n"
            + "| `Select Tree Node` | myTree | Root|Folder | Root|Folder2 | Root|Folder3 |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "*additionalNodeIdentifiers"})
    public void selectTreeNode(String identifier, String nodeIdentifier, String[] additionalNodeIdentifiers) {
        TreeOperator treeOperator = treeOperator(identifier);
        treeOperator.addSelection(nodeIdentifier);
        for (String node : additionalNodeIdentifiers) {
            treeOperator.addSelection(node);
        }
    }

    @RobotKeyword("Gets item names from the node context popup menu.\n"
            + "Clears earlier selections.\n"
            + "If several nodes have the same path then *only the first* menu item names of those nodes are returned.\n\n"
            + "Example:\n"
            + "| @{items}= | `Get Node Items From Tree Popup Menu` | myTree | Root|Folder | Actions |\n"
            + "| `Should Contain` | ${items} | Do something |")
    @ArgumentNames({"identifier", "nodeIdentifier", "menuPath"})
    public List<String> getNodeItemsFromTreePopupMenu(String identifier, String nodeIdentifier, String menuPath) {
        JPopupMenuOperator popupMenuOperator = treeOperator(identifier).createPopupOperator(nodeIdentifier);

        if (menuPath == null || menuPath.isEmpty()) {
            return ComponentUtils.getParsedElements(popupMenuOperator.getSubElements());
        } else {
            JMenuItemOperator subItem = popupMenuOperator.showMenuItem(menuPath);
            return subItem.getSubElements().length < 1 ? new ArrayList<String>() :
                    ComponentUtils.getParsedElements(subItem.getSubElements()[0].getSubElements());
        }
    }

    @RobotKeyword("Clicks on a tree node.\n\n"
            + "Examples:\n"
            + "| `Click On Tree Node` | myTree | Root|Folder |\n"
            + "| `Click On Tree Node` | myTree | 0      | \n\n"
            + "An optional ``clickCount`` parameter can be provided for example if a double click is required.\n"
            + "Default click count is one:\n"
            + "| `Click On Tree Node` | myTree | Root|Folder | 2 | # doubleclicks on node |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "clickCount=1"})
    public void clickOnTreeNode(String identifier, String nodeIdentifier, int clickCount) {
        treeOperator(identifier).clickOnNode(nodeIdentifier, clickCount);
    }

    @RobotKeywordOverload
    public void clickOnTreeNode(String identifier, String nodeIdentifier) {
        clickOnTreeNode(identifier, nodeIdentifier, 1);
    }

    @RobotKeyword("Fails if the tree node is collapsed.\n\n"
            + "Example:\n"
            + "| `Tree Node Should Be Expanded` | myTree | Root|Folder |\n")
    @ArgumentNames({"identifier", "nodeIdentifier"})
    public void treeNodeShouldBeExpanded(String identifier, String nodeIdentifier) {
        boolean isExpanded = treeOperator(identifier).isExpanded(nodeIdentifier);
        Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not expanded.", isExpanded);
    }

    @RobotKeyword("Fails if the tree node is expanded.\n\n"
            + "Example:\n"
            + "| `Tree Node Should Be Collapsed` | myTree | Root|Folder |\n")
    @ArgumentNames({"identifier", "nodeIdentifier"})
    public void treeNodeShouldBeCollapsed(String identifier, String nodeIdentifier) {
        boolean isCollapsed = treeOperator(identifier).isCollapsed(nodeIdentifier);
        Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not collapsed.", isCollapsed);
    }

    @RobotKeyword("Sets a tree node as unselected.\n\n"
            + "Example:\n"
            + "| `Unselect Tree Node` | myTree | Root|Folder |\n")
    @ArgumentNames({"identifier", "nodeIdentifier"})
    public void unselectTreeNode(String identifier, String nodeIdentifier) {
        treeOperator(identifier).removeSelection(nodeIdentifier);
    }

    @RobotKeyword("Fails if the node has child nodes.\n"
            + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`\n\n"
            + "Example:\n"
            + "| `Tree Node Should Be Leaf` | myTree | Root|Folder |\n")
    @ArgumentNames({"identifier", "nodeIdentifier"})
    public void treeNodeShouldBeLeaf(String identifier, String nodeIdentifier) {
        boolean isLeaf = treeOperator(identifier).isLeaf(nodeIdentifier);
        Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not leaf.", isLeaf);
    }

    @RobotKeyword("Fails if the node doesn't have child nodes.\n"
            + "You might want to set the waiting timeout with the keyword `Set Jemmy Timeout`\n\n"
            + "Example:\n"
            + "| `Tree Node Should Not Be Leaf` | myTree | Root|Folder |\n")
    @ArgumentNames({"identifier", "nodeIdentifier"})
    public void treeNodeShouldNotBeLeaf(String identifier, String nodeIdentifier) {
        boolean isLeaf = treeOperator(identifier).isLeaf(nodeIdentifier);
        Assert.assertFalse("Tree node '" + nodeIdentifier + "' is leaf.", isLeaf);
    }

    @RobotKeyword("Returns the count of all visible nodes.\n\n"
            + "Example:\n"
            + "| ${nodeCount}= | `Get Tree Node Count` | myTree |\n"
            + "| `Should Be Equal As Integers` | 3 | ${nodeCount} |\n")
    @ArgumentNames({"identifier"})
    public int getTreeNodeCount(String identifier) {
        return treeOperator(identifier).getRowCount();
    }

    @RobotKeyword("Returns the node name.\n"
            + "Argument ``nodeIndex`` must be a number and it is counted from visible nodes (index starts from 0).\n\n"
            + "Example:\n"
            + "| ${nodeLabel}= | `Get Tree Node Label` | myTree | 3 |\n"
            + "| `Should Be Equal` | Element1 | ${nodeLabel} |\n")
    @ArgumentNames({"identifier", "nodeIndex"})
    public String getTreeNodeLabel(String identifier, String nodeIndex) {
        TreeOperator treeOperator = treeOperator(identifier);
        return treeOperator.getTreeNodeLabel(asIndex(nodeIndex));
    }

    @RobotKeyword("Returns the node index.\n"
            + "Argument ``nodePath`` must be a string of form ``path|to|node``.\n\n"
            + "Example:\n"
            + "| ${nodeIndex}= | `Get Tree Node Index` | myTree | Root|Folder|ElementX |\n"
            + "| `Should Be Equal As Integers` | 4 | ${nodeIndex} |\n")
    @ArgumentNames({"identifier", "nodePath"})
    public int getTreeNodeIndex(String identifier, String nodePath) {
        return treeOperator(identifier).getTreeNodeIndex(nodePath);
    }

    @RobotKeyword("Returns a list with all the child node names of the specified node.\n\n"
            + "Example:\n"
            + "| ${expectedElements}= | `Create List` | someElement | otherElement |\n"
            + "| ${actualElements}=   | `Get Tree Node Child Names` | myTree | Element Folder |\n"
            + "| `Lists Should Be Equal` | ${expectedElements} | ${actualElements} | # This keyword comes from Collections library |\n")
    @ArgumentNames({"identifier", "nodeIdentifier"})
    public List<String> getTreeNodeChildNames(String identifier, String nodeIdentifier) {
        Collection<String> childNames = treeOperator(identifier).getTreeNodeChildNames(nodeIdentifier);
        return new ArrayList<String>(childNames);
    }
}
