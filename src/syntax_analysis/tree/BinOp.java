package syntax_analysis.tree;

import lexical_analysis.Token;

public class BinOp extends Node {

    public Node left;
    public Token op;
    public Node right;

    public BinOp(Node left, Token op, Node right, int line){
        super(line);
        this.left = left;
        this.op = op;
        this.right = right;
    }
}
