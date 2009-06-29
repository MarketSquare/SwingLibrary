package org.robotframework.swing.textcomponent;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.html.HTMLDocument;

import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JEditorPaneOperator;
import org.robotframework.swing.keyword.MockSupportSpecification;

@RunWith(JDaveRunner.class)
public class EditorPaneOperatorSpec extends MockSupportSpecification<EditorPaneOperator> {
    public class Any {
        private JEditorPaneOperator paneOperator;
        private HyperlinkEventFactory eventFactory;

        public EditorPaneOperator create() {
            paneOperator = mock(JEditorPaneOperator.class);
            
            checking(new Expectations() {{
                one(paneOperator).getSource(); will(returnValue(dummy(JEditorPane.class)));
                one(paneOperator).getDocument(); will(returnValue(dummy(HTMLDocument.class)));
            }});
            
            EditorPaneOperator editorPaneOperator = new EditorPaneOperator(paneOperator);
            
            eventFactory = injectMockTo(editorPaneOperator, "eventFactory", HyperlinkEventFactory.class);
            
            return editorPaneOperator;
        }
        
        public void activatesHyperLink() {
            final HyperlinkEvent event = dummy(HyperlinkEvent.class);
            checking(new Expectations() {{
                one(eventFactory).createHyperLinkEvent("some link"); will(returnValue(event));
                one(paneOperator).fireHyperlinkUpdate(event);
            }});
            
            context.activateHyperLink("some link");
        }
    }
}
