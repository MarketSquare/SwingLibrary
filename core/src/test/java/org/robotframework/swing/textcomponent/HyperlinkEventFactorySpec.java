package org.robotframework.swing.textcomponent;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;

import jdave.Block;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.netbeans.jemmy.operators.JEditorPaneOperator;

@RunWith(JDaveRunner.class)
public class HyperlinkEventFactorySpec extends Specification<HyperlinkEventFactory> {
    private String html = "<html>" +
        " <head>\n" +
        "  </head>\n" +
        " <body>\n" +
        "   Some text, foo bar baz <a href=\"somehref\">Some\n" +
        "   Link</a>\n" +
        " </body>\n" +
        "</html>\n";
    
    
    public class Any {
        private JEditorPane editorPane;
        private JEditorPaneOperator editorPaneOperator;

        public HyperlinkEventFactory create() {
            editorPane = new JEditorPane() {{
                setContentType("text/html");
                setText(html);
            }};
            editorPaneOperator = new JEditorPaneOperator(editorPane);
            return new HyperlinkEventFactory(editorPaneOperator);
        }
        
        public void createsHyperLinkEvent() throws Exception {
            HyperlinkEvent linkEvent = context.createHyperLinkEvent("Some Link");
            
            specify(linkEvent.getEventType(), HyperlinkEvent.EventType.ACTIVATED);
            specify(linkEvent.getSource(), editorPane);
            Element sourceElement = linkEvent.getSourceElement();
            specify(sourceElement.getDocument(), editorPaneOperator.getDocument());
            AttributeSet attributes = sourceElement.getAttributes();
            AttributeSet a = (AttributeSet) attributes.getAttribute(HTML.Tag.A);
            specify(a.getAttribute(HTML.Attribute.HREF), "somehref");
        }
        
        public void failsWhenLinkNotFound() throws Exception {
            specify(new Block() {
                public void run() throws Throwable {
                    context.createHyperLinkEvent("Not in document");
                }
            }, must.raise(LinkNotFoundException.class, "Hyperlink 'Not in document' was not found"));
        }
    }
}
