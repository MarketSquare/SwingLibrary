package org.robotframework.swing.keyword.spinner;

import junit.framework.Assert;

import org.robotframework.javalib.annotation.ArgumentNames;
import org.robotframework.javalib.annotation.RobotKeyword;
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
            + "| Spinner Should Exist | _dateSpinner_ |\n")
    public void spinnerShouldExist(String identifier) {
        Assert.assertTrue("Spinner '" + identifier + "' doesn't exist.", existenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Fails if spinner exists within current context.\n\n"
            + "Example:\n"
            + "| Spinner Should Not Exist | _dateSpinner_ |\n")
    public void spinnerShouldNotExist(String identifier) {
        Assert.assertFalse("Spinner '" + identifier + "' exists.", existenceResolver.satisfiesCondition(identifier));
    }

    @RobotKeyword("Scrolls spinner button up.\n"
            + "The number of notches to scroll can be given as a second argument.\n\n"
            + "Example:\n"
            + "| Increase Spinner Value | _mySpinner_ |   | # scrolls spinner button up a notch |\n"
            + "| Increase Spinner Value | _mySpinner_ | 4 | # scrolls spinner button up four notches |\n")
    @ArgumentNames({"identifier", "times=1"})
    public void increaseSpinnerValue(String identifier, String[] times) {
        increase(operatorFactory.createOperator(identifier), getNotches(times));
    }
    
    private int getNotches(String[] times) {
    	return times.length == 0 ? 1 : Integer.parseInt(times[0]);
    }

    private void increase(SpinnerOperator spinnerOperator, int numberOfIncreases) {
    	for (int i = 0; i < numberOfIncreases; ++i) {
    		spinnerOperator.increase();
    	}
    }

    @RobotKeyword("Scrolls spinner button down.\n"
            + "The number of notches to scroll can be given as a second argument.\n\n"
            + "Example:\n"
            + "| Decrease Spinner Value | _mySpinner_ |   | # scrolls spinner button down a notch |\n"
            + "| Decrease Spinner Value | _mySpinner_ | 4 | # scrolls spinner button down four notches |\n")
    @ArgumentNames({"identifier", "times=1"})
    public void decreaseSpinnerValue(String identifier, String[] times) {
        decrease(operatorFactory.createOperator(identifier), getNotches(times));
    }
    
    private void decrease(SpinnerOperator spinnerOperator, int numberOfDecreases) {
    	for (int i = 0; i < numberOfDecreases; ++i) {
    		spinnerOperator.decrease();
    	}
    }

    @RobotKeyword("Uses current context to search for a spinner button and when found, "
            + "returns its current value.\n\n"
            + "Example:\n"
            + "| ${spinnerValue}=  | Get Spinner Value | _mySpinner_    |\n"
            + "| Should Be Equal As Integers | _12_     | _${spinnerValue}_ |\n")
    public Object getSpinnerValue(String identifier) {
        return operatorFactory.createOperator(identifier).getValue();
    }

    @RobotKeyword("Sets the string value for the spinner found from the current context.\n\n"
            + "Example:\n"
            + "| Set Spinner String Value | _mySpinner_  | _January_  |\n")
    public void setSpinnerStringValue(String identifier, String value) {
		operatorFactory.createOperator(identifier).setValue(value);
    }
    
    @RobotKeyword("Sets the number value for the spinner found from the current context.\n\n"
            + "Example:\n"
            + "| Set Spinner Number Value | _mySpinner_  | _100_  |\n"
            + "| Set Spinner Number Value | _mySpinner_  | _7.5_  |\n")
    public void setSpinnerNumberValue(String identifier, String value) {
		operatorFactory.createOperator(identifier).setValue(asNumber(value));
    }

    private Object asNumber(String value) {
    	try { return Integer.valueOf(value); } catch (NumberFormatException ignored) {}
    	try { return Float.valueOf(value); } catch (NumberFormatException ignored) {}
    	throw new RuntimeException("Can't convert '"+value+"' to a number.");
    }
}
