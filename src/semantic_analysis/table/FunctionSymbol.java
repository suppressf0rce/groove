package semantic_analysis.table;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FunctionSymbol extends Symbol {

    public ArrayList<Symbol> params;

    public FunctionSymbol(String name, Symbol type, ArrayList<Symbol> params) {
        super(name, type);

        if (params == null)
            this.params = new ArrayList<>();
        else
            this.params = params;
    }

    @Override
    public String toString() {

        return "<" + FunctionSymbol.class.getName() + "(type=, name=" + name + ", parameters=" + params.stream().map(Object::toString)
                .collect(Collectors.joining(", ")) + ")>";
    }
}
