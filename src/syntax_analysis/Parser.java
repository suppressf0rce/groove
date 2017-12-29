package syntax_analysis;

import lexical_analysis.Lexer;
import lexical_analysis.Token;
import lexical_analysis.TokenType;
import syntax_analysis.tree.*;

import java.util.ArrayList;

public class Parser implements Cloneable {

    private Lexer lexer;
    private Token current_token;

    private boolean throw_exceptions;

    //Constructor
    //------------------------------------------------------------------------------------------------------------------
    public Parser(Lexer lexer) {
        this.lexer = lexer;
        current_token = lexer.get_next_token();
        throw_exceptions = lexer.isThrow_exceptions();
    }

    private void error(String message) {
        if (throw_exceptions) {
            try {
                throw new Exception("Syntax error: " + message);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.err.flush();
                System.exit(1);
            }
        } else {
            System.err.println("Syntax error: " + message);
            System.err.flush();
        }
    }

    /**
     * Compare the current token type with the passed token
     * type and if they match then "eat" the current_token
     * and assign the next token to the current_token,
     * otherwise raise an error.
     *
     * @param tokenType that will be compared with the current_token
     */
    private void eat(TokenType tokenType) {
        if (current_token.type == tokenType)
            current_token = lexer.get_next_token();
        else
            error("Expected token <" + tokenType + "> but found <" + current_token.type + "> at line: " + lexer.line);
    }

    private Node program() {
        Node root = new Program(declarations(), lexer.line);
        return root;
    }

    private ArrayList<Node> declarations() {
        ArrayList<Node> declarations = new ArrayList<>();

        declarations.add(package_declaration());
        while (current_token.type == TokenType.IMPORT ||
                current_token.type == TokenType.LET) {

            if (current_token.type == TokenType.IMPORT)
                declarations.add(import_file());


            if (current_token.type == TokenType.LET) {
                //We need to check if its function
                if (check_function()) {
                    declarations.add(function_declaration());
                } else {
                    declarations.addAll(declaration_list());
                }
            }

        }

        return declarations;
    }

    private PackageDeclaration package_declaration() {
        eat(TokenType.PACKAGE);

        StringBuilder path = new StringBuilder(current_token.value);
        eat(TokenType.ID);

        while (current_token.type != TokenType.EOL) {
            path.append(current_token.value);
            eat(TokenType.DOT);
            path.append(current_token.value);
            eat(TokenType.ID);
        }

        int line = lexer.line - 1;
        eat(TokenType.EOL);
        while (current_token.type == TokenType.EOL)
            eat(TokenType.EOL);

        return new PackageDeclaration(path.toString(), line);
    }

    private ImportFile import_file() {
        eat(TokenType.IMPORT);

        StringBuilder path = new StringBuilder(current_token.value);
        eat(TokenType.ID);

        while (current_token.type != TokenType.EOL) {
            path.append(current_token.value);
            eat(TokenType.DOT);
            path.append(current_token.value);
            if (current_token.type == TokenType.ID)
                eat(TokenType.ID);
            else
                eat(TokenType.MUL_OP);
        }

        int line = lexer.line - 1;

        eat(TokenType.EOL);
        while (current_token.type == TokenType.EOL)
            eat(TokenType.EOL);

        return new ImportFile(path.toString(), line);
    }

    /**
     * Checks whether it is function
     *
     * @return
     */
    private boolean check_function() {
        Lexer tmpLexer = new Lexer(lexer);
        Lexer currentLexer = lexer;
        Token currentToken = current_token;
        lexer = tmpLexer;
        eat(TokenType.LET);
        boolean result = current_token.type == TokenType.FUNCTION;
        lexer = currentLexer;
        current_token = currentToken;
        return result;
    }

    private FunctionDeclaration function_declaration() {
        eat(TokenType.LET);
        eat(TokenType.FUNCTION);
        String name = current_token.value;
        eat(TokenType.ID);
        eat(TokenType.L_PAREN);
        ArrayList<Param> params = parameters();
        eat(TokenType.R_PAREN);
        eat(TokenType.BE);
        Type type_node = type_spec();
        int line = lexer.line;
        CompoundStmt body = compound_statement();

        return new FunctionDeclaration(type_node, name, params, body, line);
    }

