package Interpreter;

import Interpreter.memory.Memory;
import lexical_analysis.TokenType;
import semantic_analysis.GType;
import semantic_analysis.table.Symbol;
import syntax_analysis.Visitor;
import syntax_analysis.tree.*;

import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class Interpreter implements Visitor {

    private Memory memory;

    private Interpreter() {
        memory = new Memory();
    }

    public static void run(Node tree) {
        Interpreter interpreter = new Interpreter();
        interpreter.load_files(tree);
        interpreter.load_functions(tree);
        interpreter.visit(tree);
        interpreter.memory.new_frame("main");
        Node node = (Node) interpreter.memory.get_item("main");
        interpreter.visit(node);
        interpreter.memory.del_frame();
    }

    private void load_files(Node tree) {
        //TODO: Come on don't be lazy
    }

    private void load_functions(Node tree) {
        //TODO: nije dovrseno do kara, fale parametri
        if (tree instanceof Program) {

            for (Node node : ((Program) tree).children) {
                if (node instanceof FunctionDeclaration) {
                    memory.set_item(((FunctionDeclaration) node).name, node);
                }
            }

        }
    }

    @Override
    public GType visit(Assign assign) {
        //TODO: this also
        //String var_name = assign.left.v
        return null;
    }

    @Override
    public GType visit(BinOp node) {
        try {
            if (node.op.type == TokenType.ADD_OP)
                return ((Terminal) visit(node.left)).add((Terminal) visit(node.right));
            else if (node.op.type == TokenType.SUB_OP)
                return ((Terminal) visit(node.left)).subb((Terminal) visit(node.right));
            else if (node.op.type == TokenType.MUL_OP)
                return ((Terminal) visit(node.left)).mul((Terminal) visit(node.right));
            else if (node.op.type == TokenType.DIV_OP)
                return ((Terminal) visit(node.left)).div((Terminal) visit(node.right));
            else if (node.op.type == TokenType.MOD_OP)
                return ((Terminal) visit(node.left)).mod((Terminal) visit(node.right));
            else if (node.op.type == TokenType.POW_OP)
                return ((Terminal) visit(node.left)).pow((Terminal) visit(node.right));
            else if (node.op.type == TokenType.LT_OP)
                return ((Terminal) visit(node.left)).lt((Terminal) visit(node.right));
            else if (node.op.type == TokenType.GT_OP)
                return ((Terminal) visit(node.left)).gt((Terminal) visit(node.right));
            else if (node.op.type == TokenType.GE_OP)
                return ((Terminal) visit(node.left)).ge((Terminal) visit(node.right));
            else if (node.op.type == TokenType.LE_OP)
                return ((Terminal) visit(node.left)).le((Terminal) visit(node.right));
            else if (node.op.type == TokenType.EQ_OP)
                return ((Terminal) visit(node.left)).eq((Terminal) visit(node.right));
            else if (node.op.type == TokenType.NE_OP)
                return ((Terminal) visit(node.left)).ne((Terminal) visit(node.right));
            else if (node.op.type == TokenType.AND_OP)
                return ((Terminal) visit(node.left)).and((Terminal) visit(node.right));
            else if (node.op.type == TokenType.OR_OP)
                return ((Terminal) visit(node.left)).or((Terminal) visit(node.right));
            else if (node.op.type == TokenType.XOR_OP)
                return ((Terminal) visit(node.left)).xor((Terminal) visit(node.right));
            else if (node.op.type == TokenType.LOG_AND_OP)
                return ((Terminal) visit(node.left)).log_and((Terminal) visit(node.right));
            else if (node.op.type == TokenType.LOG_OR_OP)
                return ((Terminal) visit(node.left)).log_or((Terminal) visit(node.right));

        } catch (Exception e) {
            System.err.println("Runtime error: " + e.getMessage());
            System.exit(1);
        }

        return null;
    }

    @Override
    public void visit(BreakStmt breakStmt) {

    }

    @Override
    public void visit(CompoundStmt compoundStmt) {
        memory.new_scope();
        for (Node child : compoundStmt.children) {
            visit(child);
        }


        memory.del_scope();
    }

    @Override
    public void visit(ContinueStmt continueStmt) {

    }

    @Override
    public void visit(DoWhileStmt doWhileStmt) {

    }

    @Override
    public GType visit(Expression expression) {
        GType expr = null;
        for (Node child : expression.children)
            expr = visit(child);
        return expr;
    }

    @Override
    public void visit(ForStmt forStmt) {
        visit(forStmt.setup);

        while (((Terminal) visit(forStmt.condition)).value.equals("true")) {
            visit(forStmt.body);
            visit(forStmt.increment);
        }
    }

    @Override
    public GType visit(FunctionCall node) {
        ArrayList<GType> args = new ArrayList<>();
        for (Node arg : node.args)
            args.add(visit(arg));

        if (memory.get_item(node.name) instanceof Node) {
            memory.new_frame(node.name);

            int i = 0;
            for (GType arg : args) {
                memory.declare(i + "", null);
                memory.set_item(i + "", arg);
                i++;
            }

            GType res = visit((Node) memory.get_item(node.name));
            memory.del_frame();
            return res;
        } else {
            //TODO: Builtin functions calls;
            //return new Terminal(memory.get_item(node.name).c,);
        }
        return null;
    }

    @Override
    public GType visit(FunctionDeclaration functionDeclaration) {
        int i = 0;
        for (Param param : functionDeclaration.params) {
            memory.set_item(param.var_node.value, memory.stack.current_frame.current_scope.values.remove(i + ""));
            i++;
        }

        return visit(functionDeclaration.body);
    }

    @Override
    public GType visit(FunctionBody compoundStmt) {
        for (Node child : compoundStmt.children) {
            if (child instanceof ReturnStmt)
                return visit(child);
            visit(child);
        }
        return null;
    }

    @Override
    public void visit(IfStmt ifStmt) {
        GType result = visit(ifStmt.condition);
        if (((Terminal) result).value.equals("true")) {
            visit(ifStmt.tbody);
        } else
            visit(ifStmt.fbody);
    }

    @Override
    public void visit(ImportFile importFile) {

    }

    @Override
    public GType visit(Node node) {
        if (node instanceof Assign)
            return visit((Assign) node);
        else if (node instanceof BinOp)
            return visit((BinOp) node);
        else if (node instanceof BreakStmt)
            visit((BreakStmt) node);
        else if (node instanceof FunctionBody)
            visit((FunctionBody) node);
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
            return visit((FunctionDeclaration) node);
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

        return null;
    }

    @Override
    public void visit(NoOp noOp) {

    }

    @Override
    public GType visit(Num num) {
        if (num.token.type == TokenType.INTEGER_CONST)
            return new Terminal("int", num.token.value);
        else if (num.token.type == TokenType.CHAR_CONST)
            return new Terminal("char", num.token.value);
        else
            return new Terminal("float", num.token.value);
    }

    @Override
    public void visit(PackageDeclaration packageDeclaration) {
        //TODO: sacuvaj ime paketa bre!
    }

    @Override
    public Symbol visit(Param param) {
        return null;
    }

    @Override
    public void visit(Program program) {
        for (Node node : program.children) {
            if (!(node instanceof FunctionDeclaration) && !(node instanceof ImportFile) && !(node instanceof PackageDeclaration)) {
                visit(node);
            }
        }
    }

    @Override
    public GType visit(ReturnStmt returnStmt) {
        return visit(returnStmt.expression);
    }

    @Override
    public GType visit(StringConst stringConst) {
        return new Terminal("string", stringConst.token.value);
    }

    @Override
    public GType visit(TerOp terOp) {
        return null;
    }

    @Override
    public void visit(Type type) {

    }

    @Override
    public GType visit(UnOp node) {
        //TODO: Implement this...
        if (node.prefix) {


            //if(node.op.type == TokenType.AND_OP)
            //return node.expr.va
        }
        Node tmp = node.expr;
        System.out.println(tmp);
        return null;
    }

    @Override
    public GType visit(Var var) {
        return (GType) memory.get_item(var.value);

        //TODO: puca exception ako je variabla
    }

    @Override
    public void visit(VarDeclaration varDeclaration) {
        memory.declare(varDeclaration.var_node.value, null);
    }

    @Override
    public void visit(WhileStmt whileStmt) {
        while (((Terminal) visit(whileStmt.condition)).value.equals("true"))
            visit(whileStmt.body);
    }
}
