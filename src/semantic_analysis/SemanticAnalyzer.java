package semantic_analysis;

import lexical_analysis.TokenType;
import semantic_analysis.table.*;
import syntax_analysis.Visitor;
import syntax_analysis.tree.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class SemanticAnalyzer implements Visitor {

    public boolean throw_exceptions;
    private ScopedSymbolTable current_scope;

    private SemanticAnalyzer(boolean throw_exceptions) {
        current_scope = null;
        this.throw_exceptions = throw_exceptions;
    }

    public static void analyze(Node tree, boolean throw_exceptions) {
        SemanticAnalyzer analyzer = new SemanticAnalyzer(throw_exceptions);
        analyzer.visit(tree);
    }

    private void error(String message) {
        if (throw_exceptions) {
            try {
                throw new Exception("Semantic error: " + message);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                System.err.flush();
                System.exit(1);
            }
        } else {
            System.err.println("Semantic error: " + message);
            System.err.flush();
        }
    }

    //Visit Methods
    //------------------------------------------------------------------------------------------------------------------
    @Override
    public GType visit(Node node) {
        if (node instanceof Assign)
            return visit((Assign) node);
        else if (node instanceof BinOp)
            return visit((BinOp) node);
        else if (node instanceof BreakStmt)
            visit((BreakStmt) node);
        else if (node instanceof CompoundStmt)
            visit((CompoundStmt) node);
        else if (node instanceof ContinueStmt)
            visit((ContinueStmt) node);
        else if (node instanceof DoWhileStmt)
            visit((DoWhileStmt) node);
        else if (node instanceof Expression)
            return visit((Expression) node);
        else if (node instanceof ForStmt)
            visit((ForStmt) node);
        else if (node instanceof FunctionCall)
            return visit((FunctionCall) node);
        else if (node instanceof FunctionDeclaration)
            visit((FunctionDeclaration) node);
        else if (node instanceof IfStmt)
            visit((IfStmt) node);
        else if (node instanceof ImportFile)
            visit((ImportFile) node);
        else if (node instanceof NoOp)
            visit((NoOp) node);
        else if (node instanceof Num)
            return visit((Num) node);
        else if (node instanceof PackageDeclaration)
            visit((PackageDeclaration) node);
        else if (node instanceof Param)
            visit((Param) node);
        else if (node instanceof Program)
            visit((Program) node);
        else if (node instanceof ReturnStmt)
            return visit((ReturnStmt) node);
        else if (node instanceof StringConst)
            return visit((StringConst) node);
        else if (node instanceof TerOp)
            return visit((TerOp) node);
        else if (node instanceof Type)
            visit((Type) node);
        else if (node instanceof UnOp)
            return visit((UnOp) node);
        else if (node instanceof Var)
            return visit((Var) node);
        else if (node instanceof VarDeclaration)
            visit((VarDeclaration) node);
        else if (node instanceof WhileStmt)
            visit((WhileStmt) node);
        else if (node instanceof FunctionBody)
            visit((FunctionBody) node);

        return null;
    }

    @Override
    public GType visit(Assign assign) {
        GType right = visit(assign.right);
        GType left = visit(assign.left);

        if (!right.equals(left)) {
            error("Incompatible types when assigning to type <" + left + "> from type <" + right + "> at line: " + assign.line);
        }
        return right;
    }

    @Override
    public GType visit(BinOp binOp) {
        GType ltype = visit(binOp.left);
        GType rtype = visit(binOp.right);

        if (binOp.op.type == TokenType.AND_OP || binOp.op.type == TokenType.OR_OP || binOp.op.type == TokenType.XOR_OP) {
            if (!ltype.equals(new GType("int")) || !ltype.equals(new GType("long")) ||
                    !rtype.equals(new GType("int")) || !rtype.equals(new GType("long")))
                error("Unsupported types at bitwise operator <" + ltype.type + "> and <" + rtype.type + "> at line: " + binOp.line);
        }
        return ltype.add(rtype);
    }

    @Override
    public void visit(BreakStmt breakStmt) {

    }

    @Override
    public void visit(FunctionBody functionBody) {
        current_scope = new ScopedSymbolTable(current_scope.name, current_scope.scope_level + 1, current_scope);

        for (Node child : functionBody.children) {
            if (child instanceof ReturnStmt)
                functionBody.returnStmt = (ReturnStmt) child;
            visit(child);
        }

        current_scope = current_scope.enclosing_scope;
    }

    @Override
    public void visit(CompoundStmt compoundStmt) {
        current_scope = new ScopedSymbolTable(current_scope.name, current_scope.scope_level + 1, current_scope);

        for (Node child : compoundStmt.children)
            visit(child);

        current_scope = current_scope.enclosing_scope;
    }

    @Override
    public void visit(ContinueStmt continueStmt) {

    }

    @Override
    public void visit(DoWhileStmt doWhileStmt) {
        visit(doWhileStmt.condition);
        visit(doWhileStmt.body);
    }

    @Override
    public GType visit(Expression expression) {
        GType expr = null;
        for (Node child : expression.children) {
            expr = visit(child);
        }
        return expr;
    }

    @Override
    public void visit(ForStmt forStmt) {
        visit(forStmt.setup);
        visit(forStmt.condition);
        visit(forStmt.increment);
        visit(forStmt.body);
    }

    @Override
    public GType visit(FunctionCall functionCall) {
        String func_name = functionCall.name;

        ArrayList<GType> found = new ArrayList<>();
        for (Node arg : functionCall.args) {
            GType arg_type = visit(arg);
            found.add(arg_type);
        }

        Symbol func_symbol = current_scope.lookup(new FunctionKey(func_name, found), false);
        if (func_symbol == null)
            error("Function '" + func_name + "(" + found.stream().map(Object::toString)
                    .collect(Collectors.joining(", ")) + ")' not found at line: " + functionCall.line);

        if (!(func_symbol instanceof FunctionSymbol))
            error("Function '" + func_name + "' cannot be used as a function at line: " + functionCall.line);

        if (((FunctionSymbol) func_symbol).params == null) {
            for (Node arg : functionCall.args)
                visit(arg);
            return new GType(func_symbol.type.name);
        }

        if (functionCall.args.size() != ((FunctionSymbol) func_symbol).params.size())
            error("Function '" + func_name + "' takes " + ((FunctionSymbol) func_symbol).params.size() + " arguments but "
                    + functionCall.args.size() + " were given at line: " + functionCall.line);


        ArrayList<GType> expected = new ArrayList<>();


        int i = 0;
        for (Node ignored : functionCall.args) {
            GType param_type = new GType(((FunctionSymbol) func_symbol).params.get(i).type.name);
            expected.add(param_type);
            i++;
        }

        if (!expected.equals(found)) {
            error("Incompatible argument types for function <" + func_name + "(" +
                    expected.stream().map(Object::toString)
                            .collect(Collectors.joining(", ")) + ")> but found <" + func_name + "(" +
                    found.stream().map(Object::toString)
                            .collect(Collectors.joining(", ")) + ")> at line: " + functionCall.line);
        }
        return new GType(func_symbol.type.name);
    }

    @Override
    public void visit(FunctionDeclaration functionDeclaration) {
        String type_name = functionDeclaration.type_node.value;
        Symbol type_symbol = current_scope.lookup(type_name, false);

        String function_name = functionDeclaration.name;
        FunctionSymbol function_symbol = new FunctionSymbol(function_name, type_symbol, null);

        current_scope = new ScopedSymbolTable(function_name, current_scope.scope_level + 1, current_scope);

        for (Param param : functionDeclaration.params)
            function_symbol.params.add(visit(param));

        if (current_scope.enclosing_scope.lookup(function_name, false) != null) {

            ArrayList<GType> params = new ArrayList<>();
            for (Symbol symbol : function_symbol.params) {
                params.add(new GType(symbol.type.name));
            }

            if (current_scope.enclosing_scope.lookup(new FunctionKey(function_name, params), false) != null)
                error("Duplicate function '" + function_name + "(" +
                        params.stream().map(Object::toString)
                                .collect(Collectors.joining(", ")) + ")' found at line: " + functionDeclaration.line);
        }

        current_scope.enclosing_scope.insert(function_symbol);

        visit(functionDeclaration.body);

        //Check if function needs return statement
        if (!type_name.equals("void")) {
            if (functionDeclaration.body.returnStmt == null) {
                ArrayList<GType> params = new ArrayList<>();
                for (Symbol symbol : function_symbol.params) {
                    params.add(new GType(symbol.type.name));
                }

                error("Function '" + function_name + "("
                        + params.stream().map(Object::toString).collect(Collectors.joining(", "))
                        + ")' expects return statement but it wasn't found at line: " + functionDeclaration.line);
            } else {

                //Checking if the return statements match each other

                ArrayList<GType> params = new ArrayList<>();
                for (Symbol symbol : function_symbol.params) {
                    params.add(new GType(symbol.type.name));
                }
                GType returnType = visit(functionDeclaration.body.returnStmt);
                if (!new GType(type_name).equals(returnType)) {
                    error("Incompatible return type for function '" + function_name + "("
                            + params.stream().map(Object::toString).collect(Collectors.joining(", "))
                            + ")' expects '" + type_name + "' but found '" + returnType + "' at line: " + functionDeclaration.line);
                }

            }
        }

        current_scope = current_scope.enclosing_scope;
    }

    @Override
    public void visit(IfStmt ifStmt) {
        visit(ifStmt.condition);
        visit(ifStmt.tbody);
        visit(ifStmt.fbody);
    }

    @Override
    public void visit(ImportFile importFile) {
        //TODO: add imports support
    }

    @Override
    public void visit(NoOp noOp) {

    }

    @Override
    public GType visit(Num num) {
        if (num.token.type == TokenType.INTEGER_CONST)
            return new GType("int");
        else if (num.token.type == TokenType.REAL_CONST)
            return new GType("float");
        else if (num.token.type == TokenType.CHAR_CONST)
            return new GType("char");
        else
            return new GType("boolean");
    }

    @Override
    public void visit(PackageDeclaration packageDeclaration) {

    }

    @Override
    public Symbol visit(Param param) {
        String type_name = param.type_node.value;
        Symbol type_symbol = current_scope.lookup(type_name, false);

        String var_name = param.var_node.value;
        VarSymbol var_symbol = new VarSymbol(var_name, type_symbol);

        if (current_scope.lookup(var_name, true) != null)
            error("Duplicate parameter '" + var_name + "' found at line: " + param.line);

        current_scope.insert(var_symbol);
        return var_symbol;
    }

    @Override
    public void visit(Program program) {
        ScopedSymbolTable global_scope = new ScopedSymbolTable("global", 1, current_scope);
        global_scope.init_builtins();
        current_scope = global_scope;

        for (Node child : program.children)
            visit(child);

        if (current_scope.lookup("main", false) == null)
            error("Undeclared mandatory function main");

        current_scope = current_scope.enclosing_scope;
    }

    @Override
    public GType visit(ReturnStmt returnStmt) {
        return visit(returnStmt.expression);
    }

    @Override
    public GType visit(StringConst stringConst) {
        return new GType("string");
    }

    @Override
    public GType visit(TerOp terOp) {
        visit(terOp.condition);
        GType texpr = visit(terOp.texpression);
        GType fexpr = visit(terOp.fexpression);

        if (!texpr.equals(fexpr)) {
            error("Incompatible types at ternary operator '" + texpr.toString() + "' and '" + fexpr.toString() + "' at line: " + terOp.line);
        }

        return texpr;
    }

    @Override
    public void visit(Type type) {

    }

    @Override
    public GType visit(UnOp unOp) {
        return visit(unOp.expr);
    }

    @Override
    public GType visit(Var var) {
        String var_name = var.value;
        Symbol var_symbol = current_scope.lookup(var_name, false);
        if (var_symbol == null) {
            error("Variable '" + var_name + "' not found at line: " + var.line);
        }
        return new GType(var_symbol.type.name);
    }

    @Override
    public void visit(VarDeclaration varDeclaration) {
        String type_name = varDeclaration.type_node.value;
        Symbol type_symbol = current_scope.lookup(type_name, false);

        String var_name = varDeclaration.var_node.value;
        VarSymbol var_symbol = new VarSymbol(var_name, type_symbol);

        if (current_scope.lookup(var_name, true) != null) {
            error("Duplicate identifier '" + var_name + "',  found at line: " + varDeclaration.line);
        }

        current_scope.insert(var_symbol);

    }

    @Override
    public void visit(WhileStmt whileStmt) {
        visit(whileStmt.condition);
        visit(whileStmt.body);
    }
}
