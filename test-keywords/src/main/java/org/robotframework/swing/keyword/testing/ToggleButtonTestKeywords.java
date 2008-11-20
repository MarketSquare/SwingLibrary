package org.robotframework.swing.keyword.testing;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JToggleButtonOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.chooser.ByNameComponentChooser;
import org.robotframework.swing.context.Context;

@RobotKeywords
public class ToggleButtonTestKeywords {
    @RobotKeyword
    public void selectToggleButton(String identifier) {
        createToggleButtonOperator(identifier).changeSelection(true);
    }

    @RobotKeyword
    public void unSelectToggleButton(String identifier) {
        createToggleButtonOperator(identifier).changeSelection(false);
    }

    private JToggleButtonOperator createToggleButtonOperator(String identifier) {
        return new JToggleButtonOperator((ContainerOperator) Context.getContext(), new ByNameComponentChooser(identifier));
    }
}
