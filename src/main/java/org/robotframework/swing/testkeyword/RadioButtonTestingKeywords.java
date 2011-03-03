package org.robotframework.swing.testkeyword;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JRadioButtonOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.chooser.ByNameOrTextComponentChooser;
import org.robotframework.swing.context.Context;

@RobotKeywords
public class RadioButtonTestingKeywords {
    @RobotKeyword
    public void enableRadioButton(String identifier) {
        JRadioButtonOperator operator = createOperator(identifier);
        operator.setEnabled(true);
    }
    
    @RobotKeyword
    public void disableRadioButton(String identifier) {
        JRadioButtonOperator operator = createOperator(identifier);
        operator.setEnabled(false);
    }
    
    private JRadioButtonOperator createOperator(String nameOrText) {
        return new JRadioButtonOperator((ContainerOperator) Context.getContext(), new ByNameOrTextComponentChooser(nameOrText));
    }
}
