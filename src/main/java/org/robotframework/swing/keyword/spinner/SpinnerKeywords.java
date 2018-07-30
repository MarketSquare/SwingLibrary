package org.robotframework.swing.keyword.spinner;

import org.junit.Assert;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
import org.robotframework.javalib.annotation.RobotKeywordOverload;
import org.robotframework.javalib.annotation.RobotKeywords;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.spinner.SpinnerOperator;
import org.robotframework.swing.spinner.SpinnerOperatorFactory;
import org.robotframework.swing.util.ComponentExistenceResolver;
import org.robotframework.swing.util.IComponentConditionResolver;

@RobotKeywords
public class SpinnerKeywords {
    private IdentifierParsingOperatorFactory<SpinnerOperator> operatorFactory = new SpinnerOperatorFactory();
    private IComponentConditionResolver existenceResolver = new ComponentExistenceResolver(operatorFactory);

    @RobotKeyword("Fails if spinner does not exist within current context.\n\n"
            + "Example:\n"
            + "| `Spinner Should Exist` | dateSpinner |\n")
    @ArgumentNames({"identifier"})
    public void spinnerShouldExist(String identifier) {
        Assert.assertTrue("Spinner '" + identifier + "' doesn't exist.", existenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Fails if spinner exists within current context.\n\n"
            + "Example:\n"
            + "| `Spinner Should Not Exist` | dateSpinner |\n")
    @ArgumentNames({"identifier"})
    public void spinnerShouldNotExist(String identifier) {
        Assert.assertFalse("Spinner '" + identifier + "' exists.", existenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Scrolls spinner button up.\n"
            + "The number of notches to scroll can be given as a second argument.\n\n"
            + "Examples:\n"
            + "| `Increase Spinner Value` | mySpinner |   | # scrolls spinner button up a notch |\n"
            + "| `Increase Spinner Value` | mySpinner | 4 | # scrolls spinner button up four notches |\n")
    @ArgumentNames({"identifier", "times=1"})
    public void increaseSpinnerValue(String identifier, int times) {
        increase(operatorFactory.createOperator(identifier), times);
    }

    @RobotKeywordOverload
    public void increaseSpinnerValue(String identifier) {
        increaseSpinnerValue(identifier, 1);
    }

    private void increase(SpinnerOperator spinnerOperator, int numberOfIncreases) {
    	for (int i = 0; i < numberOfIncreases; ++i) {
    		spinnerOperator.increase();
    	}
    }

    @RobotKeyword("Scrolls spinner button down.\n"
            + "The number of notches to scroll can be given as a second argument.\n\n"
            + "Examples:\n"
            + "| `Decrease Spinner Value` | mySpinner |   | # scrolls spinner button down a notch |\n"
            + "| `Decrease Spinner Value` | mySpinner | 4 | # scrolls spinner button down four notches |\n")
    @ArgumentNames({"identifier", "times=1"})
    public void decreaseSpinnerValue(String identifier, int times) {
        decrease(operatorFactory.createOperator(identifier), times);
    }

    @RobotKeywordOverload
    public void decreaseSpinnerValue(String identifier) {
        decreaseSpinnerValue(identifier, 1);
    }

    private void decrease(SpinnerOperator spinnerOperator, int numberOfDecreases) {
    	for (int i = 0; i < numberOfDecreases; ++i) {
    		spinnerOperator.decrease();
    	}
    }

    @RobotKeyword("Uses current context to search for a spinner button and when found, "
            + "returns its current value.\n\n"
            + "Example:\n"
            + "| ${spinnerValue}=  | `Get Spinner Value` | mySpinner    |\n"
            + "| `Should Be Equal As Integers` | 12     | ${spinnerValue} |\n")
    @ArgumentNames({"identifier"})
    public Object getSpinnerValue(String identifier) {
        return operatorFactory.createOperator(identifier).getValue();
    }

    @RobotKeyword("Sets the string value for the spinner found from the current context.\n\n"
            + "Example:\n"
            + "| `Set Spinner String Value` | mySpinner  | January  |\n")
    @ArgumentNames({"identifier", "value"})
    public void setSpinnerStringValue(String identifier, String value) {
		operatorFactory.createOperator(identifier).setValue(value);
    }

    @RobotKeyword("Sets the number value for the spinner found from the current context.\n\n"
            + "Examples:\n"
            + "| `Set Spinner Number Value` | mySpinner  | 100  |\n"
            + "| `Set Spinner Number Value` | mySpinner  | 7.5  |\n")
    @ArgumentNames({"identifier", "value"})
    public void setSpinnerNumberValue(String identifier, String value) {
		operatorFactory.createOperator(identifier).setValue(asNumber(value));
    }

    private Object asNumber(String value) {
    	try { return Integer.valueOf(value); } catch (NumberFormatException ignored) {}
    	try { return Float.valueOf(value); } catch (NumberFormatException ignored) {}
    	throw new RuntimeException("Can't convert '"+value+"' to a number.");
    }
}
