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

package org.robotframework.swing.textcomponent;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.swing.chooser.ByNameComponentChooser;
import org.robotframework.swing.common.ProxyHandler;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.DefaultContextVerifyingOperatorFactory;


public class TextComponentOperatorFactory extends DefaultContextVerifyingOperatorFactory<TextComponentOperator> {
    @Override
    public TextComponentOperator createOperatorByIndex(int index) {
        return createProxy(new SwingTextComponentOperator((ContainerOperator) Context.getContext(), index));
    }

    @Override
    public TextComponentOperator createOperatorByName(String name) {
        return createProxy(new SwingTextComponentOperator((ContainerOperator) Context.getContext(), new ByNameComponentChooser(name)));
    }

    @Override
    public TextComponentOperator indexAWTArgument(int index) {
        return  createProxy(new AWTTextComponentOperator((ContainerOperator) Context.getContext(), index));
    }

    @Override
    public TextComponentOperator nameAWTArgument(String name) {
        return createProxy(new AWTTextComponentOperator((ContainerOperator) Context.getContext(), new ByNameComponentChooser(name)));
    }

    private TextComponentOperator createProxy(Object operator) {
        return (TextComponentOperator) ProxyHandler.createProxy(TextComponentOperator.class,
                                                                operator);
    }
}