    private ArrayList<Param> parameters() {
        ArrayList<Param> nodes = new ArrayList<>();
        if (current_token.type != TokenType.R_PAREN) {
            nodes.add(new Param(type_spec(), variable(), lexer.line));

            while (current_token.type == TokenType.COMMA) {
                eat(TokenType.COMMA);
                nodes.add(new Param(type_spec(), variable(), lexer.line));
            }
        }
        return nodes;
    }

    private ArrayList<Node> declaration_list() {
        ArrayList<Node> result = declaration();
        while (current_token.type == TokenType.LET) {
            result.addAll(declaration());
        }
        return result;
    }

    private ArrayList<Node> declaration() {
        ArrayList<Node> result = new ArrayList<>();

        eat(TokenType.LET);
        ArrayList<Node> declarator_list = init_declarator_list();
        eat(TokenType.BE);
        Type type_node = type_spec();
        int line = lexer.line;
        eat(TokenType.EOL);

        while (current_token.type == TokenType.EOL)
            eat(TokenType.EOL);

        for (Node node : declarator_list) {
            if (node instanceof Var)
                result.add(new VarDeclaration((Var) node, type_node, line));
            else
                result.add(node);
        }

        return result;
    }

    private ArrayList<Node> init_declarator_list() {
        ArrayList<Node> result = new ArrayList<>();
        result.addAll(init_declarator());
        while (current_token.type == TokenType.COMMA) {
            eat(TokenType.COMMA);
            result.addAll(init_declarator());
        }

        return result;
    }

    private ArrayList<Node> init_declarator() {
        ArrayList<Node> result = new ArrayList<>();
        Var var = variable();
        result.add(var);
        if (current_token.type == TokenType.ASSIGN) {
            Token token = current_token;
            eat(TokenType.ASSIGN);
            result.add(new Assign(var, assignment_expression(), token, lexer.line));
        }
        return result;
    }

    private Node statement(){
        if(check_iteration_statement())
            return iteration_statement();
        else if(check_selection_statement())
            return selection_statement();
        else if(check_jump_statement())
            return jump_statement();
        else if(check_compound_statement())
            return compound_statement();

        return expression_statement(true);
    }

    private boolean check_compound_statement(){
        return current_token.type == TokenType.COLON;
    }

    private CompoundStmt compound_statement() {
        ArrayList<Node> result = new ArrayList<>();
        eat(TokenType.COLON);

        while (current_token.type == TokenType.EOL)
            eat(TokenType.EOL);

        while (current_token.type != TokenType.END) {
            if (current_token.type == TokenType.LET) {
                result.addAll(declaration_list());
            } else {
                result.add(statement());
                break;
            }
        }

        eat(TokenType.END);

        int line = lexer.line - 1;
        eat(TokenType.EOL);
        while (current_token.type == TokenType.EOL)
            eat(TokenType.EOL);

        return new CompoundStmt(result, line);
    }

    private boolean check_jump_statement(){
        return  current_token.type == TokenType.RETURN  ||
                current_token.type == TokenType.BREAK   ||
                current_token.type == TokenType.CONTINUE;
    }

    private Node jump_statement(){
        if(current_token.type == TokenType.RETURN){
            eat(TokenType.RETURN);
            Node expression = empty();
            if(current_token.type != TokenType.EOL)
                expression = expression();

            int line = lexer.line;
            eat(TokenType.EOL);
            while(current_token.type == TokenType.EOL)
                eat(TokenType.EOL);

            return new ReturnStmt(expression, line);
        }else if(current_token.type == TokenType.BREAK){
            eat(TokenType.BREAK);

            int line = lexer.line;
            eat(TokenType.EOL);
            while(current_token.type == TokenType.EOL)
                eat(TokenType.EOL);

            return new BreakStmt(line);
        }else if(current_token.type == TokenType.CONTINUE){
            eat(TokenType.CONTINUE);

            int line = lexer.line;
            eat(TokenType.EOL);
            while(current_token.type == TokenType.EOL)
                eat(TokenType.EOL);

            return new ContinueStmt(line);
        }

        return null;
    }

    private boolean check_selection_statement(){
        return current_token.type == TokenType.IF;
    }

