package syntax_analysis.tree;

public class ForStmt extends Node {

    public Node setup;
    public Node condition;
    public Node increment;
    public Node body;

    public ForStmt(Node setup, Node condition, Node increment, Node body, int line){
        super(line);
        this.setup = setup;
        this.condition = condition;
        this.increment = increment;
        this.body = body;
    }
}
