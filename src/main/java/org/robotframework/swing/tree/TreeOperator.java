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

package org.robotframework.swing.tree;

import java.awt.Component;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.Waiter;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.jemmy.operators.JTreeOperator;
import org.robotframework.swing.common.TimeoutCopier;
import org.robotframework.swing.common.TimeoutName;
import org.robotframework.swing.operator.ComponentWrapper;
import org.robotframework.swing.popup.PopupCaller;

public class TreeOperator implements ComponentWrapper {
    protected PopupCaller popupCaller = new PopupCaller();
    protected TreePathFactory treePathFactory = new TreePathFactory(this);
    protected JTreeOperator jTreeOperator;


    public TreeOperator(ContainerOperator containerOperator, ComponentChooser componentChooser) {
        jTreeOperator = new JTreeOperator(containerOperator, componentChooser);
    }

    public TreeOperator(ContainerOperator containerOperator, int index) {
        jTreeOperator = new JTreeOperator(containerOperator, index);
    }

    public TreeOperator(JTreeOperator treeOperator) {
        this.jTreeOperator = treeOperator;
    }

    public JPopupMenu callPopupOnRow(int row) {
        jTreeOperator.selectRow(row);
        jTreeOperator.scrollToRow(row);
        Point pointToClick = jTreeOperator.getPointToClick(row);
        return popupCaller.callPopupOnComponent(jTreeOperator, pointToClick);
    }

