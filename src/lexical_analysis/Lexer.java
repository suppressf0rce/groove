package lexical_analysis;

import java.util.HashMap;

public class Lexer implements Cloneable{

    //Variables
    //------------------------------------------------------------------------------------------------------------------
    private String text;
    public int pos;
    public Character current_char;
    public int line;
    private boolean throw_exceptions;

    public HashMap<String, Token> RESERVED_KEYWORDS;

    public Lexer(Lexer lexer){
        this.text = lexer.text;
        this.pos = lexer.pos;
        this.current_char = lexer.current_char;
        this.line = lexer.line;
        this.throw_exceptions = lexer.isThrow_exceptions();
        this.RESERVED_KEYWORDS = lexer.RESERVED_KEYWORDS;
    }

    public Lexer(String text, boolean throw_exceptions){
        this.text = text;
        this.throw_exceptions = throw_exceptions;
        pos = 0;
        current_char = text.charAt(pos);
        line = 1;
        RESERVED_KEYWORDS = new HashMap<>();

        //Initializing HashMap of the keywords
        //TODO: Add keywords
        RESERVED_KEYWORDS.put("void", new Token(TokenType.VOID, "void"));
        RESERVED_KEYWORDS.put("boolean", new Token(TokenType.BOOLEAN, "boolean"));
        RESERVED_KEYWORDS.put("int", new Token(TokenType.INT, "int"));
        RESERVED_KEYWORDS.put("long", new Token(TokenType.LONG, "long"));
        RESERVED_KEYWORDS.put("float", new Token(TokenType.FLOAT, "float"));
        RESERVED_KEYWORDS.put("double", new Token(TokenType.DOUBLE, "double"));
        RESERVED_KEYWORDS.put("char", new Token(TokenType.CHAR, "char"));
        RESERVED_KEYWORDS.put("string", new Token(TokenType.STRING, "string"));
        RESERVED_KEYWORDS.put("package", new Token(TokenType.PACKAGE, "package"));
        RESERVED_KEYWORDS.put("import", new Token(TokenType.IMPORT, "import"));
        RESERVED_KEYWORDS.put("let", new Token(TokenType.LET, "let"));
        RESERVED_KEYWORDS.put("be", new Token(TokenType.BE, "be"));
        RESERVED_KEYWORDS.put("function", new Token(TokenType.FUNCTION, "function"));
        RESERVED_KEYWORDS.put("class", new Token(TokenType.CLASS, "class"));
        RESERVED_KEYWORDS.put("interface", new Token(TokenType.INTERFACE, "interface"));
        RESERVED_KEYWORDS.put("enum", new Token(TokenType.ENUM, "enum"));
        RESERVED_KEYWORDS.put("extends", new Token(TokenType.EXTENDS, "extends"));
        RESERVED_KEYWORDS.put("implements", new Token(TokenType.IMPLEMENTS, "implements"));
        RESERVED_KEYWORDS.put("true", new Token(TokenType.BOOLEAN_CONST, "true"));
        RESERVED_KEYWORDS.put("false", new Token(TokenType.BOOLEAN_CONST, "false"));
        RESERVED_KEYWORDS.put("private", new Token(TokenType.PRIVATE, "private"));
        RESERVED_KEYWORDS.put("public", new Token(TokenType.PUBLIC, "public"));
        RESERVED_KEYWORDS.put("protected", new Token(TokenType.PROTECTED, "protected"));
        RESERVED_KEYWORDS.put("abstract", new Token(TokenType.ABSTRACT, "abstract"));
        RESERVED_KEYWORDS.put("static", new Token(TokenType.STATIC, "static"));
        RESERVED_KEYWORDS.put("final", new Token(TokenType.FINAL, "final"));
        RESERVED_KEYWORDS.put("super", new Token(TokenType.SUPER, "super"));
        RESERVED_KEYWORDS.put("break", new Token(TokenType.BREAK, "break"));
        RESERVED_KEYWORDS.put("continue", new Token(TokenType.CONTINUE, "continue"));
        RESERVED_KEYWORDS.put("return", new Token(TokenType.RETURN, "return"));
        RESERVED_KEYWORDS.put("if", new Token(TokenType.IF, "if"));
        RESERVED_KEYWORDS.put("else", new Token(TokenType.ELSE, "else"));
        RESERVED_KEYWORDS.put("switch", new Token(TokenType.SWITCH, "switch"));
        RESERVED_KEYWORDS.put("case", new Token(TokenType.CASE, "case"));
        RESERVED_KEYWORDS.put("default", new Token(TokenType.DEFAULT, "default"));
        RESERVED_KEYWORDS.put("while", new Token(TokenType.WHILE, "while"));
        RESERVED_KEYWORDS.put("for", new Token(TokenType.FOR, "for"));
        RESERVED_KEYWORDS.put("do", new Token(TokenType.DO, "do"));
        RESERVED_KEYWORDS.put("try", new Token(TokenType.TRY, "try"));
        RESERVED_KEYWORDS.put("catch", new Token(TokenType.CATCH, "catch"));
        RESERVED_KEYWORDS.put("throw", new Token(TokenType.THROW, "throw"));
        RESERVED_KEYWORDS.put("throws", new Token(TokenType.THROWS, "throws"));
        RESERVED_KEYWORDS.put("new", new Token(TokenType.NEW, "new"));
        RESERVED_KEYWORDS.put("this", new Token(TokenType.THIS, "this"));
        RESERVED_KEYWORDS.put("end", new Token(TokenType.END, "end"));
        RESERVED_KEYWORDS.put("and", new Token(TokenType.LOG_AND_OP, "&&"));
        RESERVED_KEYWORDS.put("or", new Token(TokenType.LOG_OR_OP, "||"));
        RESERVED_KEYWORDS.put("is", new Token(TokenType.EQ_OP, "=="));
        RESERVED_KEYWORDS.put("not", new Token(TokenType.NE_OP, "!="));
    }

