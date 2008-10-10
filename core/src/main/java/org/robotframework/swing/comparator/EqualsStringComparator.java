package org.robotframework.swing.comparator;

import org.netbeans.jemmy.operators.Operator.DefaultStringComparator;
import org.netbeans.jemmy.operators.Operator.StringComparator;

public class EqualsStringComparator implements StringComparator {
    @Override
    public boolean equals(String caption, String match) {
        return new DefaultStringComparator(true, true).equals(caption, match);
    }
}
