package syntax_analysis.tree;

import lexical_analysis.Token;

public class UnOp extends Node {

    public Token op;
    public Node expr;
    public boolean prefix;

    public UnOp(Node expr, Token op, int line){
        super(line);
        this.expr = expr;
        this.op = op;
        prefix = true;
    }
}