    private Node selection_statement(){
        if(current_token.type == TokenType.IF){
            eat(TokenType.IF);
            eat(TokenType.L_PAREN);
            Expression condition = expression();
            eat(TokenType.R_PAREN);
            Node tstatement = statement();
            Node fStatement = empty();
            if(current_token.type == TokenType.ELSE){
                eat(TokenType.ELSE);
                fStatement = statement();
            }
            return new IfStmt(condition, tstatement, fStatement, lexer.line);
        }

        return null;
    }

    private boolean check_iteration_statement(){
        return  current_token.type == TokenType.WHILE   ||
                current_token.type == TokenType.DO      ||
                current_token.type == TokenType.FOR;
    }

    private Node iteration_statement(){
        if(current_token.type == TokenType.WHILE){
            eat(TokenType.WHILE);
            eat(TokenType.L_PAREN);
            Expression expression = expression();
            eat(TokenType.R_PAREN);
            Node statement = statement();
            return new WhileStmt(expression, statement, lexer.line);
        }else if(current_token.type == TokenType.DO){
            eat(TokenType.DO);
            Node statement = statement();
            eat(TokenType.WHILE);
            eat(TokenType.L_PAREN);
            Expression expressoin = expression();
            eat(TokenType.R_PAREN);

            int line = lexer.line;
            eat(TokenType.EOL);
            while(current_token.type == TokenType.EOL)
                eat(TokenType.EOL);

            return new DoWhileStmt(expressoin, statement, line);
        }else{
            eat(TokenType.FOR);
            eat(TokenType.L_PAREN);
            Node setup = expression_statement(false);
            eat(TokenType.COLON);
            while (current_token.type == TokenType.EOL)
                eat(TokenType.EOL);
            Node condition = expression_statement(false);
            eat(TokenType.COLON);
            while (current_token.type == TokenType.EOL)
                eat(TokenType.EOL);
            Node increment = new NoOp(lexer.line);
            if(current_token.type != TokenType.R_PAREN){
                increment = expression();
            }
            eat(TokenType.R_PAREN);
            Node statement = statement();

            return new ForStmt(setup, condition, increment, statement, lexer.line);
        }
    }

    private Node expression_statement(boolean eatEOL){
        Node node = null;
        if(current_token.type != TokenType.EOL)
            node = expression();

        if(eatEOL){
            eat(TokenType.EOL);
            while(current_token.type == TokenType.EOL)
                eat(TokenType.EOL);
        }

        if(node == null)
            return new NoOp(lexer.line);
        else
            return node;
    }

    private Expression expression(){
        ArrayList<Node> result = new ArrayList<>();
        result.add(assignment_expression());

        while(current_token.type == TokenType.COMMA){
            eat(TokenType.COMMA);
            result.add(assignment_expression());
        }

        return new Expression(result, lexer.line);
    }

    private boolean check_assignment_expression() {
        Lexer tmpLexer = new Lexer(lexer);
        Lexer currentLexer = lexer;
        Token currentToken = current_token;
        lexer = tmpLexer;

        boolean result = false;
        if (current_token.type == TokenType.ID) {
            eat(TokenType.ID);
            result = current_token.type.toString().endsWith("ASSIGN");
        }

        //Restore lexer and token
        lexer = currentLexer;
        current_token = currentToken;

        return result;

    }

    private Node assignment_expression() {
        if (check_assignment_expression()) {
            Node node = variable();
            while (current_token.type.toString().endsWith("ASSIGN")) {
                Token token = current_token;
                eat(token.type);
                return new Assign(node, assignment_expression(), token, lexer.line);
            }
        }

        return conditional_expression();
    }

    private Node conditional_expression() {
        Node node = logical_and_expression();
        if (current_token.type == TokenType.QUESTION_MARK) {
            eat(TokenType.QUESTION_MARK);
            Expression texpression = expression();
            eat(TokenType.COLON);
            Node fexpression =  conditional_expression();
            return new TerOp(node, texpression, fexpression, lexer.line);
        }
        return node;
    }

    private Node logical_and_expression() {
        Node node = logical_or_expression();
        while (current_token.type == TokenType.LOG_AND_OP) {
            Token token = current_token;
            eat(token.type);
            node = new BinOp(node, token, logical_or_expression(), lexer.line);
        }

        return node;
    }

