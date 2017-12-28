package syntax_analysis.tree;

public class DoWhileStmt extends Node {

    public Expression condition;
    public Node body;

    public DoWhileStmt(Expression condition, Node body, int line){
        super(line);
        this.condition = condition;
        this.body = body;
    }
}
