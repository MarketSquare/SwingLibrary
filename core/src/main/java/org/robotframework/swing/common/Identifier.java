package org.robotframework.swing.common;

public class Identifier {
    
    public final static String REGEXP_IDENTIFIER_PREFIX = "regexp=";
    
    private String identifier;
    private boolean isRegExp;
    private boolean isIndex;
    
    public Identifier(String identifier) {
        this.identifier = identifier;
        this.isRegExp = isRegExpPrefixed(identifier);
        if (isRegExp)
            this.identifier = removeRegExpPrefix(this.identifier);
        else
            this.isIndex = isIndex(this.identifier);
    }
    
    private boolean isRegExpPrefixed(String id) {
        return id.startsWith(REGEXP_IDENTIFIER_PREFIX);
    }

    private String removeRegExpPrefix(String id) {
        return id.replaceFirst(REGEXP_IDENTIFIER_PREFIX, "");
    }
    
    public boolean isRegExp() {
        return this.isRegExp;
    }
    
    public String asString() {
        return identifier;
    }

    public boolean isIndex() {
        return isIndex;
    }
    
    public int asIndex() {
        return Integer.parseInt(identifier);
    }

    private boolean isIndex(String id) {
        try {
            Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return identifier;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((identifier == null) ? 0 : identifier.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Identifier other = (Identifier) obj;
        if (identifier == null) {
            if (other.identifier != null)
                return false;
        } else if (!identifier.equals(other.identifier))
            return false;
        return true;
    }
}
