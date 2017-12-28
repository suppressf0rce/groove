package syntax_analysis.tree;

public class Param extends Node {

    public Var var_node;
    public Type type_node;

    public Param(Type type_node, Var var_node, int line){
        super(line);
        this.type_node = type_node;
        this.var_node = var_node;
    }
}
