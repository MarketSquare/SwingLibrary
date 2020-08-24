package org.robotframework.swing.testkeyword;

import org.robotframework.javalib.keyword.Keyword;
import org.robotframework.javalib.library.AnnotationLibrary;
import org.robotframework.javalib.library.RobotFrameworkDynamicAPI;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class RunnerKeyword implements Keyword {
    private List<String> arguments;
    private Map kwargs;
    private RobotFrameworkDynamicAPI library = new AnnotationLibrary() {{
        addKeywordPattern("org/robotframework/**/keyword/**/*.class");
	}};

    public Object execute(List arguments) {
        this.arguments = arguments;
        return executeKeyword();
    }

    public Object execute(List arguments, Map kwargs) {
        this.arguments = arguments;
        this.kwargs = kwargs;
        return executeKeyword();
    }

    protected Object runKeyword() {
        return library.runKeyword(arguments.get(0), removeFirstArgument(arguments));
    }

    private List<String> removeFirstArgument(List<String> arguments) {
        List<String> newArgList = new ArrayList<>();
        newArgList.addAll(arguments);
        newArgList.remove(0);
        return newArgList;
    }

    protected abstract Object executeKeyword();

    public List<String> getArgumentTypes() {
        return null;
    }
}
