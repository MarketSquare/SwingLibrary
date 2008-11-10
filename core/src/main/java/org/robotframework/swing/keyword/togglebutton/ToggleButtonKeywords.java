package org.robotframework.swing.keyword.togglebutton;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.button.AbstractButtonOperator;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.togglebutton.ToggleButtonOperatorFactory;

@RobotKeywords
public class ToggleButtonKeywords {
    private OperatorFactory<AbstractButtonOperator> operatorFactory = new ToggleButtonOperatorFactory();
    
    @RobotKeyword("Fails if togglebutton is not selected.\n\n"
        + "Example:\n"
        + "| Toggle Button Should Be Selected | _My Toggle Button_ |\n")
    public void toggleButtonShouldBeSelected(String identifier) {
        boolean isSelected = operatorFactory.createOperator(identifier).isSelected();
        Assert.assertTrue("Toggle Button '" + identifier + "' is not selected.", isSelected);
    }
    
    @RobotKeyword("Fails if togglebutton is selected.\n\n"
        + "Example:\n"
        + "| Toggle Button Should Not Be Selected | _My Toggle Button_ |\n")
    public void toggleButtonShouldNotBeSelected(String identifier) {
        boolean isSelected = operatorFactory.createOperator(identifier).isSelected();
        Assert.assertFalse("Toggle Button '" + identifier + "' is selected.", isSelected);
    }
}
