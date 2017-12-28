package syntax_analysis.tree;

public class WhileStmt extends Node{

    public Expression condition;
    public Node body;

    public WhileStmt(Expression condition, Node body, int line){
        super(line);
        this.condition = condition;
        this.body = body;
    }
}
