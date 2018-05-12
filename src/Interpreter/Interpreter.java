package Interpreter;

import Interpreter.memory.Memory;
import lexical_analysis.TokenType;
import semantic_analysis.GType;
import semantic_analysis.table.FunctionKey;
import semantic_analysis.table.Symbol;
import syntax_analysis.Visitor;
import syntax_analysis.tree.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;

@SuppressWarnings("Duplicates")
public class Interpreter implements Visitor {

    //TODO: IMplement switch case

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
        if (tree instanceof Program) {

            for (Node node : ((Program) tree).children) {
                if (node instanceof ImportFile) {

                    if (((ImportFile) node).path.startsWith("groove.")) {
                        String path[] = ((ImportFile) node).path.split("\\.");
                        String className = path[path.length - 1];

                        try {
                            Class<?> c = Class.forName("Interpreter.groove." + className);
                            for (Method method : c.getMethods()) {
                                ArrayList<GType> params = new ArrayList<>();
                                for (Parameter param : method.getParameters()) {
                                    String param_type = param.getType().getTypeName();
                                    if (param_type.endsWith("String"))
                                        param_type = "string";
                                    params.add(new GType(param_type));
                                }
                                memory.set_item(new FunctionKey(method.getName(), params), method);
                            }
                        } catch (ClassNotFoundException e) {
                            System.err.println("Runtime error could not find builtin class '" + className + "'");
                            System.err.flush();
                            System.exit(1);
                        }
                        //
                    } else {
                        //TODO: Some other file
                    }

                }
            }

        }
    }

    private void load_functions(Node tree) {
        if (tree instanceof Program) {

            for (Node node : ((Program) tree).children) {
                if (node instanceof FunctionDeclaration) {
                    ArrayList<GType> params = new ArrayList<>();
                    for (Param param : ((FunctionDeclaration) node).params) {
                        params.add(new GType(param.type_node.value));
                    }
                    memory.set_item(new FunctionKey(((FunctionDeclaration) node).name, params), node);
                    if (((FunctionDeclaration) node).name.equals("main")) {
                        memory.set_item(((FunctionDeclaration) node).name, node);
                    }
                }
            }

        }
    }

    @Override
    public GType visit(Assign assign) {
        String var_name = assign.left.value;
        Terminal value = (Terminal) memory.get_item(var_name);
        Terminal right = (Terminal) visit(assign.right);
        if (assign.op.type == TokenType.ADD_ASSIGN) {
            Terminal result = null;
            try {
                result = value.addT(right);
            } catch (Exception e) {
                System.err.println("Runtime error: could not execute addition assign at line: " + assign.line);
                System.err.flush();
                System.exit(1);
            }
            memory.set_item(var_name, result);
        } else if (assign.op.type == TokenType.SUB_ASSIGN) {
            Terminal result = null;
            try {
                result = value.subb(right);
            } catch (Exception e) {
                System.err.println("Runtime error: could not execute subtraction assign at line: " + assign.line);
                System.err.flush();
                System.exit(1);
            }
            memory.set_item(var_name, result);
        } else if (assign.op.type == TokenType.DIV_OP) {
            Terminal result = null;
            try {
                result = value.div(right);
            } catch (Exception e) {
                System.err.println("Runtime error: could not execute division assign at line: " + assign.line);
                System.err.flush();
                System.exit(1);
            }
            memory.set_item(var_name, result);
        } else if (assign.op.type == TokenType.MUL_OP) {
            Terminal result = null;
            try {
                result = value.mul(right);
            } catch (Exception e) {
                System.err.println("Runtime error: could not execute multiplication assign at line: " + assign.line);
                System.err.flush();
                System.exit(1);
            }
            memory.set_item(var_name, result);
        } else if (assign.op.type == TokenType.POW_ASSIGN) {
            Terminal result = null;
            try {
                result = value.pow(right);
            } catch (Exception e) {
                System.err.println("Runtime error: could not execute power assign at line: " + assign.line);
                System.err.flush();
                System.exit(1);
            }
            memory.set_item(var_name, result);
        } else {
            memory.set_item(var_name, right);
        }
        return null;
    }

    @Override
    public GType visit(BinOp node) {
        try {
            if (node.op.type == TokenType.ADD_OP)
                return ((Terminal) visit(node.left)).addT((Terminal) visit(node.right));
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
    public GType visit(BreakStmt breakStmt) {
        return new GType("break");
    }

    @Override
    public GType visit(CompoundStmt compoundStmt) {
        memory.new_scope();
        GType result = null;
        for (Node child : compoundStmt.children) {
            result = visit(child);

            if (result != null && (result.type.equals("break") || result.type.equals("continue"))) {
                memory.del_scope();

                return result;
            }
        }

        memory.del_scope();

        return null;
    }

    @Override
    public GType visit(ContinueStmt continueStmt) {
        return new GType("continue");
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
            GType result = visit(forStmt.body);
            if (result != null && result.type.equals("break"))
                break;
            visit(forStmt.increment);
        }
    }

    @Override
    public GType visit(FunctionCall node) {
        ArrayList<GType> args = new ArrayList<>();
        for (Node arg : node.args)
            args.add(visit(arg));

        if (memory.get_item(new FunctionKey(node.name, args)) instanceof Node) {
            memory.new_frame(node.name);

            int i = 0;
            for (GType arg : args) {
                memory.declare(i + "", null, null);
                memory.set_item(i + "", arg);
                i++;
            }

            GType res = visit((Node) memory.get_item(new FunctionKey(node.name, args)));
            memory.del_frame();
            return res;
        } else if (memory.get_item(new FunctionKey(node.name, args)) instanceof Method) {
            //Builtin function
            Method method = (Method) memory.get_item(new FunctionKey(node.name, args));
            Object[] arguments = new Object[args.size()];
            int i = 0;
            for (GType t : args) {
                if (t instanceof Terminal) {
                    arguments[i] = Terminal.toObject(t.type, ((Terminal) t).value);
                    i++;
                }
            }
            try {
                Object result = method.invoke(this, arguments);
                if (!method.getReturnType().equals(Void.TYPE)) {
                    String return_type = method.getReturnType().toString();
                    if (return_type.endsWith("String"))
                        return_type = "string";

                    return new Terminal(return_type, result.toString());
                }

            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            //return new Terminal(memory.get_item(node.name).c,);
        } else {
            System.err.println("Runtime error: function '" + node.name + "' not found at line: " + node.line);
            System.err.flush();
            System.exit(1);
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
    public GType visit(IfStmt ifStmt) {
        GType result = visit(ifStmt.condition);
        GType tmp = null;
        if (((Terminal) result).value.equals("true")) {
            tmp = visit(ifStmt.tbody);
        } else
            tmp = visit(ifStmt.fbody);

        return tmp;
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
            return visit((BreakStmt) node);
        else if (node instanceof FunctionBody)
            visit((FunctionBody) node);
        else if (node instanceof CompoundStmt)
            return visit((CompoundStmt) node);
        else if (node instanceof ContinueStmt)
            return visit((ContinueStmt) node);
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
            return visit((IfStmt) node);
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
        else if (num.token.type == TokenType.BOOLEAN_CONST)
            return new Terminal("boolean", num.token.value);
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
        GType result = visit(terOp.condition);
        GType tmp = null;
        if (((Terminal) result).value.equals("true")) {
            tmp = visit(terOp.texpression);
        } else
            tmp = visit(terOp.fexpression);

        return tmp;
    }

    @Override
    public void visit(Type type) {

    }

    @Override
    public GType visit(UnOp node) {
        if (node.prefix) {
            if (node.op.type == TokenType.INC_OP) {
                Terminal val = (Terminal) visit(node.expr);
                Terminal result = null;
                try {
                    result = val.addT(new Terminal("int", "1"));
                } catch (Exception e) {
                    System.err.println("Runtime error: could not increment value at line: " + node.line);
                    System.err.flush();
                    System.exit(1);
                }

                if (node.expr instanceof Var) {
                    memory.set_item(((Var) node.expr).value, result);
                }
                return result;
            } else if (node.op.type == TokenType.DEC_OP) {
                Terminal val = (Terminal) visit(node.expr);
                Terminal result = null;
                try {
                    result = val.subb(new Terminal("int", "1"));
                } catch (Exception e) {
                    System.err.println("Runtime error: could not decrement value at line: " + node.line);
                    System.err.flush();
                    System.exit(1);
                }

                if (node.expr instanceof Var) {
                    memory.set_item(((Var) node.expr).value, result);
                }
                return result;
            } else if (node.op.type == TokenType.SUB_OP) {
                Terminal val = (Terminal) visit(node.expr);
                Terminal result = null;
                try {
                    result = val.mul(new Terminal("int", "-1"));
                } catch (Exception e) {
                    System.err.println("Runtime error: could not negate value at line: " + node.line);
                    System.err.flush();
                    System.exit(1);
                }
                return result;
            } else if (node.op.type == TokenType.ADD_OP) {
                return visit(node.expr);
            } else if (node.op.type == TokenType.LOG_NEG) {
                Terminal val = (Terminal) visit(node.expr);
                Terminal result = null;
                try {
                    result = val.not();
                } catch (Exception e) {
                    System.err.println("Runtime error: could not logically negate value at line: " + node.line);
                    System.err.flush();
                    System.exit(1);
                }
                return result;
            } else {
                Terminal res = (Terminal) visit(node.expr);
                return new Terminal(node.op.value, res.value);
            }
        } else {
            if (node.op.type == TokenType.INC_OP) {
                Terminal val = (Terminal) visit(node.expr);
                Terminal result = null;
                try {
                    result = val.addT(new Terminal("int", "1"));
                } catch (Exception e) {
                    System.err.println("Runtime error: could not increment value at line: " + node.line);
                    System.err.flush();
                    System.exit(1);
                }

                if (node.expr instanceof Var) {
                    memory.set_item(((Var) node.expr).value, result);
                }
                return val;
            } else if (node.op.type == TokenType.DEC_OP) {
                Terminal val = (Terminal) visit(node.expr);
                Terminal result = null;
                try {
                    result = val.subb(new Terminal("int", "1"));
                } catch (Exception e) {
                    System.err.println("Runtime error: could not decrement value at line: " + node.line);
                    System.err.flush();
                    System.exit(1);
                }

                if (node.expr instanceof Var) {
                    memory.set_item(((Var) node.expr).value, result);
                }
                return val;
            }
        }
        return visit(node.expr);
    }

    @Override
    public GType visit(Var var) {
        return (GType) memory.get_item(var.value);
    }

    @Override
    public void visit(VarDeclaration varDeclaration) {
        memory.declare(varDeclaration.var_node.value, null, varDeclaration.type_node.value);
    }

    @Override
    public void visit(WhileStmt whileStmt) {
        while (((Terminal) visit(whileStmt.condition)).value.equals("true")) {
            GType result = visit(whileStmt.body);
            if (result != null && result.type.equals("break"))
                break;
        }
    }
}
