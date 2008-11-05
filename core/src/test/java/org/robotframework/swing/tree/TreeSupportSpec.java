package org.robotframework.swing.tree;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;


@RunWith(JDaveRunner.class)
public class TreeSupportSpec extends MockSupportSpecification<TreeSupport> {
    public class Any {
        public TreeSupport create() {
            return new TreeSupport();
        }
        
        public void hasOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }
        
        public void hasContextVerifier() {
            specify(context, satisfies(new FieldIsNotNullContract("contextVerifier")));
        }
    }
    
    public class CreatingTreeOperator {
        private TreeSupport treeSupport = new TreeSupport();
        private String treeIdentifier = "someTree";
        private TreeOperator treeOperator = dummy(TreeOperator.class);
        
        public TreeSupport create() {
            injectMockOperatorFactory();
            injectMockContextVerifier();
            return treeSupport;
        }

        public void createsTreeOperatorAndVerifiesContext() {
            specify(context.createTreeOperator(treeIdentifier), must.equal(treeOperator));
        }
        
        private void injectMockContextVerifier() {
            final IContextVerifier contextVerifier = injectMockTo(treeSupport, IContextVerifier.class);
            checking(new Expectations() {{
                one(contextVerifier).verifyContext();
            }});
        }
        
        private void injectMockOperatorFactory() {
            final OperatorFactory operatorFactory = injectMockTo(treeSupport, OperatorFactory.class);
            checking(new Expectations() {{
                one(operatorFactory).createOperator(treeIdentifier);
                will(returnValue(treeOperator));
            }});
        }
    }
}
