package org.robotframework.swing.chooser;

import javax.swing.AbstractButton;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class ByTextComponentChooserSpec extends Specification<ByTextComponentChooser> {
    private String componentText = "someComponentText";

    public class Any {
        public ByTextComponentChooser create() {
            return new ByTextComponentChooser(componentText);
        }

        public void usesTextAsDescription() {
            specify(context.getDescription(), must.equal(componentText));
        }
    }

    public class WhenChoosing {
        private AbstractButton component;

        public ByTextComponentChooser create() {
            component = mock(AbstractButton.class);
            return new ByTextComponentChooser(componentText);
        }

        public void choosesIfTextMatches() {
            checking(new Expectations() {{
                one(component).getText(); will(returnValue(componentText));
            }});

            specify(context.checkComponent(component), must.equal(true));
        }

        public void doesntChooseIfTextDoesntMatch() {
            checking(new Expectations() {{
                one(component).getText(); will(returnValue("somethingElse"));
            }});

            specify(context.checkComponent(component), must.equal(false));
        }

        public void doesntChooseIfTextIsNull() {
            checking(new Expectations() {{
                one(component).getText(); will(returnValue(null));
            }});

            specify(context.checkComponent(component), must.equal(false));
        }
    }
}
