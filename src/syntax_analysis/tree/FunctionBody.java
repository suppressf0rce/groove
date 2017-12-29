package syntax_analysis.tree;

import java.util.ArrayList;


public class FunctionBody extends CompoundStmt {

    public ReturnStmt returnStmt = null;

    public FunctionBody(ArrayList<Node> children, int line) {
        super(children, line);
    }
}
