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
import java.util.Arrays;
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
import org.robotframework.swing.keyword.timeout.TimeoutKeywords;

@RobotKeywords
public class TreeNodeKeywords extends TreeSupport {
    public TimeoutKeywords timeout = new TimeoutKeywords();
    long old_time = 0;

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
            + "| `Collapse Tree Node` | myTree | 3 |\n\n"
            + "In case of existence of multiple nodes with the same node identifier, ``nodeInstance`` "
            + "can be used to collapse the desired node. If ``nodeInstance`` is not "
            + "specified, first node found with desired ``nodeIdentifier`` is collapsed. \n\n"
            + "Node instance must be specified using ``#`` before the desired number.\n"
            + "*N.B.* ``#`` is a special character and must be escaped using ``\\``.\n\n"
            + "Example:\n"
            + "| `Collapse Tree Node` | mytree | Root|Folder | \\#2 | # collapses 3rd node with specified"
            + " ``nodeIdentifier`` |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "nodeInstance="})
    public void collapseTreeNode(String identifier, String nodeIdentifier, String nodeInstance) {
        if (nodeInstance != null && !nodeInstance.equals("None")) {
            TreePath selectionPath = treeOperator(identifier).getDuplicatedNodeInstance(nodeIdentifier, Integer.parseInt(nodeInstance.split("#")[1]));
            treeOperator(identifier).collapse(selectionPath);
        }
        treeOperator(identifier).collapse(nodeIdentifier);
    }

