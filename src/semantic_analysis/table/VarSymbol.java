package semantic_analysis.table;

public class VarSymbol extends Symbol {

    public VarSymbol(String name, Symbol type) {
        super(name, type);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VarSymbol) {

            return type.equals(((VarSymbol) obj).type);

        }

        return false;
    }

    @Override
    public String toString() {
        return "Variable(name='" + name + "', type='" + type.toString() + "')";
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }
}
