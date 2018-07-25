package org.robotframework.swing.keyword.slider;

import org.junit.Assert;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.slider.SliderOperator;
import org.robotframework.swing.slider.SliderOperatorFactory;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;

@RobotKeywords
public class SliderKeywords {
    private IdentifierParsingOperatorFactory<SliderOperator> operatorFactory = new SliderOperatorFactory();
    private IComponentConditionResolver existenceResolver = new ComponentExistenceResolver(operatorFactory);

    @RobotKeyword("Fails if slider does not exist within current context.\n\n"
            + "Example:\n"
            + "| `Slider Should Exist` | mySlider |\n")
    @ArgumentNames({"identifier"})
    public void sliderShouldExist(String identifier) {
        Assert.assertTrue("Slider '" + identifier + "' doesn't exist.", existenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Fails if slider exists within current context.\n\n"
            + "Example:\n"
            + "| `Slider Should Not Exist` | mySlider |\n")
    @ArgumentNames({"identifier"})
    public void sliderShouldNotExist(String identifier) {
        Assert.assertFalse("Slider '" + identifier + "' exists.", existenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Uses current context to search for a slider and when found, "
            + "returns its current value.\n\n"
            + "Example:\n"
            + "| ${sliderValue}=  | Get Slider Value | mySlider    |\n"
            + "| `Should Be Equal As Integers` | 12     | ${sliderValue} |\n")
    @ArgumentNames({"identifier"})
    public Object getSliderValue(String identifier) {
        return sliderOperator(identifier).getValue();
    }

    private SliderOperator sliderOperator(String identifier) {
        return operatorFactory.createOperator(identifier);
    }

    @RobotKeyword("Sets the value for the slider found from the current context.\n\n"
            + "Example:\n"
            + "| `Set Slider Value` | mySlider  | 7  |\n")
    @ArgumentNames({"identifier", "value"})
    public void setSliderValue(String identifier, String value) {
        sliderOperator(identifier).setValue(Integer.parseInt(value));
    }
}
