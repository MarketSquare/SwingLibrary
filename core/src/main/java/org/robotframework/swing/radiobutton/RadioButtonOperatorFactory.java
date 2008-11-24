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


package org.robotframework.swing.radiobutton;

import org.laughingpanda.jretrofit.Retrofit;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JRadioButtonOperator;
import org.robotframework.swing.button.AbstractButtonOperator;
import org.robotframework.swing.chooser.ByNameOrTextComponentChooser;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.DefaultContextVerifyingOperatorFactory;

public class RadioButtonOperatorFactory extends DefaultContextVerifyingOperatorFactory<AbstractButtonOperator> {
    @Override
    public AbstractButtonOperator createOperatorByIndex(int index) {
        return coerceToRadioButtonOperator(new JRadioButtonOperator((ContainerOperator) Context.getContext(), index));
    }

    @Override
    public AbstractButtonOperator createOperatorByName(String name) {
        return coerceToRadioButtonOperator(new JRadioButtonOperator((ContainerOperator) Context.getContext(), new ByNameOrTextComponentChooser(name)));
    }
    
    private AbstractButtonOperator coerceToRadioButtonOperator(JRadioButtonOperator radioButtonOperator) {
        return (AbstractButtonOperator) Retrofit.partial(radioButtonOperator, AbstractButtonOperator.class);
    }
}
