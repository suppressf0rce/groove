package syntax_analysis.tree;

import java.util.ArrayList;

public class Expression extends Node {

    public ArrayList<Node> children;

    public Expression(ArrayList<Node> children, int line){
        super(line);
        this.children = children;
    }
}
