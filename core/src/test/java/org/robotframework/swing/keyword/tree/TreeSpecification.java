package org.robotframework.swing.keyword.tree;

import org.jmock.Expectations;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.tree.TreeOperator;
import org.robotframework.swing.tree.TreeSupport;

public abstract class TreeSpecification<T extends TreeSupport> extends MockSupportSpecification<T> {
    protected TreeOperator treeOperator;
    protected String treeIdentifier = "someTree";
    protected OperatorFactory<?> operatorFactory;

    protected T populateWithMockOperatorFactory(T treeKeywords) {
        return populateWithMockOperatorFactory(treeKeywords, mock(TreeOperator.class));
    }
    
    protected T populateWithMockOperatorFactory(T treeKeywords, TreeOperator treeOperator) {
        this.treeOperator = treeOperator;
        operatorFactory = injectMockTo(treeKeywords, OperatorFactory.class);
        checking(new Expectations() {{
            one(operatorFactory).createOperator(treeIdentifier);
            will(returnValue(TreeSpecification.this.treeOperator));
        }});
        return treeKeywords;
    }
}
