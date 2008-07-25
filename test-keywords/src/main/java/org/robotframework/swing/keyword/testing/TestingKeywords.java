package org.robotframework.swing.keyword.testing;

import java.awt.Component;
import java.awt.Container;

import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.Context;


@RobotKeywords
public class TestingKeywords {
    @RobotKeyword
    public void selectEmptyContext() {
        final Container container = new Container();
        Context.setContext(new ContainerOperator(container) {
            public Component getSource() {
                return container;
            }
        });
    }

    @RobotKeyword
    public String getCurrentContextSourceAsString() {
        return Context.getContext().toStringSource();
    }
}
