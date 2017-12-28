package syntax_analysis;

import syntax_analysis.tree.*;

public interface Visitor {

    public void visit(Assign assign);
    public void visit(BinOp binOp);
    public void visit(BreakStmt breakStmt);
    public void visit(CompoundStmt compoundStmt);
    public void visit(ContinueStmt continueStmt);
    public void visit(DoWhileStmt doWhileStmt);
    public void visit(Expression expression);
    public void visit(ForStmt forStmt);
    public void visit(FunctionCall functionCall);
    public void visit(FunctionDeclaration functionDeclaration);
    public void visit(IfStmt ifStmt);
    public void visit(ImportFile importFile);
    public void visit(Node node);
    public void visit(NoOp noOp);
    public void visit(Num num);
    public void visit(PackageDeclaration packageDeclaration);
    public void visit(Param param);
    public void visit(Program program);
    public void visit(ReturnStmt returnStmt);
    public void visit(StringConst stringConst);
    public void visit(TerOp terOp);
    public void visit(Type type);
    public void visit(UnOp unOp);
    public void viist(Var var);
    public void visit(VarDeclaration varDeclaration);
    public void visit(WhileStmt whileStmt);

}
