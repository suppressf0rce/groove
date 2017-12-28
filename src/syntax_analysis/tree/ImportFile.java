package syntax_analysis.tree;

public class ImportFile extends Node {

    private String path;

    public ImportFile(String path, int line){
        super(line);
        this.path = path;
    }
}
