package org.robotframework.swing.keyword.tree;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.swing.comparator.EqualsStringComparator;


@RunWith(JDaveRunner.class)
public class TreeNodePopupKeywordsSpec extends TreeSpecification<TreeNodePopupKeywords> {
    private String nodeIdentifier = "some|path";
    private String menuPath = "some|menu";
    private JPopupMenuOperator popupMenuOperator;
    private JMenuItemOperator menuItemOperator;

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

        public void hasSelectFromPopupMenuOnSelectedTreeNodesKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromPopupMenuOnSelectedTreeNodes")));
        }

        public void hasOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }
    }

    public class InvokingPopupMenuActions {
        public TreeNodePopupKeywords create() {
            TreeNodePopupKeywords treePopupKeywords = populateWithMockOperatorFactory(new TreeNodePopupKeywords());
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
        private String menuPath = "some|path";

        public TreeNodePopupKeywords create() {
            TreeNodePopupKeywords treeKeywords = populateWithMockOperatorFactory(new TreeNodePopupKeywords());
            popupMenuOperator = mock(JPopupMenuOperator.class);
            menuItemOperator = mock(JMenuItemOperator.class);
            checking(new Expectations() {{
                atLeast(1).of(popupMenuOperator).showMenuItem(menuPath); will(returnValue(menuItemOperator));
                atLeast(1).of(popupMenuOperator).setComparator(with(any(EqualsStringComparator.class)));
                one(treeOperator).createPopupOperator(nodeIdentifier);will(returnValue(popupMenuOperator));
            }});

            return treeKeywords;
        }

        public void treeNodePopupMenuItemShouldBeEnabledPassesIfMenuItemIsEnabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(true));
                one(popupMenuOperator).setVisible(false);
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodePopupMenuItemShouldBeEnabled(treeIdentifier, nodeIdentifier, menuPath);
                }
            }, must.not().raise(AssertionError.class));
        }

        public void treeNodePopupMenuItemShouldBeEnabledFailsIfMenuItemIsDisabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodePopupMenuItemShouldBeEnabled(treeIdentifier, nodeIdentifier, menuPath);
                }
            }, must.raiseExactly(AssertionError.class, "Menu item '" + menuPath + "' was disabled"));
        }

        public void treeNodePopupMenuItemShouldBeDisabledPassesIfMenuItemIsDisabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(false));
                one(popupMenuOperator).setVisible(false);
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodePopupMenuItemShouldBeDisabled(treeIdentifier, nodeIdentifier, menuPath);
                }
            }, must.not().raise(AssertionError.class));
        }

        public void treeNodePopupMenuItemShouldBeDisabledFailsIfMenuItemIsEnabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodePopupMenuItemShouldBeDisabled(treeIdentifier, nodeIdentifier, menuPath);
                }
            }, must.raiseExactly(AssertionError.class, "Menu item '" + menuPath + "' was enabled"));
        }
    }
}
