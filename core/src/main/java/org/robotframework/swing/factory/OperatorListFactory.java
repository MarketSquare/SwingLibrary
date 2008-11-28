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

package org.robotframework.swing.factory;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ComponentOperator;
import org.netbeans.jemmy.operators.Operator;

/**
 * @author Heikki Hulkko
 */
public class OperatorListFactory<T extends Operator> {
    private final ComponentBasedOperatorFactory<T> operatorFactory;
    private final ComponentChooser chooser;

    public OperatorListFactory(ComponentChooser chooser, ComponentBasedOperatorFactory<T> operatorFactory) {
        this.chooser = chooser;
        this.operatorFactory = operatorFactory;
    }

    public List<T> createOperators(Container context) {
        List<T> operators = new ArrayList<T>();
        Component component = null; int i = 0;
        while ((component = findComponent(context, i++)) != null) {
            operators.add(operatorFactory.createOperator(component));
        }
        return operators;
    }

    Component findComponent(Container context, int index) {
        return ComponentOperator.findComponent(context, chooser, index);
    }
    
    public interface ComponentBasedOperatorFactory<T extends Operator> {
        T createOperator(Component component);
    }
}

