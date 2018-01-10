package syntax_analysis.tree;

import lexical_analysis.Token;

public class Assign extends Node {

    public Var left;
    public Node right;
    public Token op;

    public Assign(Var left, Node right, Token op, int line) {
        super(line);
        this.left = left;
        this.right = right;
        this.op = op;
    }
}
