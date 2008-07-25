package org.robotframework.swing.tree;

import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

import jdave.ExpectationFailedException;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.swing.context.Context;
import org.robotframework.swing.tree.EnhancedTreeOperator;
import org.robotframework.swing.tree.TreePopupMenuOperatorFactory;


@RunWith(JDaveRunner.class)
public class TreePopupMenuOperatorFactorySpec extends Specification<TreePopupMenuOperatorFactory> {
    public class Any {
        private EnhancedTreeOperator treeContext;
        private JPopupMenuOperator popupMenuOperator;
        private JPopupMenu dummyPopupMenu = dummy(JPopupMenu.class);

        public TreePopupMenuOperatorFactory create() {
            setMockTreeContext();
            popupMenuOperator = mock(JPopupMenuOperator.class);

            return new TreePopupMenuOperatorFactory() {
                JPopupMenuOperator createPopupOperator(JPopupMenu popupMenu) {
                    if (!popupMenu.equals(dummyPopupMenu)) {
                        throw new ExpectationFailedException("Didn't receive the expected argument.");
                    }
                    return popupMenuOperator;
                }
            };
        }

        public void createsOperatorByIndex() {
            checking(new Expectations() {{
                one(treeContext).callPopupOnRow(3);
                will(returnValue(dummyPopupMenu));
            }});

            specify(context.createOperator("3"), must.equal(popupMenuOperator));
        }

        public void createsOperatorByNodePath() {
            final String nodePath = "path|to|node";
            final TreePath treePath = dummy(TreePath.class);

            checking(new Expectations() {{
                one(treeContext).findPath(nodePath); will(returnValue(treePath));
                one(treeContext).callPopupOnPath(treePath);
                will(returnValue(dummyPopupMenu));
            }});

            specify(context.createOperator(nodePath), must.equal(popupMenuOperator));
        }

        private void setMockTreeContext() {
            treeContext = mock(EnhancedTreeOperator.class);
            Context.setContext(treeContext);
        }
    }
}
