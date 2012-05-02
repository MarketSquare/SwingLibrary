package org.robotframework.swing.util;

public class ObjectUtils {

    public static boolean nullSafeEquals(Object first, Object second) {
        if (first == null && second == null)
            return true;
        if (first == null || second == null)
            return false;
        return first.equals(second);
    }
}
