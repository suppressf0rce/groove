package semantic_analysis.table;

public class BuiltinTypeSymbol extends Symbol {

    public BuiltinTypeSymbol(String name) {
        super(name, null);
    }

    @Override
    public String toString() {
        return name;
    }
}
