package org.robotframework.swing.common;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


public class ProxyHandler implements InvocationHandler {

    private Object operator;

    public ProxyHandler(Object target) {
        operator = target;
    }

    public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
        String methodName = method.getName();
        Class[] argumentTypes = method.getParameterTypes();
        try {
            Method m = operator.getClass().getMethod(methodName, argumentTypes);
            return m.invoke(operator, arguments);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("This component type does not support operation "+methodName);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }

    }

    /**
     * Swing and AWT operator often do not inherit from the same interface, although
     * they implement the same methods. With createProxy method you can create
     * a new proxy instance for operator, so that the proxy implements the interfaceTarget.
     */
    public static Object createProxy(Class interfaceTarget, Object operator) {
        InvocationHandler handler = new ProxyHandler(operator);
        Class proxyClass = Proxy.getProxyClass(
                interfaceTarget.getClassLoader(), new Class[]{interfaceTarget});
        try {
            return proxyClass.getConstructor(new Class[]{InvocationHandler.class}).
                    newInstance(new Object[]{handler});
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