    //Methods
    //------------------------------------------------------------------------------------------------------------------

    /**
     * Throws and exception and exits compiler if attribute throw_exception
     * is set to true other wise prints out the exception on the stderr
     * @param message message that will be printed out on stderr
     */
    private void error(String message){
        if(throw_exceptions) {
            try {
                throw new Exception("Lexical error: "+message);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.err.flush();
                System.exit(1);
            }
        }else{
            System.err.println("Lexical error: "+message);
            System.err.flush();
        }
    }

    /**
     * Advance the 'pos' pointer and set the 'current_char' variable.
     */
    private void advance(){
        pos++;
        if(pos > text.length() - 1){
            current_char = null;
        }else{
            current_char = text.charAt(pos);
        }
    }

    /**
     * Check next n-th char but don't change state.
     * @param n-th number of the peek
     * @return char of the peek
     */
    public Character peek(int n){
        int peek_pos = pos + n;
        if(peek_pos > text.length() - 1)
            return null;
        else
            return text.charAt(peek_pos);
    }

    /**
     * Skip all whitespaces between tokens from input
     */
    private void skip_whitespace(){
        while(Character.isSpaceChar(current_char)){
            advance();
        }
    }


    /**
     * Return a (multi-digit) integer or float consumed from the input.
     * @return number Token
     */
    private Token number(){
        StringBuilder result = new StringBuilder();

        while(current_char != null && Character.isDigit(current_char)){
            result.append(current_char);
            advance();
        }

        if(current_char != null) {
            if (current_char == '.') {
                result.append(current_char);
                advance();


                while (current_char != null && Character.isDigit(current_char)) {
                    result.append(current_char);
                    advance();
                }

                return new Token(TokenType.REAL_CONST, result.toString());
            }
        }

        return new Token(TokenType.INTEGER_CONST, result.toString());
    }

    /**
     * @return String written in code without double quotes
     */
    private String string(){
        StringBuilder result = new StringBuilder();
        advance();
        while(current_char != '"'){
            if(current_char == null || (peek(1) == '\n' && current_char != '\\'))
                error("Unterminated string with '\"' at line: "+line);

            result.append(current_char);
            advance();
        }


        advance();
        return result.toString();
    }


