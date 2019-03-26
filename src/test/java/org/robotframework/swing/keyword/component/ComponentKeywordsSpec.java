package org.robotframework.swing.keyword.component;

import jdave.Block;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.robotframework.jdave.contract.FieldIsNotNullContract;
import org.robotframework.jdave.contract.RobotKeywordContract;
import org.robotframework.jdave.contract.RobotKeywordsContract;
import org.robotframework.swing.comparator.EqualsStringComparator;
import org.robotframework.swing.component.ComponentOperator;
import org.robotframework.swing.factory.IdentifierParsingOperatorFactory;
import org.robotframework.swing.factory.OperatorFactory;
import org.robotframework.jdave.mock.MockSupportSpecification;
import org.robotframework.swing.util.IComponentConditionResolver;


@RunWith(JDaveRunner.class)
public class ComponentKeywordsSpec extends MockSupportSpecification<ComponentKeywords> {
    private String componentIdentifier = "someComponent";

    public class Any {
        public ComponentKeywords create() {
            return new ComponentKeywords();
        }

        public void isRobotKeywordsAnnotated() {
            specify(context, satisfies(new RobotKeywordsContract()));
        }

        public void hasOperatorFactory() {
            specify(context, satisfies(new FieldIsNotNullContract("operatorFactory")));
        }

        public void hasComponentExistenceResolver() {
            specify(context, satisfies(new FieldIsNotNullContract("componentExistenceResolver")));
        }

        public void hasComponentShouldNotExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("componentShouldNotExist")));
        }

        public void hasComponentShouldExistKeyword() {
            specify(context, satisfies(new RobotKeywordContract("componentShouldExist")));
        }

        public void hasClickOnComponentKeyword() {
            specify(context, satisfies(new RobotKeywordContract("clickOnComponent")));
        }

        public void hasGetTooltipTextKeyword() {
            specify(context, satisfies(new RobotKeywordContract("getTooltipText")));
        }

        public void hasSetFocusToComponentKeyword() {
            specify(context, satisfies(new RobotKeywordContract("focusToComponent")));
        }

        public void hasSelectFromPopupMenuKeyword() {
            specify(context, satisfies(new RobotKeywordContract("selectFromPopupMenu")));
        }
    }

    public class Operating {
        private ComponentOperator operator;

        public ComponentKeywords create() {
            ComponentKeywords keywords = new ComponentKeywords();
            final OperatorFactory operatorFactory = injectMockTo(keywords, "operatorFactory", IdentifierParsingOperatorFactory.class);
            operator = mock(ComponentOperator.class);
            checking(new Expectations() {{
                one(operatorFactory).createOperator(componentIdentifier); will(returnValue(operator));
            }});
            return keywords;
        }

        public void clicksOnComponent() {
            checking(new Expectations() {{
                one(operator).clickOnComponent("1", "BUTTON1_MASK", new String[0]);
            }});

            context.clickOnComponent(componentIdentifier, new String[0]);
        }

        public void doubleclicksOnComponent() {
            checking(new Expectations() {{
                one(operator).clickOnComponent("2", "BUTTON1_MASK", new String[0]);
            }});

            context.clickOnComponent(componentIdentifier, new String[] {"2"});
        }

        public void clicksOnComponentWithKeyAliases() {
            checking(new Expectations() {{
                one(operator).clickOnComponent("2", "BUTTON1_MASK", new String[] {"SHIFT_MASK"});
            }});

            context.clickOnComponent(componentIdentifier, new String[] {"2", "BUTTON1_MASK", "SHIFT"});
        }


        public void getsTooltipText() {
            checking(new Expectations() {{
                one(operator).getToolTipText(); will(returnValue("tooltip"));
            }});

            specify(context.getTooltipText(componentIdentifier), "tooltip");
        }

        public void focusesToComponent() {
            checking(new Expectations() {{
                one(operator).getFocus();
            }});

            context.focusToComponent(componentIdentifier);
        }

        public void selectsFromPopupMenu() {
            final JPopupMenuOperator popupOperator = mock(JPopupMenuOperator.class);
            final String menuPath = "some|menu";

            checking(new Expectations() {{
                one(operator).invokePopup(); will(returnValue(popupOperator));
                one(popupOperator).pushMenuNoBlock(with(equal(menuPath)), with(any(EqualsStringComparator.class)));
            }});

            context.selectFromPopupMenu(componentIdentifier, menuPath);
        }
    }

    public class ResolvingExistence {
        private IComponentConditionResolver componentExistenceResolver;
        private ComponentKeywords componentKeywords;

        public ComponentKeywords create() {
            componentKeywords = new ComponentKeywords();
            injectMockComponentExistenceResolverToKeywords();
            return componentKeywords;
        }

        public void componentShouldNotExistPassesIfComponentIsNotFound() throws Throwable {
            checking(new Expectations() {{
                one(componentExistenceResolver).satisfiesCondition(componentIdentifier);
                will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.componentShouldNotExist(componentIdentifier);
                }
            }, must.not().raise(AssertionError.class));
        }

        public void componentShouldNotExistFailsIfComponentIsFound() throws Throwable {
            checking(new Expectations() {{
                one(componentExistenceResolver).satisfiesCondition(componentIdentifier);
                will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.componentShouldNotExist(componentIdentifier);
                }
            }, must.raiseExactly(AssertionError.class, "Component '" + componentIdentifier + "' exists"));
        }

        public void componentShouldExistPassesIfComponentIsFound() throws Throwable {
            checking(new Expectations() {{
                one(componentExistenceResolver).satisfiesCondition(componentIdentifier);
                will(returnValue(true));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.componentShouldExist(componentIdentifier);
                }
            }, must.not().raise(AssertionError.class));
        }

        public void componentShouldExistFailsIfComponentIsNotFound() {
            checking(new Expectations() {{
                one(componentExistenceResolver).satisfiesCondition(componentIdentifier);
                will(returnValue(false));
            }});

            specify(new Block() {
                public void run() throws Throwable {
                    context.componentShouldExist(componentIdentifier);
                }
            }, must.raiseExactly(AssertionError.class, "Component '" + componentIdentifier + "' does not exist"));
        }

        private void injectMockComponentExistenceResolverToKeywords() {
            componentExistenceResolver = injectMockTo(componentKeywords, "componentExistenceResolver", IComponentConditionResolver.class);
        }
    }
}