    private Node logical_or_expression() {
        Node node = inclusive_or_expression();
        while (current_token.type == TokenType.LOG_OR_OP) {
            Token token = current_token;
            eat(token.type);
            node = new BinOp(node, token, inclusive_or_expression(), lexer.line);
        }
        return node;
    }

    private Node inclusive_or_expression() {
        Node node = exclusive_or_expression();
        while (current_token.type == TokenType.OR_OP) {
            Token token = current_token;
            eat(token.type);
            node = new BinOp(node, token, exclusive_or_expression(), lexer.line);
        }
        return node;
    }

    private Node exclusive_or_expression() {
        Node node = and_expression();
        while (current_token.type == TokenType.XOR_OP) {
            Token token = current_token;
            eat(token.type);
            node = new BinOp(node, token, logical_and_expression(), lexer.line);
        }

        return node;
    }

    private Node and_expression() {
        Node node = equality_expression();
        while (current_token.type == TokenType.AND_OP) {
            Token token = current_token;
            eat(token.type);
            node = new BinOp(node, token, equality_expression(), lexer.line);
        }

        return node;
    }

    private Node equality_expression() {
        Node node = relational_expression();
        while (current_token.type == TokenType.EQ_OP || current_token.type == TokenType.NE_OP) {
            Token token = current_token;
            eat(token.type);
            node = new BinOp(node, token, relational_expression(), lexer.line);
        }

        return node;
    }

    private Node relational_expression() {
        Node node = shift_expression();
        while ( current_token.type == TokenType.LE_OP ||
                current_token.type == TokenType.LT_OP ||
                current_token.type == TokenType.GE_OP ||
                current_token.type == TokenType.GT_OP) {
            Token token = current_token;
            eat(token.type);
            node = new BinOp(node, token, shift_expression(), lexer.line);
        }
        return node;
    }

    private Node shift_expression() {
        Node node  = additive_expression();
        while(current_token.type == TokenType.LEFT_OP || current_token.type == TokenType.RIGHT_OP){
            Token token = current_token;
            eat(token.type);
            node = new BinOp(node, token, additive_expression(), lexer.line);
        }

        return node;
    }

    private Node additive_expression(){
        Node node = multiplicative_expression();
        while(current_token.type == TokenType.ADD_OP || current_token.type == TokenType.SUB_OP){
            Token token = current_token;
            eat(token.type);
            node = new BinOp(node, token, multiplicative_expression(), lexer.line);
        }

        return node;
    }

    private Node multiplicative_expression(){
        Node node = power_expression();
        while(  current_token.type == TokenType.MUL_OP  ||
                current_token.type == TokenType.DIV_OP  ||
                current_token.type == TokenType.MOD_OP){
            Token token = current_token;
            eat(token.type);
            node = new BinOp(node, token, power_expression(), lexer.line);
        }
        return node;
    }

    private Node power_expression(){
        Node node = cast_expression();
        while(current_token.type == TokenType.POW_OP){
            Token token = current_token;
            eat(token.type);
            node = new BinOp(node, token, cast_expression(), lexer.line);
        }
        return node;
    }

    private boolean check_cast_expression(){
        Lexer tmpLexer = new Lexer(lexer);
        Lexer currentLexer = lexer;
        Token currentToken = current_token;
        lexer = tmpLexer;

        boolean result = false;
        if(current_token.type == TokenType.L_PAREN){
            eat(TokenType.L_PAREN);
            if(     current_token.type == TokenType.BOOLEAN ||
                    current_token.type == TokenType.INT     ||
                    current_token.type == TokenType.LONG    ||
                    current_token.type == TokenType.STRING  ||
                    current_token.type == TokenType.FLOAT   ||
                    current_token.type == TokenType.DOUBLE  ||
                    current_token.type == TokenType.CHAR){
                eat(current_token.type);

                result = current_token.type == TokenType.R_PAREN;

            }
        }

        //Restore defaults
        lexer = currentLexer;
        current_token = currentToken;
        //return
        return result;
    }

    private Node cast_expression(){
        if(check_cast_expression()){
            eat(TokenType.L_PAREN);
            Type type_node = type_spec();
            eat(TokenType.R_PAREN);
            return new UnOp(cast_expression(), type_node.token, lexer.line);
        }else{
            return unary_expression();
        }
    }

