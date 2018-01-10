package syntax_analysis;

import semantic_analysis.GType;
import semantic_analysis.table.Symbol;
import syntax_analysis.tree.*;

public interface Visitor {

    public GType visit(Assign assign);

    public GType visit(BinOp binOp);

    public GType visit(BreakStmt breakStmt);

    public GType visit(CompoundStmt compoundStmt);

    public GType visit(ContinueStmt continueStmt);
    public void visit(DoWhileStmt doWhileStmt);

    public GType visit(Expression expression);
    public void visit(ForStmt forStmt);

    public GType visit(FunctionCall functionCall);

    public GType visit(FunctionDeclaration functionDeclaration);

    public GType visit(FunctionBody compoundStmt);

    public GType visit(IfStmt ifStmt);
    public void visit(ImportFile importFile);

    public GType visit(Node node);
    public void visit(NoOp noOp);

    public GType visit(Num num);
    public void visit(PackageDeclaration packageDeclaration);

    public Symbol visit(Param param);
    public void visit(Program program);

    public GType visit(ReturnStmt returnStmt);

    public GType visit(StringConst stringConst);

    public GType visit(TerOp terOp);
    public void visit(Type type);

    public GType visit(UnOp unOp);

    public GType visit(Var var);
    public void visit(VarDeclaration varDeclaration);
    public void visit(WhileStmt whileStmt);

}
