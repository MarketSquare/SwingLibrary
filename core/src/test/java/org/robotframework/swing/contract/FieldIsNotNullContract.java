package org.robotframework.swing.contract;

import jdave.ExpectationFailedException;
import jdave.IContract;

import org.laughingpanda.beaninject.impl.Accessor;

public class FieldIsNotNullContract implements IContract {
    private final String fieldName;

    public FieldIsNotNullContract(String fieldName) {
        this.fieldName = fieldName;
    }

    public void isSatisfied(Object obj) throws ExpectationFailedException {
        if (extractFieldValue(obj) == null) {
            throw new ExpectationFailedException("Field " + fieldName + " of " + obj.getClass().getName() + " was null.");
        }
    }

    private Object extractFieldValue(Object obj) {
        try {
            return Accessor.field(fieldName, obj.getClass()).get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
