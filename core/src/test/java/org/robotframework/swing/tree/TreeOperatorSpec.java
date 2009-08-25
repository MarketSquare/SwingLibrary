package org.robotframework.swing.tree;

import java.awt.Point;

import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;
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

    public class OperatingWithTreePath {
        private TreeOperator treeOperator;
        
        public TreeOperator create() {
            treePath = mock(TreePath.class);
            jTreeOperator = mock(JTreeOperator.class);
            treeOperator = new TreeOperator(jTreeOperator);
            injectMockPathFactory(treeOperator);
            return treeOperator;
        }
        
        public void clicksOnNode() {
            final int clickCount = 2;
            checking(new Expectations() {{
                one(jTreeOperator).clickOnPath(treePath, clickCount);
            }});
            
            context.clickOnNode(nodeIdentifier, clickCount);
        }

        public void expands() {
            checking(new Expectations() {{
                one(jTreeOperator).expandPath(treePath);
            }});
            
            context.expand(nodeIdentifier);
        }
        
        public void collapses() {
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
        
        public void getsChildNames() {
            final TreePath[] expectedPaths = new TreePath[3];
            for (int i = 0; i < expectedPaths.length; i++) {
                final String childName = "child" + i; 
                final TreePath path = mock(TreePath.class, childName);
                checking(new Expectations() {{
                    allowing(path).getLastPathComponent(); will(returnValue(childName));
                }});
                expectedPaths[i] = path; 
            }
            
            checking(new Expectations() {{
                allowing(jTreeOperator).getSource(); will(returnValue(dummy(JTree.class)));
                one(jTreeOperator).getChildPaths(treePath); will(returnValue(expectedPaths));
            }});
            
            specify(context.getTreeNodeChildNames(nodeIdentifier), containAll("child0", "child1", "child2"));
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
        
        private void injectMockPathFactory(TreeOperator treeOperator) {
            final TreePathFactory pathFactory = injectMockTo(treeOperator, TreePathFactory.class);
            checking(new Expectations() {{
                one(pathFactory).createTreePath(nodeIdentifier);
                will(returnValue(treePath));
            }});
        }
    }
    
    public class Operating {
        private JTreeOperator jTreeOperator;
        private TreePath somePath = mock(TreePath.class);

        public TreeOperator create() {
            jTreeOperator = mock(JTreeOperator.class);
            return new TreeOperator(jTreeOperator);
        }
        
        public void isRootVisible() {
            checking(new Expectations() {{
                one(jTreeOperator).isRootVisible(); will(returnValue(true));
            }});
            
            specify(context.isRootVisible());
        }
        
        public void getsModel() {
            final TreeModel model = dummy(TreeModel.class);
            checking(new Expectations() {{
                one(jTreeOperator).getModel(); will(returnValue(model));
            }});
            
            specify(context.getModel(), model);
        }
        
        public void collapses() {
            checking(new Expectations() {{
                one(jTreeOperator).collapsePath(somePath);
            }});
            
            context.collapse(somePath);
        }
        
        public void expands() {
            checking(new Expectations() {{
                one(jTreeOperator).expandPath(somePath);
            }});
            
            context.expand(somePath);
        }
        
        public void isCollapsed() {
            checking(new Expectations() {{
                one(jTreeOperator).isCollapsed(somePath); will(returnValue(true));
            }});
            
            specify(context.isCollapsed(somePath));
        }
        
        public void isExpanded() {
            checking(new Expectations() {{
                one(jTreeOperator).isExpanded(somePath); will(returnValue(true));
            }});
            
            specify(context.isExpanded(somePath));
        }
        
        public void isLeaf() {
            final TreeNode node = mock(TreeNode.class);
            checking(new Expectations() {{
                one(somePath).getLastPathComponent(); will(returnValue(node));
                one(node).isLeaf(); will(returnValue(true));
            }});
            
            specify(context.isLeaf(somePath));
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
    
    public class OperatingOnAllNodes {
        private TreeIterator treeIterator;

        public TreeOperator create() {
            treeIterator = mock(TreeIterator.class);
            return new TreeOperator(dummy(JTreeOperator.class)) {
                protected TreeIterator createIterator() {
                    return treeIterator;
                }
            };
        }
        
        public void operatesOnAllNodes() {
            final TreePathAction someAction = dummy(TreePathAction.class);
            
            checking(new Expectations() {{
                one(treeIterator).operateOnAllNodes(someAction);
            }});
            
            context.operateOnAllNodes(someAction);
        }
    }
}
