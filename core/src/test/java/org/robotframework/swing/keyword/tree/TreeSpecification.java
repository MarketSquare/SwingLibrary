package org.robotframework.swing.keyword.tree;

import org.jmock.Expectations;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.tree.TreeOperator;
import org.robotframework.swing.tree.TreeSupport;

public abstract class TreeSpecification<T extends TreeSupport> extends MockSupportSpecification<T> {
    protected TreeOperator treeOperator;
    protected String treeIdentifier = "someTree";

    protected T populateWithMockOperatorFactory(T treeKeywords) {
        treeOperator = mock(TreeOperator.class);
        final OperatorFactory<?> operatorFactory = injectMockTo(treeKeywords, OperatorFactory.class);
        checking(new Expectations() {{
            one(operatorFactory).createOperator(treeIdentifier);
            will(returnValue(treeOperator));
        }});
        return treeKeywords;
    }
}
