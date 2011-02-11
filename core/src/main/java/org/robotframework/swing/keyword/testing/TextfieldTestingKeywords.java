package org.robotframework.swing.keyword.testing;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.textcomponent.TextComponentOperator;
import org.robotframework.swing.textcomponent.TextComponentOperatorFactory;

@RobotKeywords
public class TextfieldTestingKeywords {
    @RobotKeyword
    public void disableTextField(String identifier) {
        createOperator(identifier).setEnabled(false);
    }

    @RobotKeyword
    public void enableTextField(String identifier) {
        createOperator(identifier).setEnabled(true);
    }

    @RobotKeyword
    public void selectTextFieldContents(String identifier) {
        TextComponentOperator textFieldOperator = createOperator(identifier);
        textFieldOperator.makeComponentVisible();
        textFieldOperator.selectAll();
    }
    
    private TextComponentOperator createOperator(String identifier) {
        return new TextComponentOperatorFactory().createOperator(identifier);
    }
}
