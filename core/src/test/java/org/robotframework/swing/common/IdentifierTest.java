package org.robotframework.swing.common;

import static org.junit.Assert.*;

import org.junit.Test;


public class IdentifierTest {

    private int index = 1;
    private String indexId = ""+index;
    private Identifier indexIdentifier = new Identifier(indexId);
    private String strId = "fooness";
    private Identifier identifier = new Identifier(strId); 
    private String regExpId = "regexp="+strId;
    private Identifier regExpIdentifier = new Identifier(regExpId);

    @Test
    public void identifier() {
        assertEquals(strId, identifier.asString());
        assertFalse(identifier.isIndex());
        assertFalse(identifier.isRegExp());
    }
    
    @Test
    public void indexIdentifier() {
        assertEquals(index, indexIdentifier.asIndex());
        assertEquals(indexId, indexIdentifier.asString());
        assertTrue(indexIdentifier.isIndex());
        assertFalse(indexIdentifier.isRegExp());
    }
    
    @Test
    public void regExpIdentifier() {
        assertTrue(regExpIdentifier.isRegExp());
        assertEquals(strId, regExpIdentifier.asString());
        assertFalse(regExpIdentifier.isIndex());
    }
}
