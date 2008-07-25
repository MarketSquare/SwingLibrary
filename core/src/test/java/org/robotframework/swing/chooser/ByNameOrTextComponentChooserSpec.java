package org.robotframework.swing.chooser;

import java.awt.Component;

import javax.swing.JLabel;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.robotframework.swing.chooser.ByNameOrTextComponentChooser;

@RunWith(JDaveRunner.class)
public class ByNameOrTextComponentChooserSpec extends Specification<ByNameOrTextComponentChooser> {
    public class Any {
        public ByNameOrTextComponentChooser create() {
            return new ByNameOrTextComponentChooser("textOrName");
        }

        public void usesNameAsDescription() {
            specify(context.getDescription(), must.equal("textOrName"));
        }

        public void choosesByNameIfItMatches() {
            final Component component = mock(Component.class);

            checking(new Expectations() {{
                one(component).getName(); will(returnValue("textOrName"));
            }});

            specify(context.checkComponent(component), must.equal(true));
        }

        public void choosesByTextIfItMatchesAndNameDoesntMatch() {
            final JLabel component = mock(JLabel.class);
            checking(new Expectations() {{
                one(component).getName(); will(returnValue("nonMatchingName"));
                one(component).getText(); will(returnValue("textOrName"));
            }});

            specify(context.checkComponent(component), must.equal(true));
        }

        public void doesntThrowExceptionForNullNamesOrTexts() {
            final JLabel component = mock(JLabel.class);
            checking(new Expectations() {{
                one(component).getName(); will(returnValue(null));
                one(component).getText(); will(returnValue(null));
            }});

            specify(context.checkComponent(component), must.equal(false));
        }
    }
}
