package org.robotframework.swing.togglebutton;

import org.laughingpanda.jretrofit.Retrofit;
import org.netbeans.jemmy.operators.JToggleButtonOperator;
import org.robotframework.swing.button.AbstractButtonOperator;
import org.robotframework.swing.chooser.ByNameOrTextComponentChooser;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.DefaultContextVerifier;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;

public class ToggleButtonOperatorFactory extends IdentifierParsingOperatorFactory<AbstractButtonOperator> {
    private IContextVerifier contextVerifier = new DefaultContextVerifier();

    @Override
    public AbstractButtonOperator createOperator(String identifier) {
        contextVerifier.verifyContext();
        return super.createOperator(identifier);
    }

    @Override
    public AbstractButtonOperator createOperatorByIndex(int index) {
        return coerceToAbstractButtonOperator(new JToggleButtonOperator(Context.getContext(), index));
    }

    @Override
    public AbstractButtonOperator createOperatorByName(String name) {
        return coerceToAbstractButtonOperator(new JToggleButtonOperator(Context.getContext(), new ByNameOrTextComponentChooser(name)));
    }

    private AbstractButtonOperator coerceToAbstractButtonOperator(JToggleButtonOperator toggleButtonOperator) {
        return (AbstractButtonOperator) Retrofit.partial(toggleButtonOperator, AbstractButtonOperator.class);
    }
}
