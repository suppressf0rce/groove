package syntax_analysis.tree;

import java.util.ArrayList;

public class FunctionCall extends Node{

    public String name;
    public ArrayList<Node> args;

    public FunctionCall(String name, ArrayList<Node> args, int line){
        super(line);
        this.name = name;
        this.args = args;
    }
}
