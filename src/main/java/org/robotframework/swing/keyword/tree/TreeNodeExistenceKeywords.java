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
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.tree.TreeNodeExistenceResolver;
import org.robotframework.swing.tree.TreeSupport;


@RobotKeywords
public class TreeNodeExistenceKeywords extends TreeSupport {
    @RobotKeyword("Fails if the tree node does not exist.\n\n"
        + "Example:\n"
        + "| `Tree Node Should Exist` | myTree | Root|Folder |\n")
    @ArgumentNames({"identifier", "nodeIdentifier"})
    public void treeNodeShouldExist(String identifier, String nodeIdentifier) {
        boolean treeNodeExists = createExistenceResolver(identifier).treeNodeExists(nodeIdentifier);
        Assert.assertTrue("Tree node '" + nodeIdentifier + "' doesn't exist.", treeNodeExists);
    }

    @RobotKeyword("Fails if the tree node exists.\n\n"
        + "Example:\n"
        + "| `Tree Node Should Not Exist` | myTree | Root|Folder |\n")
    @ArgumentNames({"identifier", "nodeIdentifier"})
    public void treeNodeShouldNotExist(String identifier, String nodeIdentifier) {
        boolean treeNodeExists = createExistenceResolver(identifier).treeNodeExists(nodeIdentifier);
        Assert.assertFalse("Tree node '" + nodeIdentifier + "' exists.", treeNodeExists);
    }

    TreeNodeExistenceResolver createExistenceResolver(String treeIdentifier) {
        return new TreeNodeExistenceResolver(treeOperator(treeIdentifier));
    }
}
