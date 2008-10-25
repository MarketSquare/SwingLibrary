package org.robotframework.swing.keyword;

import jdave.Specification;

import org.laughingpanda.beaninject.Inject;

public abstract class MockSupportSpecification<T> extends Specification<T> {
    protected <T> T injectMockTo(Object target, String fieldName, Class<T> clss) {
        T mock = mock(clss);
        Inject.field(fieldName).of(target).with(mock);
        return mock;
    }

    protected <T> T injectMockTo(Object target, Class<T> clss) {
        String simpleName = getSimpleName(clss);
        String firstCharLowerCase = simpleName.substring(0, 1).toLowerCase();
        String fieldName = firstCharLowerCase + simpleName.substring(1);
        return injectMockTo(target, fieldName, clss);
    }

    protected <T> T injectMockToContext(Class<T> clss) {
        return injectMockTo(context, clss);
    }

    protected <T> T injectMockToContext(String fieldName, Class<T> clss) {
        return injectMockTo(context, fieldName, clss);
    }

    private <T> String getSimpleName(Class<T> clss) {
        if (clss.isInterface() &&
            clss.getSimpleName().length() > 1 &&
            clss.getSimpleName().charAt(0) == 'I' &&
            Character.isUpperCase(clss.getSimpleName().charAt(1))) {
            return clss.getSimpleName().substring(1);
        } else {
            return clss.getSimpleName();
        }
    }
}
