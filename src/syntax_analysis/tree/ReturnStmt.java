package syntax_analysis.tree;

public class ReturnStmt extends Node {

    private Node expression;

    public ReturnStmt(Node expression, int line){
        super(line);
        this.expression = expression;
    }
}
