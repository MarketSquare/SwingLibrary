package org.robotframework.swing.keyword.menu;

import jdave.Block;
import jdave.junit4.JDaveRunner;
import junit.framework.AssertionFailedError;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JCheckBoxMenuItemOperator;
import org.robotframework.swing.contract.FieldIsNotNullContract;
import org.robotframework.swing.contract.RobotKeywordContract;
import org.robotframework.swing.contract.RobotKeywordsContract;
import org.robotframework.swing.keyword.MockSupportSpecification;
import org.robotframework.swing.keyword.window.WindowKeywords;

@RunWith(JDaveRunner.class)
public class MenuCheckboxKeywordsSpec extends MockSupportSpecification<MenuCheckboxKeywords> {
    public class Any {
        public MenuCheckboxKeywords create() {
            return new MenuCheckboxKeywords();
        }
        
        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }
        
        public void hasMainMenuItemShouldBeCheckedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("mainMenuItemShouldBeChecked")));
        }
        
        public void hasMainMenuItemShouldNotBeCheckedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("mainMenuItemShouldNotBeChecked")));
        }
        
        public void hasMenuItemShouldBeCheckedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("menuItemShouldBeChecked")));
        }
        
        public void hasMenuItemShouldNotBeCheckedKeyword() {
            specify(context, satisfies(new RobotKeywordContract("menuItemShouldNotBeChecked")));
        }
        
        public void createsWindowKeywords() {
            specify(context, satisfies(new FieldIsNotNullContract("windowKeywords")));
        }
    }
    
    public class Operating {
        private JCheckBoxMenuItemOperator menuItemOperator;
        private WindowKeywords windowKeywords;
        private String menuPath = "some|menu|item";
        
        public MenuCheckboxKeywords create() {
            menuItemOperator = mock(JCheckBoxMenuItemOperator.class);
            MenuCheckboxKeywords keywords = new MenuCheckboxKeywords() {
                protected JCheckBoxMenuItemOperator showMenuItem(String path) {
                    specify(menuPath, path);
                    return menuItemOperator;
                }
            };
            
            windowKeywords = injectMockTo(keywords, WindowKeywords.class);
            return keywords;
        }
        
        public void mainMenuItemShouldBeCheckedPassesWhenMenuItemIsChecked() throws Throwable {
            checking(new Expectations() {{
                one(windowKeywords).selectMainWindow();
                one(menuItemOperator).isSelected(); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.mainMenuItemShouldBeChecked(menuPath);
                }
            }, must.not().raiseAnyException());
        }
        
        public void mainMenuItemShouldBeCheckedFailsWhenMenuItemIsNotChecked() throws Throwable {
            checking(new Expectations() {{
                one(windowKeywords).selectMainWindow();
                one(menuItemOperator).isSelected(); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.mainMenuItemShouldBeChecked(menuPath);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Menu item '" + menuPath + "' is not selected."));
        }
        
        public void menuItemShouldBeCheckedPassesWhenMenuItemIsChecked() throws Throwable {
            checking(new Expectations() {{
                never(windowKeywords).selectMainWindow();
                one(menuItemOperator).isSelected(); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldBeChecked(menuPath);
                }
            }, must.not().raiseAnyException());
        }
        
        public void menuItemShouldBeCheckedFailsWhenMenuItemIsNotChecked() throws Throwable {
            checking(new Expectations() {{
                never(windowKeywords).selectMainWindow();
                one(menuItemOperator).isSelected(); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldBeChecked(menuPath);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Menu item '" + menuPath + "' is not selected."));
        }
        
        public void mainMenuItemShouldBeCheckedPassesWhenMenuItemIsNotChecked() throws Throwable {
            checking(new Expectations() {{
                one(windowKeywords).selectMainWindow();
                one(menuItemOperator).isSelected(); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.mainMenuItemShouldNotBeChecked(menuPath);
                }
            }, must.not().raiseAnyException());
        }
        
        public void mainMenuItemShouldNotBeCheckedFailsWhenMenuItemIsChecked() throws Throwable {
            checking(new Expectations() {{
                one(windowKeywords).selectMainWindow();
                one(menuItemOperator).isSelected(); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.mainMenuItemShouldNotBeChecked(menuPath);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Menu item '" + menuPath + "' is selected."));
        }
        
        public void menuItemShouldNotBeCheckedPassesWhenMenuItemIsNotChecked() throws Throwable {
            checking(new Expectations() {{
                never(windowKeywords).selectMainWindow();
                one(menuItemOperator).isSelected(); will(returnValue(false));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldNotBeChecked(menuPath);
                }
            }, must.not().raiseAnyException());
        }
        
        public void menuItemShouldNotBeCheckedFailsWhenMenuItemIsChecked() throws Throwable {
            checking(new Expectations() {{
                never(windowKeywords).selectMainWindow();
                one(menuItemOperator).isSelected(); will(returnValue(true));
            }});
            
            specify(new Block() {
                public void run() throws Throwable {
                    context.menuItemShouldNotBeChecked(menuPath);
                }
            }, must.raiseExactly(AssertionFailedError.class, "Menu item '" + menuPath + "' is selected."));
        }
    }
}
