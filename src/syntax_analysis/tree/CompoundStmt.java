package syntax_analysis.tree;

import java.util.ArrayList;

public class CompoundStmt extends Node {

    public ArrayList<Node> children;

    public CompoundStmt(ArrayList<Node> children, int line){
        super(line);
        this.children = children;
    }
}
