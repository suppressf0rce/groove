package semantic_analysis.table;

public class Symbol {

    public String name;
    public Symbol type;

    public Symbol(String name, Symbol type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof Symbol) {
            return name.equals(((Symbol) obj).name);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
