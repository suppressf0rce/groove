package Interpreter.memory;

import java.util.LinkedHashMap;

public class Scope {

    public String scope_name;
    public Scope parent_scope;
    public LinkedHashMap<String, Object> values;

    public Scope(String scope_name, Scope parent_scope) {
        this.scope_name = scope_name;
        this.parent_scope = parent_scope;

        values = new LinkedHashMap<>();
    }

    public void set_item(String key, Object value) {
        values.put(key, value);
    }

    public Object get_item(String key) {
        return values.get(key);
    }

    public boolean contains(String key) {
        return values.containsKey(key);
    }

}
