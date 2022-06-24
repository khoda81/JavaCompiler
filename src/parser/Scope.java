package parser;

import java.util.HashMap;

public class Scope extends HashMap<String, MyObject> {
    Scope parent;

    public Scope() {
        this(null);
    }

    public Scope(Scope parent) {
        super();
        this.parent = parent;
    }

    @Override
    public MyObject get(Object key) {
        // get the entry from the current scope first
        MyObject value = super.get(key);
        if (value != null)
            return value;

        // if not found, get the entry from the parent scope
        if (parent != null)
            return parent.get(key);

        // if not found, return null
        return null;
    }
}