    /**
     * Handle chars between single quotes
     * @return string of that char
     */
    private String character(){
        advance();
        String character = current_char+"";
        advance();

        if(current_char!=null && current_char != '\'')
            error("Unclosed char constant at line: "+line);

        advance();

        return character;
    }

    /**
     * Handle identifiers and reserved keywords
     * @return Token of id
     */
    private Token id(){
        StringBuilder result = new StringBuilder();

        while(current_char != null && Character.isLetterOrDigit(current_char)){
            result.append(current_char);
            advance();
        }

        Token token = new Token(TokenType.ID, result.toString());

        if(RESERVED_KEYWORDS.containsKey(result.toString())){
            token = RESERVED_KEYWORDS.get(result.toString());
        }
        return token;
    }

    /**
     * Skips single line of the comment
     */
    private void single_line_comment(){
        while(current_char != '\n' && current_char != null)
            advance();

        while (current_char != null && current_char == '\n')
            advance();
    }

    /**
     * Skips multi line of the comments until it reaches the end of the comment
     */
    private void multi_line_comment(){

        advance();
        advance();

        while((current_char != null && current_char != '*') || (peek(1) != null && peek(1) != '/'))
            advance();

        advance();

        if(current_char !=null && current_char == '/')
            advance();

        while(current_char!= null && current_char == '\n')
            advance();
    }

