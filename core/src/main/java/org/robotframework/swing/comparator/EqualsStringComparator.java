package org.robotframework.swing.comparator;

import org.netbeans.jemmy.operators.Operator.DefaultStringComparator;
import org.netbeans.jemmy.operators.Operator.StringComparator;

/**
 * @author Heikki Hulkko
 */
public class EqualsStringComparator implements StringComparator {
    public boolean equals(String caption, String match) {
        return new DefaultStringComparator(true, true).equals(caption, match);
    }
}
