package org.robotframework.swing.keyword.menu;

import javax.swing.JMenu;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.keyword.window.WindowKeywords;
import org.robotframework.swing.menu.MenuSupport;


@RunWith(JDaveRunner.class)
public class MainMenuKeywordsSpec extends MockSupportSpecification<MainMenuKeywords> {
    private String menuPath = "some|path";

    public class Any {
        public MainMenuKeywords create() {
            return new MainMenuKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasSelectFromMainMenuKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromMainMenu")));
        }

        public void hasSelectFromMainMenuBlockKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromMainMenuAndWait")));
        }

        public void hasGetMainMenuItemNameKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getMainMenuItemName")));
        }

        public void hasWindowKeywords() {
            specify(context, satisfies(new FieldIsNotNullContract("windowKeywords")));
        }

        public void hasGetMenuItemNameKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getMainMenuItemName")));
        }
        
        public void hasMainMenuItemShouldExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("mainMenuItemShouldExist")));
        }
        
        public void hasMainMenuItemShouldNotExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("mainMenuItemShouldNotExist")));
        }

        public void hasMenuKeywords() {
            specify(context, satisfies(new FieldIsNotNullContract("menuKeywords")));
        }
    }

    public class OperatingOnMainMenu {
        private MenuKeywords menuKeywords;
        private WindowKeywords windowKeywords;

        public MainMenuKeywords create() {
            MainMenuKeywords mainMenuKeywords = new MainMenuKeywords();

            windowKeywords = injectMockTo(mainMenuKeywords, WindowKeywords.class);
            checking(new Expectations() {{
                one(windowKeywords).selectMainWindow();
            }});
            
            menuKeywords = injectMockTo(mainMenuKeywords, MenuKeywords.class);
            return mainMenuKeywords;
        }

        public void selectsFromMainMenuBySelectingMainWindowAndCallingSelectFromMenuKeyword() {
            checking(new Expectations() {{
                one(menuKeywords).selectFromMenu(menuPath);
            }});

            context.selectFromMainMenu(menuPath);
        }

        public void selectsFromMainMenuBlockBySelectingMainWindowAndCallingSelectFromMenuBlockKeywords() {
            checking(new Expectations() {{
                one(menuKeywords).selectFromMenuAndWait(menuPath);
            }});

            context.selectFromMainMenuAndWait(menuPath);
        }
        
        public void checksItemExistence() {
            checking(new Expectations() {{
                one(windowKeywords).selectMainWindow();
                one(menuKeywords).menuItemShouldExist(menuPath);
                one(menuKeywords).menuItemShouldNotExist(menuPath);
            }});

            context.mainMenuItemShouldExist(menuPath);
            context.mainMenuItemShouldNotExist(menuPath);
        }
    }

    public class GettingMenuItemNames {
        private JMenuBarOperator menuBarOperator;

        public MenuSupport create() {
            menuBarOperator = mock(JMenuBarOperator.class);
            return new MainMenuKeywords() {
                @Override
                protected JMenuBarOperator menubarOperator() {
                    return menuBarOperator;
                }
            };
        }

        public void getsMainMenuItemName() {
            final JMenu menu = mock(JMenu.class);
            final String menuText = "menuText";

            checking(new Expectations() {{
                one(menuBarOperator).getMenu(0);
                will(returnValue(menu));

                one(menu).getText(); will(returnValue(menuText));
            }});

            specify(context.getMainMenuItemName("0"), must.equal(menuText));
        }
    }
}
