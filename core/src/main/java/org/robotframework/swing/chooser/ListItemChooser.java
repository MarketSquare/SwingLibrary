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


package org.robotframework.swing.chooser;

import org.netbeans.jemmy.operators.JListOperator;
import org.springframework.util.ObjectUtils;

public class ListItemChooser implements org.netbeans.jemmy.operators.JListOperator.ListItemChooser {
    private final String name;

    public ListItemChooser(String itemIdentifier) {
        this.name = itemIdentifier;
    }

    public boolean checkItem(JListOperator operator, int index) {
        String item = operator.getModel().getElementAt(index).toString();
        return ObjectUtils.nullSafeEquals(name, item);
    }

    public String getDescription() {
        return name;
    }
}