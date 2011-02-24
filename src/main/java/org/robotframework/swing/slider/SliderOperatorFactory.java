package org.robotframework.swing.slider;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JSliderOperator;
import org.robotframework.swing.chooser.ByNameComponentChooser;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;

public class SliderOperatorFactory extends IdentifierParsingOperatorFactory<SliderOperator> {

    @Override
    public SliderOperator createOperatorByIndex(int index) {
        return new DefaultSliderOperator(new JSliderOperator((ContainerOperator) Context.getContext(), index));
    }

    @Override
    public SliderOperator createOperatorByName(String name) {
        return new DefaultSliderOperator(new JSliderOperator((ContainerOperator) Context.getContext(), new ByNameComponentChooser(name)));
    }
}