    /**
     * Lexical analyzer (also known as scanner or tokenizer)
     * This method is responsible for breaking a sentence
     * apart into tokens. One token at a time.
     * @return Token
     */
    public Token get_next_token(){
        while(current_char != null){
            if(current_char == '\n'){
                line ++;
                advance();
                return new Token(TokenType.EOL, "\n");
            }

            if(Character.isSpaceChar(current_char)){
                skip_whitespace();
                continue;
            }

            if(Character.isLetter(current_char))
                return id();

            if(Character.isDigit(current_char))
                return number();

            if(current_char == '\"')
                return new Token(TokenType.STRING_CONST, string());

            if(current_char == '\'')
                return new Token(TokenType.CHAR_CONST, character());

            if(current_char == '/' && peek(1) == '/'){
                single_line_comment();
                continue;
            }

            if(current_char == '<' && peek(1) == '<' && peek(2) == '='){
                advance();
                advance();
                advance();
                return new Token(TokenType.LEFT_ASSIGN, "<<=");
            }

            if(current_char == '>' && peek(1) == '>' && peek(2) == '='){
                advance();
                advance();
                advance();
                return new Token(TokenType.RIGHT_ASSIGN, ">>=");
            }

            if(current_char == '*' && peek(1) == '*' && peek(2) == '='){
                advance();
                advance();
                advance();
                return new Token(TokenType.POW_ASSIGN, "**=");
            }

            if(current_char == '+' && peek(1) == '='){
                advance();
                advance();
                return new Token(TokenType.ADD_ASSIGN, "+=");
            }

            if(current_char == '-' && peek(1) == '='){
                advance();
                advance();
                return new Token(TokenType.SUB_ASSIGN, "-=");
            }

            if(current_char == '*' && peek(1) == '='){
                advance();
                advance();
                return new Token(TokenType.MUL_ASSIGN, "*=");
            }

            if(current_char == '/' && peek(1) == '='){
                advance();
                advance();
                return new Token(TokenType.DIV_ASSIGN, "/=");
            }

            if(current_char == '%' && peek(1) == '='){
                advance();
                advance();
                return new Token(TokenType.MOD_ASSIGN, "%=");
            }

            if(current_char == '&' && peek(1) == '='){
                advance();
                advance();
                return new Token(TokenType.AND_ASSIGN, "&=");
            }

            if(current_char == '|' && peek(1) == '='){
                advance();
                advance();
                return new Token(TokenType.OR_ASSIGN, "|=");
            }

            if(current_char == '^' && peek(1) == '='){
                advance();
                advance();
                return new Token(TokenType.XOR_ASSIGN, "^=");
            }

            if(current_char == '<' && peek(1) == '<'){
                advance();
                advance();
                return new Token(TokenType.LEFT_OP, "<<");
            }

            if(current_char == '>' && peek(1) == '>'){
                advance();
                advance();
                return new Token(TokenType.RIGHT_OP, ">>");
            }

            if(current_char == '*' && peek(1) == '*'){
                advance();
                advance();
                return new Token(TokenType.POW_OP, "**");
            }

            if(current_char == '+' && peek(1) == '+'){
                advance();
                advance();
                return new Token(TokenType.INC_OP, "++");
            }

            if(current_char == '-' && peek(1) == '-'){
                advance();
                advance();
                return new Token(TokenType.DEC_OP, "--");
            }

            if(current_char == '&' && peek(1) == '&'){
                advance();
                advance();
                return new Token(TokenType.LOG_AND_OP, "&&");
            }

            if(current_char == '|' && peek(1) == '|'){
                advance();
                advance();
                return new Token(TokenType.LOG_OR_OP, "||");
            }

            if(current_char == '<' && peek(1) == '='){
                advance();
                advance();
                return new Token(TokenType.LE_OP, "<=");
            }

            if(current_char == '>' && peek(1) == '='){
                advance();
                advance();
                return new Token(TokenType.GE_OP, ">=");
            }

            if(current_char == '=' && peek(1) == '='){
                advance();
                advance();
                return new Token(TokenType.EQ_OP, "==");
            }

            if(current_char == '!' && peek(1) == '='){
                advance();
                advance();
                return new Token(TokenType.NE_OP, "!=");
            }

            if(current_char == '/' && peek(1) == '*'){
                multi_line_comment();
                continue;
            }

            if(current_char == '<'){
                advance();
                return new Token(TokenType.LT_OP, "<");
            }


            if(current_char == '>'){
                advance();
                return new Token(TokenType.GT_OP, ">");
            }


            if(current_char == '='){
                advance();
                return new Token(TokenType.ASSIGN, "=");
            }


            if(current_char == '!'){
                advance();
                return new Token(TokenType.LOG_NEG, "!");
            }


            if(current_char == '&'){
                advance();
                return new Token(TokenType.AND_OP, "&");
            }


            if(current_char == '|'){
                advance();
                return new Token(TokenType.OR_OP, "|");
            }


            if(current_char == '^'){
                advance();
                return new Token(TokenType.XOR_OP, "^");
            }


            if(current_char == '+'){
                advance();
                return new Token(TokenType.ADD_OP, "+");
            }


            if(current_char == '-'){
                advance();
                return new Token(TokenType.SUB_OP, "-");
            }


            if(current_char == '*'){
                advance();
                return new Token(TokenType.MUL_OP, "*");
            }


            if(current_char == '/'){
                advance();
                return new Token(TokenType.DIV_OP, "/");
            }


            if(current_char == '%'){
                advance();
                return new Token(TokenType.MOD_OP, "%");
            }


            if(current_char == '('){
                advance();
                return new Token(TokenType.L_PAREN, "(");
            }


            if(current_char == ')'){
                advance();
                return new Token(TokenType.R_PAREN, ")");
            }

            if(current_char == ':'){
                advance();
                return new Token(TokenType.COLON, ":");
            }

            if (current_char == ';') {
                advance();
                return new Token(TokenType.SEMICOLON, ";");
            }


            if(current_char == '.'){
                advance();
                return new Token(TokenType.DOT, ".");
            }


            if(current_char == ','){
                advance();
                return new Token(TokenType.COMMA, ",");
            }


            if(current_char == '#'){
                single_line_comment();
                continue;
            }


            if(current_char == '?'){
                advance();
                return new Token(TokenType.QUESTION_MARK, "?");
            }

            error("Invalid char <"+current_char+"> at line: "+line);
        }
        return new Token(TokenType.EOF, null);
    }


    //Getters & setters
    //------------------------------------------------------------------------------------------------------------------
    public boolean isThrow_exceptions() {
        return throw_exceptions;
    }
}
