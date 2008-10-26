package org.robotframework.swing.keyword.tree;

import java.awt.Component;

import javax.swing.JMenuItem;
import javax.swing.JTree;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.tree.ITreePopupMenuItemFinder;


@RunWith(JDaveRunner.class)
public class TreeNodePopupKeywordsSpec extends TreeSpecification<TreeNodePopupKeywords> {
    private String treeIdentifier = "someTree";
    private String nodeIdentifier = "some|path";
    private String menuPath = "some|menu";

    public class Any {
        public TreeNodePopupKeywords create() {
            return new TreeNodePopupKeywords();
        }

        public void isRoboKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasSelectFromTreeNodePopupMenuKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromTreeNodePopupMenu")));
        }

        public void hasSelectFromTreeNodePopupMenuInSeparateThreadKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromTreeNodePopupMenuInSeparateThread")));
        }

        public void hasTreeNodePopupMenuItemShouldBeEnabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("treeNodePopupMenuItemShouldBeEnabled")));
        }

        public void hasTreeNodePopupMenuItemShouldBeDisabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("treeNodePopupMenuItemShouldBeDisabled")));
        }

        public void hasOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void hasContextVerifier() {
            specify(context, satisfies(new FieldIsNotNullContract("contextVerifier")));
        }
    }

    public class Operating {
        private JPopupMenuOperator popupMenuOperator;

        public TreeNodePopupKeywords create() {
            TreeNodePopupKeywords treePopupKeywords = populateWithMockOperatingFactoryAndContextVerifier(new TreeNodePopupKeywords());

            popupMenuOperator = mock(JPopupMenuOperator.class);

            checking(new Expectations() {{
                one(treeOperator).createPopupOperator(nodeIdentifier);
                will(returnValue(popupMenuOperator));
            }});

            return treePopupKeywords;
        }

        public void selectsFromTreeNodePopupMenu() {
            checking(new Expectations() {{
                one(popupMenuOperator).pushMenu(with(equal(menuPath)), with(any(EqualsStringComparator.class)));
            }});

            context.selectFromTreeNodePopupMenu(treeIdentifier, nodeIdentifier, menuPath);
        }

        public void selectsFromTreeNodePopupMenuInSeparateThread() {
            checking(new Expectations() {{
                one(popupMenuOperator).pushMenuNoBlock(with(equal(menuPath)), with(any(EqualsStringComparator.class)));
            }});

            context.selectFromTreeNodePopupMenuInSeparateThread(treeIdentifier, nodeIdentifier, menuPath);
        }
    }

    public class CheckingConditions {
        private JMenuItem menuItem;

        public TreeNodePopupKeywords create() {
            final ITreePopupMenuItemFinder menuFinder = createMockMenuFinder();
            TreeNodePopupKeywords treeKeywords = populateWithMockOperatingFactoryAndContextVerifier(new TreeNodePopupKeywords() {
                ITreePopupMenuItemFinder createPopupMenuItemFinder(Component source) {
                    return menuFinder;
                }
            });
            
            checking(new Expectations() {{
                one(treeOperator).getSource(); will(returnValue(dummy(JTree.class)));
            }});
            
            return treeKeywords;
        }


        public void treeNodePopupMenuItemShouldBeEnabledPassesIfMenuItemIsEnabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItem).isEnabled(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodePopupMenuItemShouldBeEnabled(treeIdentifier, nodeIdentifier, menuPath);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void treeNodePopupMenuItemShouldBeEnabledFailsIfMenuItemIsDisabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItem).isEnabled(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodePopupMenuItemShouldBeEnabled(treeIdentifier, nodeIdentifier, menuPath);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Menu item '" + menuPath + "' was disabled"));
        }

        public void treeNodePopupMenuItemShouldBeDisabledPassesIfMenuItemIsDisabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItem).isEnabled(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodePopupMenuItemShouldBeDisabled(treeIdentifier, nodeIdentifier, menuPath);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void treeNodePopupMenuItemShouldBeDisabledFailsIfMenuItemIsEnabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItem).isEnabled(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodePopupMenuItemShouldBeDisabled(treeIdentifier, nodeIdentifier, menuPath);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Menu item '" + menuPath + "' was enabled"));
        }

        private ITreePopupMenuItemFinder createMockMenuFinder() {
            final ITreePopupMenuItemFinder popupMenuItemFinder = mock(ITreePopupMenuItemFinder.class);
            menuItem = mock(JMenuItem.class);
            checking(new Expectations() {{
                one(popupMenuItemFinder).findMenu(nodeIdentifier, menuPath);
                will(returnValue(menuItem));
            }});
            return popupMenuItemFinder;
        }
    }
}
