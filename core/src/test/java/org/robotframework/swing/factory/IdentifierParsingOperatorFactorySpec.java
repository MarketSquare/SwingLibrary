package org.robotframework.swing.factory;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.Operator;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;


@RunWith(JDaveRunner.class)
public class IdentifierParsingOperatorFactorySpec extends Specification<IdentifierParsingOperatorFactory<Operator>> {
    public class Any {
        private Operator createdByIndex = dummy(Operator.class, "byIndex");
        private Operator createdByName = dummy(Operator.class, "byName");
        private IdentifierParsingOperatorFactory<Operator> factory;

        public IdentifierParsingOperatorFactory<Operator> create() {
            factory = new IdentifierParsingOperatorFactory<Operator>() {
                @Override
                public Operator createOperatorByIndex(int index) {
                    return createdByIndex;
                }

                @Override
                public Operator createOperatorByName(String name) {
                    return createdByName;
                }
            };
            return factory;
        }

        public void createsOperatorByNameForNameArguments() {
            specify(factory.parseArgument("someNameArgument"), should.equal(createdByName));
        }

        public void createsOperatorByIndexForIndexArguments() {
            specify(factory.parseArgument("123"), should.equal(createdByIndex));
        }
    }
}
