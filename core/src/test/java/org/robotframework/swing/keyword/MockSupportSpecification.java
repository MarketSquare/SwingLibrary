package org.robotframework.swing.keyword;

import jdave.Specification;

import org.laughingpanda.beaninject.Inject;

public abstract class MockSupportSpecification<T> extends Specification<T> {
    protected <E> E injectMockTo(Object target, String fieldName, Class<E> clss) {
        E mock = mock(clss);
        Inject.field(fieldName).of(target).with(mock);
        return mock;
    }

    protected <E> E injectMockTo(Object target, Class<E> clss) {
        String simpleName = getSimpleName(clss);
        String firstCharLowerCase = simpleName.substring(0, 1).toLowerCase();
        String fieldName = firstCharLowerCase + simpleName.substring(1);
        return injectMockTo(target, fieldName, clss);
    }

    protected <E> E injectMockToContext(Class<E> clss) {
        return injectMockTo(context, clss);
    }

    protected <E> E injectMockToContext(String fieldName, Class<E> clss) {
        return injectMockTo(context, fieldName, clss);
    }

    private <E> String getSimpleName(Class<E> clss) {
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
