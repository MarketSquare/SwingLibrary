package org.robotframework.swing.keyword.tree;

import javax.swing.JMenuItem;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.swing.context.IContextVerifier;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.keyword.tree.TreePopupKeywords;
import org.robotframework.swing.tree.ITreePopupMenuItemFinder;


@RunWith(JDaveRunner.class)
public class TreePopupKeywordsSpec extends MockSupportSpecification<TreePopupKeywords> {
    private String nodeIdentifier = "some|path";
    private String menuPath = "some|menu";

    public class Any {
        public TreePopupKeywords create() {
            return new TreePopupKeywords();
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

        public void hasTreePopupMenuItemFinder() {
            specify(context, satisfies(new FieldIsNotNullContract("treePopupMenuItemFinder")));
        }
    }

    public class Operating {
        private OperatorFactory operatorFactory;
        private JPopupMenuOperator popupMenuOperator;

        public TreePopupKeywords create() {
            TreePopupKeywords treePopupKeywords = new TreePopupKeywords();
            injectContextVerifierTo(treePopupKeywords);

            operatorFactory = injectMockTo(treePopupKeywords, OperatorFactory.class);
            popupMenuOperator = mock(JPopupMenuOperator.class);

            checking(new Expectations() {{
                one(operatorFactory).createOperator(nodeIdentifier);
                will(returnValue(popupMenuOperator));
            }});

            return treePopupKeywords;
        }

        public void selectsFromTreeNodePopupMenu() {
            checking(new Expectations() {{
                one(popupMenuOperator).pushMenu(menuPath);
            }});

            context.selectFromTreeNodePopupMenu(nodeIdentifier, menuPath);
        }

        public void selectsFromTreeNodePopupMenuInSeparateThread() {
            checking(new Expectations() {{
                one(popupMenuOperator).pushMenuNoBlock(menuPath);
            }});

            context.selectFromTreeNodePopupMenuInSeparateThread(nodeIdentifier, menuPath);
        }
    }

    public class CheckingConditions {
        private JMenuItem menuItem;

        public TreePopupKeywords create() {
            TreePopupKeywords treePopupKeywords = new TreePopupKeywords();
            injectMockMenuFinderTo(treePopupKeywords);
            injectContextVerifierTo(treePopupKeywords);

            return treePopupKeywords;
        }


        public void treeNodePopupMenuItemShouldBeEnabledPassesIfMenuItemIsEnabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItem).isEnabled(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodePopupMenuItemShouldBeEnabled(nodeIdentifier, menuPath);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void treeNodePopupMenuItemShouldBeEnabledFailsIfMenuItemIsDisabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItem).isEnabled(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodePopupMenuItemShouldBeEnabled(nodeIdentifier, menuPath);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Menu item '" + menuPath + "' was disabled"));
        }

        public void treeNodePopupMenuItemShouldBeDisabledPassesIfMenuItemIsDisabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItem).isEnabled(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodePopupMenuItemShouldBeDisabled(nodeIdentifier, menuPath);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void treeNodePopupMenuItemShouldBeDisabledFailsIfMenuItemIsEnabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItem).isEnabled(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.treeNodePopupMenuItemShouldBeDisabled(nodeIdentifier, menuPath);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Menu item '" + menuPath + "' was enabled"));
        }

        private void injectMockMenuFinderTo(TreePopupKeywords treePopupKeywords) {
            final ITreePopupMenuItemFinder popupMenuItemFinder =
                injectMockTo(treePopupKeywords, "treePopupMenuItemFinder", ITreePopupMenuItemFinder.class);

            menuItem = mock(JMenuItem.class);
            checking(new Expectations() {{
                one(popupMenuItemFinder).findMenu(nodeIdentifier, menuPath);
                will(returnValue(menuItem));
            }});
        }
    }

    private void injectContextVerifierTo(TreePopupKeywords treePopupKeywords) {
        final IContextVerifier contextVerifier = injectMockTo(treePopupKeywords, "contextVerifier", IContextVerifier.class);
        checking(new Expectations() {{
            one(contextVerifier).verifyContext();
        }});
    }
}
