package org.robotframework.swing.keyword.spinner;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.context.DefaultContextVerifier;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.spinner.SpinnerOperator;
import org.robotframework.swing.spinner.SpinnerOperatorFactory;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;

@RobotKeywords
public class SpinnerKeywords {
    private IContextVerifier contextVerifier = new DefaultContextVerifier();
    private IdentifierParsingOperatorFactory<SpinnerOperator> operatorFactory = new SpinnerOperatorFactory();
    private IComponentConditionResolver existenceResolver = new ComponentExistenceResolver(operatorFactory);

    @RobotKeyword
    public void spinnerShouldExist(String identifier) {
        contextVerifier.verifyContext();
        Assert.assertTrue("Spinner '" + identifier + "' doesn't exist.", existenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword
    public void spinnerShouldNotExist(String identifier) {
        contextVerifier.verifyContext();
        Assert.assertFalse("Spinner '" + identifier + "' exists.", existenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword
    public void setSpinnerValue(String identifier, String value) {
        contextVerifier.verifyContext();
        operatorFactory.createOperator(identifier).setValue(value);
    }

    @RobotKeyword
    public String getSpinnerValue(String identifier) {
        contextVerifier.verifyContext();
        return (String) operatorFactory.createOperator(identifier).getValue();
    }
}
