package org.robotframework.swing.util;

import static org.hamcrest.Matchers.is;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.TimeoutExpiredException;
import org.netbeans.jemmy.operators.Operator;
import org.robotframework.swing.arguments.IdentifierHandler;


@RunWith(JDaveRunner.class)
public class ComponentExistenceResolverSpec extends Specification<ComponentExistenceResolver> {
    public class Any {
        private IdentifierHandler operatorFactory;
        private Operator dummyOperator = mock(Operator.class);

        public ComponentExistenceResolver create() {
            operatorFactory = mock(IdentifierHandler.class);
            return new ComponentExistenceResolver(operatorFactory);
        }

        public void satisfiesConditionIfComponentFound() {
            checking(new Expectations() {{
                one(operatorFactory).parseArgument("someIdentifier");
                will(returnValue(dummyOperator));
            }});

            specify(context.satisfiesCondition("someIdentifier"));
        }

        public void doesNotSatisfyConditionIfComponentIsNotFound() {
            checking(new Expectations() {{
                one(operatorFactory).parseArgument("someIdentifier");
                will(throwException(new TimeoutExpiredException("timeout expired")));
            }});

            specify(context.satisfiesCondition("someIdentifier"), is(false));
        }
    }
}
