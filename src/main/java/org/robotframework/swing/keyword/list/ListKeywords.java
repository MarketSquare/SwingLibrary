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

package org.robotframework.swing.keyword.list;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.common.IdentifierSupport;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.list.ListOperator;
import org.robotframework.swing.list.ListOperatorFactory;
import org.robotframework.swing.table.TableOperator;
import org.robotframework.swing.util.SwingInvoker;

@RobotKeywords
public class ListKeywords extends IdentifierSupport {
    private OperatorFactory<ListOperator> operatorFactory = new ListOperatorFactory();

    @RobotKeyword("Clears selection from list.\n\n" + "Example:\n"
            + "| `Clear Selection From List` | myList |\n")
    @ArgumentNames({ "identifier" })
    public void clearSelectionFromList(String identifier) {
        createOperator(identifier).clearSelection();
    }

    @RobotKeyword("Selects an item from the list.\n\n"
            + "Examples:\n"
            + "| `Select From List` | myList | myItem | # selects 'myItem'                   |\n"
            + "| `Select From List` | myList | 0      | # selects the first item in the list |\n\n"
            + "| `Select From List` | myList | myItem | 2 | # doubleclicks on item |\n"
            + "Any number of list item identifiers can be provided to select multiple items at once:\n"
            + "| `Select From List` | myList | myItem | anotherItem | yetAnother |\n")
    @ArgumentNames({ "identifier", "listItemIdentifier",
            "*additionalItemIdentifiers" })
    public void selectFromList(final String identifier,
            final String listItemIdentifier,
            final String[] additionalItemIdentifiers) {
        final ListOperator operator = createOperator(identifier);
        // http://code.google.com/p/robotframework-swinglibrary/issues/detail?id=192
        SwingInvoker.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                operator.selectItems(intoList(listItemIdentifier,
                        additionalItemIdentifiers));

            }
        });

    }

    private List<String> intoList(final String listItemIdentifier,
            String[] additionalItemIdentifiers) {
        final List<String> itemIdentifiers = new ArrayList<String>() {
            {
                add(listItemIdentifier);
            }
        };
        CollectionUtils.addAll(itemIdentifiers, additionalItemIdentifiers);
        return itemIdentifiers;
    }

    @RobotKeyword("Clicks on a list item.\n\n"
            + "Examples:\n"
            + "| `Click On List Item` | myList | myItem |\n"
            + "| `Click On List Item` | myList | 0      | \n\n"
            + "An optional click count parameter can be provided for example if a double click is required.\n"
            + "Default click count is one:\n"
            + "| `Click On List Item` | myList | myItem | 2 | # doubleclicks on item |\n")
    @ArgumentNames({ "identifier", "listItemIdentifier", "clickCount=1" })
    public void clickOnListItem(String identifier, String listItemIdentifier, int clickCount) {
        createOperator(identifier).clickOnItem(listItemIdentifier, clickCount);
    }

    @RobotKeywordOverload
    public void clickOnListItem(String identifier, String listItemIdentifier) {
        clickOnListItem(identifier, listItemIdentifier, 1);
    }

    @RobotKeyword("Returns the item that's currently selected in the list.\n\n"
            + "Example:\n"
            + "| ${listItem}=      | `Get Selected Value From List` | myList      |\n"
            + "| `Should Be Equal` | Expected Item                  | ${listItem} |\n")
    @ArgumentNames({ "identifier" })
    public Object getSelectedValueFromList(String identifier) {
        return createOperator(identifier).getSelectedValue();
    }

    @RobotKeyword("Returns the number of items contained in list.\n\n"
            + "Example:\n"
            + "| ${listItemCount}=    | `Get List Item Count` | myList      |\n"
            + "| `Should Be Equal As Integers` | 2 | ${listItemCount} |\n")
    @ArgumentNames({ "identifier" })
    public int getListItemCount(String identifier) {
        return createOperator(identifier).getSize();
    }

    @RobotKeyword("Selects all list items.\n\n" + "Example:\n"
            + "| `Select All List Items` | My List |\n")
    @ArgumentNames({ "identifier" })
    public void selectAllListItems(String identifier) {
        createOperator(identifier).selectAll();
    }

    @RobotKeyword("Returns all values from a list.\n\n" + "Example:\n"
            + "| ${values}= | `Get List Values` | myList |\n"
            + "| `Should Be Equal`  | ${values} | one, two, three |\n")
    @ArgumentNames({ "identifier" })
    public List<String> getListValues(String identifier) {
        return createOperator(identifier).getListValues();
    }

    @RobotKeyword("Fails if value not in list.\n\n" + "Example:\n"
            + "| `List Should Contain` | myList | item |\n")
    @ArgumentNames({ "identifier", "value" })
    public void listShouldContain(String identifier, String value) {
        Assert.assertTrue("List " + identifier + " does not contain " + value,
                getListValues(identifier).contains(value));
    }

    @RobotKeyword("Fails if value is in list.\n\n" + "Example:\n"
            + "| `List Should Not Contain` | myList | item |\n")
    @ArgumentNames({ "identifier", "value" })
    public void listShouldNotContain(String identifier, String value) {
        Assert.assertTrue("List " + identifier + " contains " + value,
                !getListValues(identifier).contains(value));
    }
    
    @RobotKeyword("Selects an item from a list item popup.\n"
            + "Separator for items is ``|``.\n\n"
            + "Example:\n"
            + "| `Select From List Item Popup Menu` | myList | listItem | File|Exit | ")
    @ArgumentNames({"identifier", "listItemIdentifier", "menuPath"})
    public void selectFromListItemPopupMenu(String identifier, String listItemIdentifier, String menuPath) {    	
        JMenuItemOperator menuItem = getPopupMenuItem(identifier, listItemIdentifier, menuPath);
        menuItem.push();   
    }
    
    private JMenuItemOperator getPopupMenuItem(String identifier, String listItemIdentifier, String menuPath) {
        ListOperator listOperator = createOperator(identifier);
        JPopupMenuOperator popupMenuOperator = listOperator.callPopupOnListItem(listItemIdentifier);
        return popupMenuOperator.showMenuItem(menuPath, new EqualsStringComparator());
    }   
    
    private ListOperator createOperator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }
}
