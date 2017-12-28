package syntax_analysis.tree;

import lexical_analysis.Token;

public class StringConst extends Node {

    public Token token;

    public StringConst(Token token, int line){
        super(line);
        this.token = token;
    }

}
