package org.robotframework.swing.testkeyword;

import org.robotframework.javalib.keyword.Keyword;
import org.robotframework.javalib.library.AnnotationLibrary;
import org.robotframework.javalib.library.RobotJavaLibrary;

public abstract class RunnerKeyword implements Keyword {
    private Object[] arguments;
    private RobotJavaLibrary library = new AnnotationLibrary() {{
        addKeywordPattern("org/robotframework/**/keyword/**/*.class");
	}};

    public Object execute(Object[] arguments) {
        this.arguments = arguments;
        return executeKeyword();
    }

    protected Object runKeyword() {
        return library.runKeyword(arguments[0].toString(), removeFirstArgument(arguments));
    }

    private Object[] removeFirstArgument(Object[] arguments) {
        Object[] fixedArguments = new Object[arguments.length - 1];

        for(int i = 1; i < arguments.length; i++) {
            fixedArguments[i - 1] = arguments[i];
        }

        return fixedArguments;
    }

    protected abstract Object executeKeyword();
}
