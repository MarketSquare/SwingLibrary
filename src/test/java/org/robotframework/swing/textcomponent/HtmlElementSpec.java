package org.robotframework.swing.textcomponent;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.jmock.Expectations;
import org.junit.runner.RunWith;

@RunWith(JDaveRunner.class)
public class HtmlElementSpec extends Specification<HtmlElement> {
    public class CreatingUrl {
        private Element element;
        private String expectedHref = "someHref";
        private URL expectedBase = dummy("http://base");
        private URL url = dummy("http://base/someplace");

        public HtmlElement create() {
            element = mock(Element.class);
            return new HtmlElement(element) {
                URL createURL(URL base, String href) {
                    specify(base, expectedBase);
                    specify(href, expectedHref);
                    return url;
                }
            };
        }
        
        public void createsUrl() {
            final AttributeSet attributes = mock(AttributeSet.class);
            final AttributeSet a = mock(AttributeSet.class, "a");
            final HTMLDocument document = mock(HTMLDocument.class);
            checking(new Expectations() {{
                one(element).getAttributes(); will(returnValue(attributes));
                one(attributes).getAttribute(HTML.Tag.A); will(returnValue(a));
                one(a).getAttribute(HTML.Attribute.HREF); will(returnValue(expectedHref));
                
                one(element).getDocument(); will(returnValue(document));
                one(document).getBase(); will(returnValue(expectedBase));
            }});
            
            specify(context.getUrl(), url);
        }
        
        private URL dummy(String spec) {
            try {
                return new File(spec).toURL();
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
    public class Retrieving {
        private Element element;

        public HtmlElement create() {
            element = mock(Element.class, "someElem");
            return new HtmlElement(element);
        }
        
        public void getsText() throws Exception {
            final Document document = mock(Document.class);
            
            checking(new Expectations() {{
                one(element).getDocument(); will(returnValue(document));
                one(element).getStartOffset(); will(returnValue(2));
                one(element).getEndOffset(); will(returnValue(10));
                one(document).getText(2, 8); will(returnValue("someText"));
            }});
            
            specify(context.getText(), "someText");
        }
        
        public void getsChildElement() {
            final Element child = dummy(Element.class);
            checking(new Expectations() {{
                one(element).getElement(0); will(returnValue(child));
            }});
            
            specify(context.getChild(0).getClass(), HtmlElement.class);
        }
        
        public void getsHref() {
            final AttributeSet attributes = mock(AttributeSet.class);
            final AttributeSet a = mock(AttributeSet.class, "a");
            checking(new Expectations() {{
                one(element).getAttributes(); will(returnValue(attributes));
                one(attributes).getAttribute(HTML.Tag.A); will(returnValue(a));
                one(a).getAttribute(HTML.Attribute.HREF); will(returnValue("someHref"));
            }});
            
            specify(context.getHref(), "someHref");
        }
    }
    
    public class Delegating {
        private Element element;

        public HtmlElement create() {
            element = mock(Element.class);
            return new HtmlElement(element);
        }
        
        public void delegatesGetAttributes() {
            final AttributeSet expectedAttributeSet = dummy(AttributeSet.class);
            checking(new Expectations() {{
                one(element).getAttributes(); will(returnValue(expectedAttributeSet));
            }});
            specify(context.getAttributes(), must.equal(expectedAttributeSet));
        }

        public void delegatesGetDocument() {
            final Document expectedDocument = dummy(Document.class);
            checking(new Expectations() {{
                one(element).getDocument(); will(returnValue(expectedDocument));
            }});
            specify(context.getDocument(), must.equal(expectedDocument));
        }

        public void delegatesGetElement() {
            final int index = 0;
            final Element expectedElement = dummy(Element.class, "getelement");
            checking(new Expectations() {{
                one(element).getElement(index); will(returnValue(expectedElement));
            }});
            specify(context.getElement(index), must.equal(expectedElement));
        }

        public void delegatesGetElementCount() {
            final int expectedInt = 0;
            checking(new Expectations() {{
                one(element).getElementCount(); will(returnValue(expectedInt));
            }});
            specify(context.getElementCount(), must.equal(expectedInt));
        }

        public void delegatesGetElementIndex() {
            final int offset = 0;
            final int expectedInt = 0;
            checking(new Expectations() {{
                one(element).getElementIndex(offset); will(returnValue(expectedInt));
            }});
            specify(context.getElementIndex(offset), must.equal(expectedInt));
        }

        public void delegatesGetEndOffset() {
            final int expectedInt = 0;
            checking(new Expectations() {{
                one(element).getEndOffset(); will(returnValue(expectedInt));
            }});
            specify(context.getEndOffset(), must.equal(expectedInt));
        }

        public void delegatesGetName() {
            final String expectedString = "";
            checking(new Expectations() {{
                one(element).getName(); will(returnValue(expectedString));
            }});
            specify(context.getName(), must.equal(expectedString));
        }

        public void delegatesGetParentElement() {
            final Element expectedElement = dummy(Element.class, "parentelement");
            checking(new Expectations() {{
                one(element).getParentElement(); will(returnValue(expectedElement));
            }});
            specify(context.getParentElement(), must.equal(expectedElement));
        }

        public void delegatesGetStartOffset() {
            final int expectedInt = 0;
            checking(new Expectations() {{
                one(element).getStartOffset(); will(returnValue(expectedInt));
            }});
            specify(context.getStartOffset(), must.equal(expectedInt));
        }

        public void delegatesIsLeaf() {
            final boolean expectedBoolean = false;
            checking(new Expectations() {{
                one(element).isLeaf(); will(returnValue(expectedBoolean));
            }});
            specify(context.isLeaf(), must.equal(expectedBoolean));
        }
    }
}
