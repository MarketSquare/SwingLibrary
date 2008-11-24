package org.robotframework.swing.keyword.testing;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.button.ButtonOperatorFactory;


@RobotKeywords
public class ButtonTestKeywords {
    private ButtonOperatorFactory operatorFactory = new ButtonOperatorFactory();

    @RobotKeyword
    public void setButtonText(String identifier, String newText) {
        operatorFactory.createOperator(identifier).setText(newText);
    }

    @RobotKeyword
    public void disableButton(String identifier) {
        operatorFactory.createOperator(identifier).setEnabled(false);
    }

    @RobotKeyword
    public void enableButton(String identifier) {
        operatorFactory.createOperator(identifier).setEnabled(true);
    }
}
