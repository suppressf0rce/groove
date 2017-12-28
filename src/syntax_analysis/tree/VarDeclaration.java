package syntax_analysis.tree;

public class VarDeclaration extends Node{

    public Var var_node;
    public Type type_node;

    public VarDeclaration(Var var_node, Type type_node, int line){
        super(line);
        this.var_node = var_node;
        this.type_node = type_node;
    }
}
