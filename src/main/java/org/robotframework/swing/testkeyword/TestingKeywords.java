package org.robotframework.swing.testkeyword;

import java.awt.Component;
import java.awt.Container;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.operator.ComponentWrapper;
import org.robotframework.swing.testapp.Delay;


@RobotKeywords
public class TestingKeywords {
    @RobotKeyword
    public void selectEmptyContext() {
        final Container container = new Container();
        Context.setContext(new ComponentWrapper() {
            public Component getSource() {
                return container;
            }
        });
    }

    @RobotKeyword
    public String getCurrentContextSourceAsString() {
        return ((ContainerOperator) Context.getContext()).toStringSource();
    }
    
    @RobotKeyword
    public void setDelay(String delayStr) {
        Delay.delayTimeMillis = Long.parseLong(delayStr);
    }
    
    @RobotKeyword
    public void randomizeDelay() {
        Delay.randomEnabled = true;
    }
}
