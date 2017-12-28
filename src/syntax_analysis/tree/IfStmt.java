package syntax_analysis.tree;

public class IfStmt extends Node {

    public Expression condition;
    public Node tbody;
    public Node fbody;

    public IfStmt(Expression condition, Node tbody, Node fbody, int line){
        super(line);
        this.condition = condition;
        this.tbody = tbody;
        this.fbody = fbody;
    }
}
