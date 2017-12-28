package lexical_analysis;

public class Token implements Cloneable{

    public TokenType type;
    public String value;

    public Token(TokenType type, String value){
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Token("+type.toString()+","+value+")";
    }
}
