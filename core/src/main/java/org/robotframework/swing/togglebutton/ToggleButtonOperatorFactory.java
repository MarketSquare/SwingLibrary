package org.robotframework.swing.togglebutton;

import org.laughingpanda.jretrofit.Retrofit;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JToggleButtonOperator;
import org.robotframework.swing.button.AbstractButtonOperator;
import org.robotframework.swing.chooser.ByNameOrTextComponentChooser;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.DefaultContextVerifyingOperatorFactory;

public class ToggleButtonOperatorFactory extends DefaultContextVerifyingOperatorFactory<AbstractButtonOperator> {
    @Override
    public AbstractButtonOperator createOperatorByIndex(int index) {
        return coerceToAbstractButtonOperator(new JToggleButtonOperator((ContainerOperator) Context.getContext(), index));
    }

    @Override
    public AbstractButtonOperator createOperatorByName(String name) {
        return coerceToAbstractButtonOperator(new JToggleButtonOperator((ContainerOperator) Context.getContext(), new ByNameOrTextComponentChooser(name)));
    }

    private AbstractButtonOperator coerceToAbstractButtonOperator(JToggleButtonOperator toggleButtonOperator) {
        return (AbstractButtonOperator) Retrofit.partial(toggleButtonOperator, AbstractButtonOperator.class);
    }
}