    private Node unary_expression(){
        if(current_token.type == TokenType.INC_OP || current_token.type == TokenType.DEC_OP){
            Token token = current_token;
            eat(token.type);
            return new UnOp(unary_expression(), token, lexer.line);
        }else if (  current_token.type == TokenType.AND_OP ||
                    current_token.type == TokenType.ADD_OP ||
                    current_token.type == TokenType.SUB_OP ||
                    current_token.type == TokenType.LOG_NEG){
            Token token = current_token;
            eat(token.type);
            return new UnOp(cast_expression(), token, lexer.line);
        }else{
            return postfix_expression();
        }
    }

    private Node postfix_expression(){
        Node node = primary_expression();
        if(current_token.type == TokenType.INC_OP || current_token.type == TokenType.DEC_OP){
            Token token = current_token;
            eat(token.type);
            node = new UnOp(node, token, lexer.line);
            ((UnOp)node).prefix = false;
        }
        else if(current_token.type == TokenType.L_PAREN){
            eat(TokenType.L_PAREN);
            ArrayList<Node> args = new ArrayList<>();
            if(! (current_token.type == TokenType.R_PAREN)){
                args = argument_expression_list();
            }
            eat(TokenType.R_PAREN);
            if(! (node instanceof Var)){
                error("Function identifier must be a string");
            }else
            node = new FunctionCall(((Var)node).token.value, args, lexer.line);
        }

        return node;
    }

    private ArrayList<Node> argument_expression_list(){
        ArrayList<Node> args = new ArrayList<>();
        args.add(assignment_expression());

        while(current_token.type == TokenType.COMMA){
            eat(TokenType.COMMA);
            args.add(assignment_expression());
        }

        return args;
    }

    private Node primary_expression(){
        Token token = current_token;
        if(token.type == TokenType.L_PAREN){
            eat(TokenType.L_PAREN);
            Expression node = expression();
            eat(TokenType.R_PAREN);
            return node;
        }else if(   token.type == TokenType.INTEGER_CONST   ||
                    token.type == TokenType.REAL_CONST      ||
                    token.type == TokenType.CHAR_CONST      ||
                    token.type == TokenType.BOOLEAN_CONST){
            return constant();
        }else if(token.type == TokenType.STRING_CONST){
            return str();
        }else{
            return variable();
        }
    }

    private Num constant() {
        Token token = current_token;
        int line = lexer.line;

        if (token.type == TokenType.CHAR_CONST) {
            eat(TokenType.CHAR_CONST);
            return new Num(token, line);
        } else if (token.type == TokenType.INTEGER_CONST) {
            eat(TokenType.INTEGER_CONST);
            return new Num(token, line);
        } else if (token.type == TokenType.REAL_CONST) {
            eat(TokenType.REAL_CONST);
            return new Num(token, line);
        } else if (token.type == TokenType.BOOLEAN_CONST) {
            eat(TokenType.BOOLEAN_CONST);
            return new Num(token, line);
        } else {
            return null;
        }
    }

    private Type type_spec() {
        Token token = current_token;
        if (token.type == TokenType.BOOLEAN ||
                token.type == TokenType.INT ||
                token.type == TokenType.LONG ||
                token.type == TokenType.STRING ||
                token.type == TokenType.FLOAT ||
                token.type == TokenType.DOUBLE ||
                token.type == TokenType.VOID ||
                token.type == TokenType.CHAR ||
                token.type == TokenType.VOID) {
            eat(token.type);
            return new Type(token, lexer.line);
        }

        return null;
    }

    public Var variable() {
        Var node = new Var(current_token, lexer.line);
        eat(TokenType.ID);
        return node;
    }

    public NoOp empty(){
        return new NoOp(lexer.line);
    }

    public StringConst str() {
        Token token = current_token;
        eat(TokenType.STRING_CONST);
        return new StringConst(token, lexer.line);
    }

    /**
     * Parse function that creates AST, for the Groove
     * You can find program grammar inside grammar.md
     *
     * @return
     */
    public Node parse() {
        Node node = program();
        if (current_token.type != TokenType.EOF) {
            error("Expected token <EOF> but found <" + current_token.type.toString() + ">");
        }

        return node;
    }
}