    public TreePath findPath(final String treePath) {
        try {
            return (TreePath) treePathWaiterFor(treePath).waitAction(null);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Waiter treePathWaiterFor(final String treePath) {
        Waiter waiter = new Waiter(new TreePathWaitable(jTreeOperator, treePath));
        waiter.setTimeouts(new TimeoutCopier(jTreeOperator, 
                                             TimeoutName.J_TREE_OPERATOR_WAIT_NEXT_NODE_TIMEOUT).getTimeouts());
        return waiter;
    }
    
    public void expand(String nodeIdentifier) {
        expand(createTreePath(nodeIdentifier));
    }
    
    public void expand(TreePath path) {
        jTreeOperator.expandPath(path);
    }
    
    public void collapse(String nodeIdentifier) {
        collapse(createTreePath(nodeIdentifier));
    }
    
    public void collapse(TreePath nodeIdentifier) {
        jTreeOperator.collapsePath(nodeIdentifier);
    }
    
    public void addSelection(String nodeIdentifier) {
        jTreeOperator.addSelectionPath(createTreePath(nodeIdentifier));
    }

    public void addSelectionPath(TreePath nodePath) {
        jTreeOperator.addSelectionPath(nodePath);
    }

    public TreePath getDuplicatedNodeInstance(String nodeIdentifier, Integer duplicatedNodeInstance) {
        TreeNode pn = (TreeNode) jTreeOperator.findPath(nodeIdentifier).getParentPath().getLastPathComponent();
        Object[] nodeList = jTreeOperator.getChildren(pn);
        Object firstNode = jTreeOperator.findPath(nodeIdentifier).getLastPathComponent();
        List<Object> listOfNodesWithSameName = new ArrayList<>();
        for (Object nodetocheck : nodeList) {
            if((firstNode.toString()).equals(nodetocheck.toString()))
                listOfNodesWithSameName.add(nodetocheck);
        }
        Object desiredNode = listOfNodesWithSameName.get(duplicatedNodeInstance);
        return jTreeOperator.getChildPath(jTreeOperator.findPath(nodeIdentifier).getParentPath(), pn.getIndex((TreeNode) desiredNode));
    }

    public void removeSelection(String nodeIdentifier) {
        jTreeOperator.removeSelectionPath(createTreePath(nodeIdentifier));
    }
    
    public boolean isExpanded(String nodeIdentifier) {
        return isExpanded(createTreePath(nodeIdentifier));
    }
    
    public boolean isExpanded(TreePath nodeIdentifier) {
        return jTreeOperator.isExpanded(nodeIdentifier);
    }
    
    public boolean isCollapsed(String nodeIdentifier) {
        return isCollapsed(createTreePath(nodeIdentifier));
    }
    
    public boolean isCollapsed(TreePath nodeIdentifier) {
        return jTreeOperator.isCollapsed(nodeIdentifier);
    }
    
    public boolean isLeaf(String nodeIdentifier) {
        return isLeaf(createTreePath(nodeIdentifier));
    }
    
    public boolean isLeaf(TreePath nodeIdentifier) {
        TreeNode lastPathComponent = (TreeNode) nodeIdentifier.getLastPathComponent();
        return lastPathComponent.isLeaf();
    }
    
    public boolean isPathSelected(String nodeIdentifier) {
        return jTreeOperator.isPathSelected(createTreePath(nodeIdentifier));
    }
    
    public boolean isVisible(String nodeIdentifier) {
        TreePath treePath = createTreePath(nodeIdentifier);
        return jTreeOperator.isVisible(treePath);
    }

    public void clickOnNode(String nodeIdentifier, int clickCount) {
        jTreeOperator.clickOnPath(createTreePath(nodeIdentifier), clickCount);
    }
    
    public JPopupMenuOperator createPopupOperator(String nodeIdentifier) {
        return popupFactory().createOperator(nodeIdentifier);
    }
    
    public JPopupMenuOperator createPopupOperatorOnSelectedNodes() {
        return popupFactory().createOperatorBySelection();
    }
    
    public String getTreeNodeLabel(int index) {
        TreePath pathForRow = jTreeOperator.getPathForRow(index);
        return pathForRow.getLastPathComponent().toString();
    }

    public int getTreeNodeIndex(String nodePath) {
        return jTreeOperator.getRowForPath(findPath(nodePath));
    }

    public Component getSource() {
        return jTreeOperator.getSource();
    }
    
    public TreeModel getModel() {
        return jTreeOperator.getModel();
    }
    
    public TreePath getPathForRow(int i) {
        return jTreeOperator.getPathForRow(i);
    }
    
    public int getRowCount() {
        return jTreeOperator.getRowCount();
    }
    
    public void clearSelection() {
        jTreeOperator.clearSelection();
    }
    
    public boolean isRootVisible() {
        return jTreeOperator.isRootVisible();
    }
    
    public JPopupMenu callPopupOnPath(TreePath treePath) {
        return jTreeOperator.callPopupOnPath(treePath);
    }
    
    public JPopupMenu callPopupOnPaths(TreePath[] treePaths) {
        return jTreeOperator.callPopupOnPaths(treePaths);
    }
    
    public TreePath[] getSelectionPaths() {
        return jTreeOperator.getSelectionPaths();
    }
    
    public void operateOnAllNodes(TreePathAction action) {
        createIterator().operateOnAllNodes(action);
    }

    @SuppressWarnings("unchecked")
    public Collection<String> getTreeNodeChildNames(String nodeIdentifier) {
        final TreePath treePath = createTreePath(nodeIdentifier);
        return CollectionUtils.collect(getChildPaths(treePath), new Transformer() {
            public Object transform(Object input) {
                Object node = ((TreePath) input).getLastPathComponent();
                return new NodeTextExtractor((JTree) getSource()).getText(node, treePath);
            }
        });
    }

    private List<TreePath> getChildPaths(TreePath treePath) {
        return Arrays.asList(jTreeOperator.getChildPaths(treePath));
    }
    
    protected TreeIterator createIterator() {
        return new TreeIterator(jTreeOperator);
    }
    
    protected TreePopupMenuOperatorFactory popupFactory() {
        return new TreePopupMenuOperatorFactory(this);
    }
    
    protected TreePath createTreePath(String nodeIdentifier) {
        return treePathFactory.createTreePath(nodeIdentifier);
    }

    public JTreeOperator getTreeOperator() {
        return jTreeOperator;
    }
}
