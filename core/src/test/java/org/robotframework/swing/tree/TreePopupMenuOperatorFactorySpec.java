package org.robotframework.swing.tree;

import javax.swing.JPopupMenu;
import javax.swing.tree.TreePath;

import jdave.ExpectationFailedException;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JPopupMenuOperator;


@RunWith(JDaveRunner.class)
public class TreePopupMenuOperatorFactorySpec extends Specification<TreePopupMenuOperatorFactory> {
    public class Any {
        private TreeOperator treeOperator;
        private JPopupMenuOperator popupMenuOperator;
        private JPopupMenu dummyPopupMenu = dummy(JPopupMenu.class);

        public TreePopupMenuOperatorFactory create() {
            popupMenuOperator = mock(JPopupMenuOperator.class);
            treeOperator = mock(TreeOperator.class);
            
            return new TreePopupMenuOperatorFactory(treeOperator) {
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
                one(treeOperator).callPopupOnRow(3);
                will(returnValue(dummyPopupMenu));
            }});

            specify(context.createOperator("3"), must.equal(popupMenuOperator));
        }

        public void createsOperatorByNodePath() {
            final String nodePath = "path|to|node";
            final TreePath treePath = dummy(TreePath.class);

            checking(new Expectations() {{
                one(treeOperator).findPath(nodePath); will(returnValue(treePath));
                one(treeOperator).callPopupOnPath(treePath);
                will(returnValue(dummyPopupMenu));
            }});

            specify(context.createOperator(nodePath), must.equal(popupMenuOperator));
        }
        
        public void createsOperatorBySelection() {
            final TreePath[] selectionPaths = new TreePath[0];
            
            checking(new Expectations() {{
                one(treeOperator).getSelectionPaths(); will(returnValue(selectionPaths));
                one(treeOperator).callPopupOnPaths(selectionPaths); will(returnValue(dummyPopupMenu));
            }});
            
            specify(context.createOperatorBySelection(), must.equal(popupMenuOperator));
        }
    }
}
