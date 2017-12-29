package semantic_analysis.table;

import semantic_analysis.GType;

import java.util.ArrayList;

public class FunctionKey {

    public String name;
    public ArrayList<GType> params;

    public FunctionKey(String name, ArrayList<GType> params) {
        this.name = name;
        this.params = params;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj instanceof FunctionKey) {

            boolean nameMatch = name.equals(((FunctionKey) obj).name);
            boolean paramsMatch = params.equals(((FunctionKey) obj).params);

            return nameMatch && paramsMatch;
        }

        return false;
    }

    @Override
    public int hashCode() {
        return name.hashCode() * params.hashCode();
    }
}
