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
import org.netbeans.jemmy.operators.JRadioButtonOperator;
import org.robotframework.swing.chooser.ByNameOrTextComponentChooser;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.DefaultContextVerifier;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;

public class RadioButtonOperatorFactory extends IdentifierParsingOperatorFactory<RadioButtonOperator> {
    private IContextVerifier contextVerifier = new DefaultContextVerifier();
    
    @Override
    public RadioButtonOperator createOperator(String identifier) {
        contextVerifier.verifyContext();
        return super.createOperator(identifier);
    }
    
    @Override
    public RadioButtonOperator createOperatorByIndex(int index) {
        return coerceToRadioButtonOperator(new JRadioButtonOperator(Context.getContext(), index));
    }

    @Override
    public RadioButtonOperator createOperatorByName(String name) {
        return coerceToRadioButtonOperator(new JRadioButtonOperator(Context.getContext(), new ByNameOrTextComponentChooser(name)));
    }
    
    private RadioButtonOperator coerceToRadioButtonOperator(JRadioButtonOperator radioButtonOperator) {
        return (RadioButtonOperator) Retrofit.partial(radioButtonOperator, RadioButtonOperator.class);
    }
}
