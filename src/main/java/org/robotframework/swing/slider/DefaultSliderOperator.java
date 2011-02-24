package org.robotframework.swing.slider;

import org.netbeans.jemmy.operators.JSliderOperator;

public class DefaultSliderOperator implements SliderOperator {

    private JSliderOperator sliderOperator;

    public DefaultSliderOperator(JSliderOperator sliderOperator) {
        this.sliderOperator = sliderOperator;
    }
    
    public int getValue() {
        return sliderOperator.getValue();
    }

    public void setValue(int value) {
        sliderOperator.setValue(value);
    }
}
