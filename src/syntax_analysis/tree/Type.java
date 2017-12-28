package syntax_analysis.tree;

import lexical_analysis.Token;

public class Type extends Node {

    public Token token;
    public String value;

    public Type(Token token, int line){
        super(line);
        this.token = token;
        value = token.value;
    }
}
