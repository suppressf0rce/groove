package semantic_analysis;

import java.util.Arrays;
import java.util.HashMap;

public class GType {

    public static HashMap<String, String> types;
    public static String[] order = {"char", "string", "boolean", "int", "long", "float", "double"};

    static {
        types = new HashMap<>();
        types.put("char", "int");
        types.put("string", "string");
        types.put("boolean", "int");
        types.put("int", "int");
        types.put("long", "int");
        types.put("float", "float");
        types.put("double", "float");
    }

    public String type;

    public GType(String type) {
        this.type = type;
    }

    private GType calc_type(GType other) {
        int left_order = Arrays.asList(types).indexOf(type);
        int right_order = Arrays.asList(types).indexOf(other.type);
        return new GType(order[Math.max(left_order, right_order)]);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof GType) {
            if (GType.types.get(type) != null && GType.types.get(((GType) obj).type) != null) {
                return GType.types.get(type).equals(GType.types.get(((GType) obj).type));
            }
        }
        return false;
    }

    public GType add(GType other) {
        return calc_type(other);
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public int hashCode() {
        return GType.types.get(type).hashCode();

        //If we want to be sensitive to difference between double and float , or int and long we will use type.hashCode() instead
    }
}
