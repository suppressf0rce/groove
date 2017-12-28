package syntax_analysis.tree;

import lexical_analysis.Token;

public class Var extends Node {

    public Token token;
    public String value;

    public Var(Token token, int line){
        super(line);
        this.token = token;
        this.value = token.value;
    }
}
