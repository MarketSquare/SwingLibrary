package org.robotframework.swing.tree;

import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.jemmy.operators.JTreeOperator;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.popup.PopupCaller;


@RunWith(JDaveRunner.class)
public class TreeOperatorSpec extends MockSupportSpecification<TreeOperator> {
    private JTreeOperator jTreeOperator;
    private TreePath treePath;
    private String nodeIdentifier = "some|node";
    
    public class Any {
        public void hasPopupCaller() {
            TreeOperator enhancedTreeOperator = new TreeOperator(dummy(JTreeOperator.class));
            specify(enhancedTreeOperator, satisfies(new FieldIsNotNullContract("popupCaller")));
        }
    }

    public class Operating {
        private TreeOperator enhancedTreeOperator;
        
        public TreeOperator create() {
            treePath = mock(TreePath.class);
            jTreeOperator = mock(JTreeOperator.class);
            enhancedTreeOperator = new TreeOperator(jTreeOperator);
            injectMockPathFactory();
            return enhancedTreeOperator;
        }
        
        public void clicksOnNode() {
            final int clickCount = 2;
            checking(new Expectations() {{
                one(jTreeOperator).clickOnPath(treePath, clickCount);
            }});
            
            context.clickOnNode(nodeIdentifier, clickCount);
        }

        public void expandsPath() {
            checking(new Expectations() {{
                one(jTreeOperator).expandPath(treePath);
            }});
            
            context.expand(nodeIdentifier);
        }
        
        public void collapsesPath() {
            checking(new Expectations() {{
                one(jTreeOperator).collapsePath(treePath);
            }});
            
            context.collapse(nodeIdentifier);
        }
        
        public void addsSelection() {
            checking(new Expectations() {{
                one(jTreeOperator).addSelectionPath(treePath);
            }});
            
            context.addSelection(nodeIdentifier);
        }
        
        public void removesSelection() {
            checking(new Expectations() {{
                one(jTreeOperator).removeSelectionPath(treePath);
            }});
            
            context.removeSelection(nodeIdentifier);
        }
        

        public void checksExpanded() {
            checking(new Expectations() {{
                one(jTreeOperator).isExpanded(treePath); will(returnValue(true));
            }});
            
            specify(context.isExpanded(nodeIdentifier));
        }
        
        public void checksCollapsed() {
            checking(new Expectations() {{
                one(jTreeOperator).isCollapsed(treePath); will(returnValue(true));
            }});
            
            specify(context.isCollapsed(nodeIdentifier));
        }
        
        public void checksIsLeaf() {
            final TreeNode treeNode = mock(TreeNode.class);
            checking(new Expectations() {{
                one(treePath).getLastPathComponent(); will(returnValue(treeNode));
                one(treeNode).isLeaf(); will(returnValue(true));
            }});
            
            specify(context.isLeaf(nodeIdentifier));
        }
        
        public void checksIsSelected() {
            checking(new Expectations() {{
                one(jTreeOperator).isPathSelected(treePath); will(returnValue(true));
            }});
            
            specify(context.isPathSelected(nodeIdentifier));
        }

        public void checksIsVisible() {
            checking(new Expectations() {{
                one(jTreeOperator).isVisible(treePath); will(returnValue(true));
            }});
            
            specify(context.isVisible(nodeIdentifier));
        }
        
        private void injectMockPathFactory() {
            final TreePathFactory pathFactory = injectMockTo(enhancedTreeOperator, TreePathFactory.class);
            checking(new Expectations() {{
                one(pathFactory).createTreePath(nodeIdentifier);
                will(returnValue(treePath));
            }});
        }
    }

    public class WorkingOnNodeIndexes {
        public TreeOperator create() {
            treePath = mock(TreePath.class);
            jTreeOperator = mock(JTreeOperator.class);
            return new TreeOperator(jTreeOperator) {
                public TreePath findPath(String path) {
                    return TreeOperatorSpec.this.treePath;
                }
            };
        }
        
        public void getsTreeNodeLabel() {
            final String label = "someNode";
            checking(new Expectations() {{
                one(jTreeOperator).getPathForRow(2); will(returnValue(treePath));
                one(treePath).getLastPathComponent(); will(returnValue(label));
            }});
            
            specify(context.getTreeNodeLabel(2), label);
        }
        
        public void getsTreeNodeIndex() {
            checking(new Expectations() {{
                one(jTreeOperator).getRowForPath(treePath); will(returnValue(3)); 
            }});
            
            specify(context.getTreeNodeIndex(nodeIdentifier), 3);
        }
    }
    
    public class CreatingPopupOperator {
        private TreePopupMenuOperatorFactory popupFactory;
        private JPopupMenuOperator popupMenuOperator = dummy(JPopupMenuOperator.class);

        public TreeOperator create() {
            popupFactory = mock(TreePopupMenuOperatorFactory.class);
            return new TreeOperator(dummy(JTreeOperator.class)) {
                protected TreePopupMenuOperatorFactory createPopupFactory() {
                    return popupFactory;
                }
            };
        }
        
        public void createsPopupOperator() {
            checking(new Expectations() {{
                one(popupFactory).createOperator(nodeIdentifier);
                will(returnValue(popupMenuOperator));
            }});
            
            specify(context.createPopupOperator(nodeIdentifier), popupMenuOperator);
        }
        
        public void createsPopupOperatorOnSelectedNodes() {
            checking(new Expectations() {{
                one(popupFactory).createOperatorBySelection();
                will(returnValue(popupMenuOperator));
            }});
            
            specify(context.createPopupOperatorOnSelectedNodes(), popupMenuOperator);
        }
    }
    
    public class CallingPopup {
        private int row = 2;
        private JTreeOperator jTreeOperator;
        private Point pointToClick = new Point(1, 2);

        public TreeOperator create() {
            jTreeOperator = mock(JTreeOperator.class);
            return new TreeOperator(jTreeOperator);
        }
        
        public void callsPopuOnRow() {
            final PopupCaller<JTreeOperator> popupCaller = injectMockToContext(PopupCaller.class);
            final JPopupMenu popupMenu = dummy(JPopupMenu.class);
            
            checking(new Expectations() {{
                one(jTreeOperator).selectRow(row);
                one(jTreeOperator).scrollToRow(row);
                one(jTreeOperator).getPointToClick(row); will(returnValue(pointToClick));
                one(popupCaller).callPopupOnComponent(jTreeOperator, pointToClick); will(returnValue(popupMenu));
            }});
            
            specify(context.callPopupOnRow(row), must.equal(popupMenu));
        }
    }
}
