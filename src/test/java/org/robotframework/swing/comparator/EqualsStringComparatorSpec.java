package org.robotframework.swing.comparator;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.Operator.StringComparator;

@RunWith(JDaveRunner.class)
public class EqualsStringComparatorSpec extends Specification<StringComparator> {
    public class Any {
        public StringComparator create() {
            return new EqualsStringComparator();
        }

        public void doesntAcceptSubstringMatches() {
            specify(!context.equals("fOo", "foo"));
            specify(!context.equals("foobar", "foo"));
        }

        public void acceptsExactMatches() {
            specify(context.equals("foo", "foo"));
        }
    }
}
