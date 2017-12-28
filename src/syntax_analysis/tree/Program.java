package syntax_analysis.tree;

import java.util.ArrayList;

public class Program extends Node {

    public ArrayList<Node> children;

    public Program(ArrayList<Node> children, int line){
        super(line);
        this.children = children;
    }
}
