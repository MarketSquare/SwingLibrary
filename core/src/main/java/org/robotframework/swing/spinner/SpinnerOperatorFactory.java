package org.robotframework.swing.spinner;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JSpinnerOperator;
import org.robotframework.swing.chooser.ByNameComponentChooser;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;

/**
 * @author Heikki Hulkko
 */
public class SpinnerOperatorFactory extends IdentifierParsingOperatorFactory<SpinnerOperator> {
    @Override
    public SpinnerOperator createOperatorByIndex(int index) {
        return new DefaultSpinnerOperator(new JSpinnerOperator((ContainerOperator) Context.getContext(), index));
    }

    @Override
    public SpinnerOperator createOperatorByName(String name) {
        return new DefaultSpinnerOperator(new JSpinnerOperator((ContainerOperator) Context.getContext(), new ByNameComponentChooser(name)));
    }
}
