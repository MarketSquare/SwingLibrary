package org.robotframework.swing.tree;

import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.JTree;

import jdave.ExpectationFailedException;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.factory.OperatorFactorySpecification;
import org.robotframework.swing.popup.IPopupCaller;


@RunWith(JDaveRunner.class)
public class EnhancedTreeOperatorSpec extends OperatorFactorySpecification<EnhancedTreeOperator> {
    public class Any {
        public void hasPopupCaller() {
            EnhancedTreeOperator enhancedTreeOperator = new EnhancedTreeOperator(createMockContainerOperator(), dummy(ComponentChooser.class));
            specify(enhancedTreeOperator, satisfies(new FieldIsNotNullContract("popupCaller")));
        }
    }

    public class CallingPopup {
        private int selectRowCallCount = 0;
        private int scrollToRowCallCount = 0;
        private Point pointToClick = new Point(1, 1);

        public void callsPopupOnRow() {
            int expectedRow = 2;
            EnhancedTreeOperator treeOperator = createTreeOperatorWithExpectedRow(expectedRow);
            JPopupMenu popupMenu = injectPopupCallerTo(treeOperator);

            specify(treeOperator.callPopupOnRow(expectedRow), must.equal(popupMenu));
            specify(selectRowCallCount, must.equal(1));
            specify(scrollToRowCallCount, must.equal(1));
        }

        private JPopupMenu injectPopupCallerTo(final EnhancedTreeOperator treeOperator) {
            final IPopupCaller popupCaller = injectMockTo(treeOperator, IPopupCaller.class);
            final JPopupMenu popupMenu = dummy(JPopupMenu.class);
            checking(new Expectations() {{
                one(popupCaller).callPopupOnComponent(treeOperator, pointToClick);
                will(returnValue(popupMenu));
            }});
            return popupMenu;
        }

        private EnhancedTreeOperator createTreeOperatorWithExpectedRow(final int expectedRow) {
            return new EnhancedTreeOperator(createMockContainerOperator(), dummy(ComponentChooser.class)) {
                public void selectRow(int row) {
                    if (expectedRow == row) {
                        selectRowCallCount++;
                    } else {
                        throw new ExpectationFailedException("Expected " + expectedRow + ", but got: " + row);
                    }
                }
                public void scrollToRow(int row) {
                    if (expectedRow == row) {
                        scrollToRowCallCount++;
                    } else {
                        throw new ExpectationFailedException("Expected " + expectedRow + ", but got: " + row);
                    }
                }
                public Point getPointToClick(int row) {
                    if (expectedRow == row) {
                        return pointToClick;
                    }
                    throw new ExpectationFailedException("Expected " + expectedRow + ", but got: " + row);
                }
            };
        }
    }

    private ContainerOperator createMockContainerOperator() {
        dummyContainerOperator = mock(ContainerOperator.class, "containerOp");
        mockFindsByName(createMockJTree());
        return dummyContainerOperator;
    }

    private JTree createMockJTree() {
        final JTree tree = mock(JTree.class);
        checking(new Expectations() {{
            allowing(tree).isShowing(); will(returnValue(true));
        }});
        return tree;
    }
}
