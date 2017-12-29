package syntax_analysis.tree;

import syntax_analysis.Visitable;
import syntax_analysis.Visitor;

public class Node implements Visitable {

    public int line;

    public Node(int line){
        this.line = line;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
