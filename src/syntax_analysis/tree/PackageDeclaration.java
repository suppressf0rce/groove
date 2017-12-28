package syntax_analysis.tree;

public class PackageDeclaration extends Node {

    private String path;

    public PackageDeclaration(String path, int line){
        super(line);
        this.path = path;
    }
}
