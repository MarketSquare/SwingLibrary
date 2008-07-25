package org.robotframework.swing.keyword.menu;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.EventTool;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.keyword.menu.MenuKeywords;
import org.robotframework.swing.keyword.menu.MenuSupport;
import org.robotframework.swing.util.IComponentConditionResolver;


@RunWith(JDaveRunner.class)
public class MenuKeywordsSpec extends MockSupportSpecification<MenuKeywords> {
    private String menuPath = "some|path";
    private JMenuItemOperator menuItemOperator;
    private OperatorFactory operatorFactory;
    private JMenuBarOperator menuBarOperator;

    public class Any {
        public MenuSupport create() {
            return new MenuKeywords();
        }

        public void isRobotKeywordAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasEventTool() {
            specify(context, satisfies(new FieldIsNotNullContract("eventTool")));
        }

        public void hasSelectFromMenuKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromMenu")));
        }

        public void hasSelectFromMenuBlockKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromMenuAndWait")));
        }

        public void hasMenuItemShouldBeEnabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("menuItemShouldBeEnabled")));
        }

        public void hasMenuItemShouldNotBeEnabledKeyword() {
            specify(context, satisfies(new RobotKeywordContract("menuItemShouldNotBeEnabled")));
        }

        public void hasMainMenuItemShouldExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("mainMenuItemShouldExist")));
        }
    }

    public class OperatingOnMenus {
        public MenuSupport create() {
            return createMenuKeywordsWithMockInternals();
        }

        public void selectsFromMenu() {
            checking(new Expectations() {{
                one(menuItemOperator).pushNoBlock();
            }});

            context.selectFromMenu(menuPath);
        }

        public void selectsFromMenuAndWaits() {
            checking(new Expectations() {{
                one(menuItemOperator).push();
            }});

            context.selectFromMenuAndWait(menuPath);
        }
    }

    public class CheckingMenuItemExistence {
        private IComponentConditionResolver menuExistenceResolver;

        public MenuSupport create() {
            menuExistenceResolver = mock(IComponentConditionResolver.class);
            MenuSupport menuKeywords = new MenuKeywords() {
                @Override
                IComponentConditionResolver createMenuItemExistenceResolver() {
                    return menuExistenceResolver;
                }
            };

            return menuKeywords;
        }

        public void menuItemShouldExistPassesIfMenuItemExists() throws Throwable {
            checking(new Expectations() {{
                one(menuExistenceResolver).satisfiesCondition(menuPath);
                will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.mainMenuItemShouldExist(menuPath);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void menuItemShouldExistFailsIfMenuItemDoesntExists() {
            checking(new Expectations() {{
                one(menuExistenceResolver).satisfiesCondition(menuPath);
                will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.mainMenuItemShouldExist(menuPath);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Menu item '" + menuPath + "' does not exist."));
        }
    }

    public class CheckingMenuItemState {
        public MenuSupport create() {
            MenuSupport menuKeywords = createMenuKeywordsWithMockInternals();
            checking(new Expectations() {{
                one(menuBarOperator).pressMouse();
            }});

            return menuKeywords;
        }

        public void menuItemShouldBeEnabledPassesIfMenuItemIsEnabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldBeEnabled(menuPath);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void menuItemShouldBeEnabledFailsIfMenuItemIsDisabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldBeEnabled(menuPath);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Menu item '" + menuPath + "' is disabled."));
        }

        public void menuItemShouldNotBeEnabledPassesIfMenuItemIsDisabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldNotBeEnabled(menuPath);
                }
            }, must.not().raise(AssertionFailedError.class));
        }

        public void menuItemShouldNotBeEnabledFailsIfMenuItemIsEnabled() throws Throwable {
            checking(new Expectations() {{
                one(menuItemOperator).isEnabled(); will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldNotBeEnabled(menuPath);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Menu item '" + menuPath + "' is enabled."));
        }
    }

    public class HandlingAliases {
        protected boolean menuItemShouldNotBeEnabledWasCalled = false;

        public MenuSupport create() {
            return new MenuKeywords() {
                public void menuItemShouldNotBeEnabled(String menuPath) {
                    menuItemShouldNotBeEnabledWasCalled  = true;
                }
            };
        }

        public void menuItemShouldBeDisabledIsAnAliasForMenuItemShouldNotBeEnabled() {
            context.menuItemShouldBeDisabled(menuPath);
            specify(menuItemShouldNotBeEnabledWasCalled);
        }
    }

    private MenuSupport createMenuKeywordsWithMockInternals() {
        menuBarOperator = mock(JMenuBarOperator.class);
        MenuSupport menuKeywords = new MenuKeywords() {
            protected JMenuBarOperator menubarOperator() {
                return menuBarOperator;
            }
        };

        menuItemOperator = mock(JMenuItemOperator.class);
        final EventTool eventTool = injectMockTo(menuKeywords, EventTool.class);

        checking(new Expectations() {{
            one(menuBarOperator).showMenuItem(with(equal(menuPath)));
            will(returnValue(menuItemOperator));

            exactly(2).of(eventTool).waitNoEvent(with(equal(200L)));

            one(menuItemOperator).grabFocus();
        }});
        return menuKeywords;
    }
}
