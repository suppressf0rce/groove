package syntax_analysis.tree;

import java.util.ArrayList;

public class FunctionDeclaration extends Node {

    public Type type_node;
    public String name;
    public ArrayList<Param> params;
    public FunctionBody body;

    public FunctionDeclaration(Type type_node, String func_name, ArrayList<Param> params, FunctionBody body, int line) {
        super(line);
        this.type_node = type_node;
        this.name = func_name;
        this.params = params;
        this.body = body;
    }
}
