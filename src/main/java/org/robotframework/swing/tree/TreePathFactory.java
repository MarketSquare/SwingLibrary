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

import javax.swing.tree.TreePath;

import org.netbeans.jemmy.TimeoutExpiredException;
import org.robotframework.swing.arguments.IdentifierHandler;

public class TreePathFactory extends IdentifierHandler<TreePath> {
    private final TreeOperator treeOperator;

    public TreePathFactory(TreeOperator treeOperator) {
        this.treeOperator = treeOperator;
    }

    @Override
    public TreePath indexArgument(int row) {
        TreePath pathForRow = treeOperator.getPathForRow(row);
        if (pathForRow == null) {
            throw new TimeoutExpiredException("Couldn't find tree path for row '" + row + "'");
        }
        return pathForRow;
    }

    @Override
    public TreePath nameArgument(String nodePath) {
        return treeOperator.findPath(nodePath);
    }

    public TreePath createTreePath(String nodeIdentifier) {
        return parseArgument(nodeIdentifier);
    }
}
