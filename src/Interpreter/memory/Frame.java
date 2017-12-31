package Interpreter.memory;

import java.util.ArrayList;

public class Frame {

    public String frame_name;
    public Scope current_scope;
    public ArrayList<Scope> scopes;

    public Frame(String frame_name, Scope global_scope) {
        this.frame_name = frame_name;
        this.current_scope = new Scope(current_scope.scope_name + ".scope_00", global_scope);
        scopes = new ArrayList<>();
        scopes.add(current_scope);
    }

    public void new_scope() {
        int scopeID = Integer.parseInt(current_scope.scope_name.substring(current_scope.scope_name.length() - 2, current_scope.scope_name.length())) + 1;
        current_scope = new Scope(current_scope.scope_name.substring(0, current_scope.scope_name.length() - 2) + "" + scopeID, current_scope);
        scopes.add(current_scope);
    }

    public void del_scope() {
        Scope tmp = current_scope;
        current_scope = tmp.parent_scope;
        scopes.remove(scopes.size() - 1);
    }

    public boolean contains(String key) {
        return current_scope.contains(key);
    }


}
