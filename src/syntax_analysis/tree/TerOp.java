package syntax_analysis.tree;

public class TerOp extends Node {

    public Node condition;
    public Expression texpression;
    public Node fexpression;

    public TerOp(Node condition, Expression texpression, Node fexpression, int line){
        super(line);
        this.condition = condition;
        this.texpression = texpression;
        this.fexpression = fexpression;
    }
}