    @RobotKeyword("Expands a node in a tree.\n\n"
            + "Argument ``nodeIdentifier`` can be either tree path (i.e visible labels of nodes to be expanded), "
            + "or index of the expanded node element. Nested structures can only be expanded "
            + "using the tree path syntax.\n\nExamples:\n"
            + "| `Expand Tree Node` | myTree | Root|Folder |\n"
            + "| `Expand Tree Node` | myTree | 3 |\n"
            + "In case of existence of multiple nodes with the same node identifier, ``nodeInstance`` "
            + "can be used to expand the desired node. If ``nodeInstance`` is not "
            + "specified, first node found with desired ``nodeIdentifier`` is expanded. \n\n"
            + "Node instance must be specified using ``#`` before the desired number.\n"
            + "*N.B.* ``#`` is a special character and must be escaped using ``\\``.\n\n"
            + "Example:\n"
            + "| `Expand Tree Node` | mytree | Root|Folder | \\#2 | # expands 3rd node with specified"
            + " ``nodeIdentifier`` |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "nodeInstance="})
    public void expandTreeNode(String identifier, String nodeIdentifier, String nodeInstance) {
        if (nodeInstance != null && !nodeInstance.equals("None")) {
            TreePath selectionPath = treeOperator(identifier).getDuplicatedNodeInstance(nodeIdentifier, Integer.parseInt(nodeInstance.split("#")[1]));
            treeOperator(identifier).expand(selectionPath);
        } else {
            treeOperator(identifier).expand(nodeIdentifier);
        }
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
            + "Does not clear earlier selections.\n\n"
            + "Example:\n"
            + "| `Select Tree Node` | myTree | Root|Folder |\n"
            + "Any number of node identifiers can be provided to select multiple nodes at once:\n"
            + "| `Select Tree Node` | myTree | Root|Folder | Root|Folder2 | Root|Folder3 |\n\n"
            + "``nodeInstance`` specifies n-th node to choose if several nodes have the same node "
            + "identifier. Node instance must be specified using ``#`` before the desired number."
            + "*N.B.* ``#`` is a special character and must be escaped using ``\\``.\n"
            + "If ``nodeInstance`` is not specified then the *first node* "
            + "with the corresponding ``nodeIdentifier`` will be selected.\n"
            + "If ``additionalNodeIdentifiers`` is specified ``nodeInstance`` will be set to ``0`` "
            + "and the keyword will work by selecting the first node found that has specified ``nodeIdentifier``.\n\n"
            + "| `Select Tree Node` | myTree | Folder |  \\#2 |  | # selects 3rd node which has the specified "
            + "``nodeIdentifier`` | \n"
            + "| `Select Tree Node` | mytree | Folder | Folder2 |  | # if ``nodeInstance`` is not specified"
            + " when using `additionalNodeIdentifiers` it will automatically select 1st element "
            + "found that match ``nodeIdentifier`` | \n"
            + "| `Select Tree Node` | mytree | Folder | \\#1 | Folder2 | # selects 2nd node with "
            + "``nodeIdentifier`` and first occurrence of `Folder2` |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "nodeInstance=", "*additionalNodeIdentifiers"})
    public void selectTreeNode(String identifier, String nodeIdentifier, String nodeInstance, ArrayList<String> additionalNodeIdentifiers) {
        TreeOperator treeOperator = treeOperator(identifier);
        if (nodeInstance != null && nodeInstance != "None") {
            if ((nodeInstance).contains("#")) {
                TreePath selectionPath = treeOperator.getDuplicatedNodeInstance(nodeIdentifier, Integer.parseInt(nodeInstance.split("#")[1]));
                treeOperator.addSelectionPath(selectionPath);
            } else {
                additionalNodeIdentifiers.add(nodeInstance);
            }
        } else {
            treeOperator.addSelection(nodeIdentifier);
        }
        for (String node : additionalNodeIdentifiers) {
            treeOperator.addSelection(node);
        }
    }

    @RobotKeywordOverload
    public void selectTreeNode(String identifier, String nodeIdentifier, String arg) {
        if ((arg.toString()).contains("#")){
            selectTreeNode(identifier, nodeIdentifier, (String) arg, new ArrayList<String>());
        } else {
            ArrayList<String> nodes = new ArrayList<>();
            nodes.add(arg);
            selectTreeNode(identifier, nodeIdentifier, "None", nodes);
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
            + "| `Click On Tree Node` | myTree | Root|Folder | 2 | # doubleclicks on node |\n\n"
            + "In case of existence of multiple nodes with the same node identifier, ``nodeInstance`` "
            + "can be used to click the desired node. If ``nodeInstance`` is not "
            + "specified, first node found with desired ``nodeIdentifier`` is clicked. \n\n"
            + "Node instance must be specified using ``#`` before the desired number.\n"
            + "*N.B.* ``#`` is a special character and must be escaped using ``\\``.\n\n"
            + "Example:\n"
            + "| `Click On Tree Node` | mytree | Root|Folder | \\#3 |   | # click 4th node with specified "
            + "``nodeIdentifier`` | \n"
            + "| `Click On Tree Node` | mytree | Root|Folder | \\#2 | 4 | # clicks 4 times the 3rd node "
            + "with specified ``nodeIdentifier`` |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "nodeInstance=", "clickCount=1"})
    public void clickOnTreeNode(String identifier, String nodeIdentifier, String nodeInstance, int clickCount) {
        if (nodeInstance != null && !nodeInstance.equals("None")) {
            TreePath selectionPath = treeOperator(identifier).getDuplicatedNodeInstance(nodeIdentifier, Integer.parseInt(nodeInstance.split("#")[1]));
            treeOperator(identifier).clickOnNode(selectionPath, clickCount);
        } else {
            treeOperator(identifier).clickOnNode(nodeIdentifier, clickCount);
        }
    }

    @RobotKeywordOverload
    public void clickOnTreeNode(String identifier, String nodeIdentifier, String arg) {
        if ((arg).contains("#")){
            clickOnTreeNode(identifier, nodeIdentifier, (String) arg, 1);
        } else {
            clickOnTreeNode(identifier, nodeIdentifier, "None", Integer.parseInt(arg));
        }
    }

    @RobotKeyword("Fails if the tree node is collapsed.\n"
            + "Optionally, you can set jemmy timeout, default value being None. It will automatically select the right timeout.\n"
            + "See `Set Jemmy Timeout` keyword for more information about jemmy timeouts.\n\n"
            + "Example:\n"
            + "| `Tree Node Should Be Expanded` | myTree | Root|Folder |\n"
            + "| `Tree Node Should Be Expanded` | myTree | Root|Folder | 4 |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "nodeInstance=","jemmyTimeout="})
    public void treeNodeShouldBeExpanded(String identifier, String nodeIdentifier, String nodeInstance, String jemmyTimeout) {
        if (jemmyTimeout != "None") {
            old_time = timeout.setJemmyTimeout("JTreeOperator.WaitNodeExpandedTimeout", jemmyTimeout);
        }
        try {
            boolean isExpanded;
            if (!nodeInstance.equals("None")) {
                TreePath selectionPath = treeOperator(identifier).getDuplicatedNodeInstance(nodeIdentifier, Integer.parseInt(nodeInstance.split("#")[1]));
                isExpanded = treeOperator(identifier).isExpanded(selectionPath);
            } else {
                isExpanded = treeOperator(identifier).isExpanded(nodeIdentifier);
            }
            Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not expanded.", isExpanded);
        } finally {
            if (jemmyTimeout != "None") timeout.setJemmyTimeout("JTreeOperator.WaitNodeExpandedTimeout", Long.toString(old_time));
        }
    }

    @RobotKeywordOverload
    public void treeNodeShouldBeExpanded(String identifier, String nodeIdentifier, String arg) {
        if (arg.contains("#")) {
            treeNodeShouldBeExpanded(identifier, nodeIdentifier, arg, "None");
        } else {
            treeNodeShouldBeExpanded(identifier, nodeIdentifier, "None", arg);
        }
    }

    @RobotKeywordOverload
    public void treeNodeShouldBeExpanded(String identifier, String nodeIdentifier) {
        treeNodeShouldBeExpanded(identifier, nodeIdentifier, "None", "None");
    }

    @RobotKeyword("Fails if the tree node is expanded.\n"
            + "Optionally, you can set jemmy timeout, default value being None. It will automatically select the right timeout.\n"
            + "See `Set Jemmy Timeout` keyword for more information about jemmy timeouts.\n\n"
            + "Example:\n"
            + "| `Tree Node Should Be Collapsed` | myTree | Root|Folder |\n"
            + "| `Tree Node Should Be Collapsed` | myTree | Root|Folder | 4 |\n\n"
            + "In case of existence of multiple nodes with the same node identifier, ``nodeInstance`` "
            + "can be used to check the desired node. If ``nodeInstance`` is not "
            + "specified, first node found with desired ``nodeIdentifier`` is checked. \n\n"
            + "Node instance must be specified using ``#`` before the desired number.\n"
            + "*N.B.* ``#`` is a special character and must be escaped using ``\\``.\n\n"
            + "Example:\n"
            + "| `Tree Node Should Be Collapsed` | mytree | Root|Folder | \\#3 |   | # checks 4th node with specified "
            + "``nodeIdentifier`` | \n"
            + "| `Tree Node Should Be Collapsed` | mytree | Root|Folder | \\#2 | 4 | # wait 4 seconds for the 3rd node "
            + "with specified ``nodeIdentifier`` to be collapsed |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "nodeInstance=", "jemmyTimeout="})
    public void treeNodeShouldBeCollapsed(String identifier, String nodeIdentifier, String nodeInstance, String jemmyTimeout) {
        if (jemmyTimeout != "None") {
            old_time = timeout.setJemmyTimeout("JTreeOperator.WaitNodeExpandedTimeout", jemmyTimeout);
        }
        try {
            boolean isCollapsed;
            if (!nodeInstance.equals("None")) {
                TreePath selectionPath = treeOperator(identifier).getDuplicatedNodeInstance(nodeIdentifier, Integer.parseInt(nodeInstance.split("#")[1]));
                isCollapsed = treeOperator(identifier).isCollapsed(selectionPath);
            } else {
                isCollapsed = treeOperator(identifier).isCollapsed(nodeIdentifier);
            }
            Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not collapsed.", isCollapsed);
        } finally {
            if (jemmyTimeout != "None") timeout.setJemmyTimeout("JTreeOperator.WaitNodeExpandedTimeout", Long.toString(old_time));
        }
    }

    @RobotKeywordOverload
    public void treeNodeShouldBeCollapsed(String identifier, String nodeIdentifier, String arg) {
        if (arg.contains("#")) {
            treeNodeShouldBeCollapsed(identifier, nodeIdentifier, arg, "None");
        } else {
            treeNodeShouldBeCollapsed(identifier, nodeIdentifier, "None", arg);
        }
    }

    @RobotKeywordOverload
    public void treeNodeShouldBeCollapsed(String identifier, String nodeIdentifier) {
        treeNodeShouldBeCollapsed(identifier, nodeIdentifier, "None", "None");
    }

    @RobotKeyword("Sets a tree node as unselected.\n\n"
            + "Example:\n"
            + "| `Unselect Tree Node` | myTree | Root|Folder |\n\n"
            + "``nodeInstance`` specifies n-th node to unselect if several nodes have the same node "
            + "identifier. Node instance must be specified using ``#`` before the desired number."
            + "*N.B.* ``#`` is a special character and must be escaped using ``\\``.\n"
            + "If ``nodeInstance`` is not specified then the *first node* "
            + "with the corresponding ``nodeIdentifier`` will be selected.\n"
            + "| `Unselect Tree Node` | myTree | Root|Folder | \\#1 | # unselects 2nd node with the"
            + " specified ``nodeIdentifier`` |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "nodeInstance="})
    public void unselectTreeNode(String identifier, String nodeIdentifier, String nodeInstance) {
        if (nodeInstance != null && !nodeInstance.equals("None")) {
            TreePath selectionPath = treeOperator(identifier).getDuplicatedNodeInstance(nodeIdentifier, Integer.parseInt(nodeInstance.split("#")[1]));
            treeOperator(identifier).removeSelection(selectionPath);
        } else {
            treeOperator(identifier).removeSelection(nodeIdentifier);
        }
    }

    @RobotKeyword("Fails if the node has child nodes.\n"
            + "Optionally, you can set jemmy timeout, default value being None. It will automatically select the right timeout.\n"
            + "See `Set Jemmy Timeout` keyword for more information about jemmy timeouts.\n\n"
            + "Example:\n"
            + "| `Tree Node Should Be Leaf` | myTree | Root|Folder |\n"
            + "| `Tree Node Should Be Leaf` | myTree | Root|Folder | 4 |\n\n"
            + "``nodeInstance`` checks n-th node if it is a leaf, in case several nodes have the same node "
            + "identifier. Node instance must be specified using ``#`` before the desired number."
            + "*N.B.* ``#`` is a special character and must be escaped using ``\\``.\n"
            + "If ``nodeInstance`` is not specified then the *first node* "
            + "with the corresponding ``nodeIdentifier`` will be checked.\n"
            + "| `Tree Node Should Be Leaf` | myTree | Root|Folder | \\#1 | # checks if 2nd node with the"
            + " specified ``nodeIdentifier`` is a leaf |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "nodeInstance=", "jemmyTimeout="})
    public void treeNodeShouldBeLeaf(String identifier, String nodeIdentifier, String nodeInstance, String jemmyTimeout) {
        if (!jemmyTimeout.equals("None")) {
            old_time = timeout.setJemmyTimeout("JTreeOperator.WaitNodeExpandedTimeout", jemmyTimeout);
        }
        try {
            boolean isLeaf = isLeafNode(identifier, nodeIdentifier, nodeInstance);
            Assert.assertTrue("Tree node '" + nodeIdentifier + "' is not leaf.", isLeaf);
        } finally {
            if (jemmyTimeout != null && !jemmyTimeout.equals("None"))
                timeout.setJemmyTimeout("JTreeOperator.WaitNodeExpandedTimeout", Long.toString(old_time));
        }
    }

    @RobotKeywordOverload
    public void treeNodeShouldBeLeaf(String identifier, String nodeIdentifier, String arg) {
        if (arg.contains("#")) {
            treeNodeShouldBeLeaf(identifier, nodeIdentifier, arg, "None");
        } else {
            treeNodeShouldBeLeaf(identifier, nodeIdentifier, "None", arg);
        }
    }

    @RobotKeywordOverload
    public void treeNodeShouldBeLeaf(String identifier, String nodeIdentifier) {
        treeNodeShouldBeLeaf(identifier, nodeIdentifier, "None", "None");
    }

    @RobotKeyword("Fails if the node doesn't have child nodes.\n"
            + "Optionally, you can set jemmy timeout, default value being None. It will automatically select the right timeout.\n"
            + "See `Set Jemmy Timeout` keyword for more information about jemmy timeouts.\n\n"
            + "Example:\n"
            + "| `Tree Node Should Not Be Leaf` | myTree | Root|Folder |\n"
            + "| `Tree Node Should Not Be Leaf` | myTree | Root|Folder | 4 |\n"
            + "``nodeInstance`` checks n-th node if it is not a leaf, in case several nodes have the same node "
            + "identifier. Node instance must be specified using ``#`` before the desired number."
            + "*N.B.* ``#`` is a special character and must be escaped using ``\\``.\n"
            + "If ``nodeInstance`` is not specified then the *first node* "
            + "with the corresponding ``nodeIdentifier`` will be checked.\n"
            + "| `Tree Node Should Not Be Leaf` | myTree | Root|Folder | \\#1 |   | # checks if 2nd node with the"
            + " specified ``nodeIdentifier`` is not leaf |\n"
            + "| `Tree Node Should Not Be Leaf` | myTree | Root|Folder | \\#1 | 4 | # checks for 4 seconds if 2nd "
            + "node with the specified ``nodeIdentifier`` is not leaf |\n")
    @ArgumentNames({"identifier", "nodeIdentifier", "nodeInstance=", "jemmyTimeout="})
    public void treeNodeShouldNotBeLeaf(String identifier, String nodeIdentifier, String nodeInstance, String jemmyTimeout) {
        if (jemmyTimeout != null && !jemmyTimeout.equals("None")) {
            old_time = timeout.setJemmyTimeout("JTreeOperator.WaitNodeExpandedTimeout", jemmyTimeout);
        }
        try {
            boolean isLeaf = isLeafNode(identifier, nodeIdentifier, nodeInstance);
            Assert.assertFalse("Tree node '" + nodeIdentifier + "' is leaf.", isLeaf);
        } finally {
            if (jemmyTimeout != null && !jemmyTimeout.equals("None"))
                timeout.setJemmyTimeout("JTreeOperator.WaitNodeExpandedTimeout", Long.toString(old_time));
        }
    }


    @RobotKeywordOverload
    public void treeNodeShouldNotBeLeaf(String identifier, String nodeIdentifier, String arg) {
        if (arg.contains("#")) {
            treeNodeShouldNotBeLeaf(identifier, nodeIdentifier, arg, "None");
        } else {
            treeNodeShouldNotBeLeaf(identifier, nodeIdentifier, "None", arg);
        }
    }

    @RobotKeywordOverload
    public void treeNodeShouldNotBeLeaf(String identifier, String nodeIdentifier) {
        treeNodeShouldNotBeLeaf(identifier, nodeIdentifier, "None", "None");
    }

    private boolean isLeafNode(String identifier, String nodeIdentifier, String nodeInstance) {
        if (nodeInstance != null && !nodeInstance.equals("None")) {
            TreePath selectionPath = treeOperator(identifier).getDuplicatedNodeInstance(nodeIdentifier, Integer.parseInt(nodeInstance.split("#")[1]));
            return treeOperator(identifier).isLeaf(selectionPath);
        } else {
            return treeOperator(identifier).isLeaf(nodeIdentifier);
        }
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
            + "| `Lists Should Be Equal` | ${expectedElements} | ${actualElements} | # This keyword comes from Collections library |\n"
            + "If ``nodeIdentifier`` is not given or is ``$EMPTY``, all the root's children will be returned.\n")
    @ArgumentNames({"identifier", "*nodeIdentifier"})
    public List<String> getTreeNodeChildNames(String identifier, String[] nodeIdentifier) {
        String nodeId = nodeIdentifier.length == 0 ? "" : nodeIdentifier[0];
        Collection<String> childNames = treeOperator(identifier).getTreeNodeChildNames(nodeId);
        return new ArrayList<String>(childNames);
    }

    @RobotKeyword("Returns a list with the selected paths of a chosen tree.\n\n"
            + "Example:\n"
            + "| ${expectedElements}= | `Create List` | firstSelectedPath | secondSelectedPath |\n"
            + "| ${chosen_paths} = | `Get Selection Paths` | myTree |\n"
            + "| `Lists Should Be Equal` | ${expectedElements} | ${actualElements} | # This keyword comes from Collections library |\n")
    @ArgumentNames({"identifier"})
    public List<String> getSelectionPaths(String identifier) {
        TreePath[] selectionPaths = treeOperator(identifier).getSelectionPaths();
        ArrayList<String> paths = new ArrayList<>();
        for (TreePath selectionPath : selectionPaths) {
            String path = selectionPath.toString();
            List<String> replaceItems = Arrays.asList("[", "]");
            for (String s: replaceItems) {
                path = path.replace(s, "");
            }
            path = path.replace(", ", "|");
            paths.add(path);
        }
        return paths;
    }
}
