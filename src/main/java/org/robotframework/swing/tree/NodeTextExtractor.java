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


package org.robotframework.swing.tree;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

import org.laughingpanda.jretrofit.Retrofit;
import org.robotframework.swing.chooser.WithText;
import org.robotframework.swing.common.SmoothInvoker;

public class NodeTextExtractor {
    private final JTree tree;

    public NodeTextExtractor(JTree tree) {
        this.tree = tree;
    }
    
    public String getText(final Object node, final String path) {
        TreePath treePath = new TreePath(path.split("\\|"));
        return getText(node, treePath);
    }
    
    public String getText(final Object node, final TreePath treePath) {
        SmoothInvoker<String> textExctractor = new SmoothInvoker<String>() {
            public Object work() {
                return extractTextFromNode(node, treePath);
            }
        };
        return textExctractor.invoke();
    }
    
    private String extractTextFromNode(Object node, TreePath treePath) {
        try {
            WithText componentWithText = getNodeComponentWithText(node, treePath);
            return componentWithText.getText();
        } catch (Exception e) {
            return node.toString();
        }
    }
    
    private WithText getNodeComponentWithText(Object node, TreePath treePath) {
        Component component = getNodeComponent(node, treePath);
        return coerceToWithText(component);
    }
    
    private Component getNodeComponent(Object node, TreePath treePath) {
        int row = tree.getRowForPath(treePath);
        boolean isLeaf = tree.getModel().isLeaf(node);
        boolean hasFocus = tree.getLeadSelectionRow() == row;
        boolean isSelected = tree.isRowSelected(row);
        boolean isExpanded = tree.isExpanded(row);
        
        TreeCellRenderer cellRenderer = tree.getCellRenderer();
        return cellRenderer.getTreeCellRendererComponent(tree, node, isSelected, isExpanded, isLeaf, row, hasFocus);
    }
    
    private WithText coerceToWithText(Component component) {
        return (WithText) Retrofit.partial(component, WithText.class);
    }
}
