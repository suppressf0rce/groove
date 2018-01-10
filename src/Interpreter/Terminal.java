package Interpreter;

import semantic_analysis.GType;

import java.util.Arrays;

@SuppressWarnings("Duplicates")
public class Terminal extends GType {

    public String value;

    public Terminal(String type, String value) {
        super(type);
        this.value = value;
    }

    public static Object toObject(String clazz, String value) {
        if (clazz.equals("char"))
            return value.charAt(0);
        if (clazz.equals("string"))
            return value;
        if (clazz.equals("boolean"))
            return Boolean.parseBoolean(value);
        if (clazz.equals("int"))
            return Integer.parseInt(value);
        if (clazz.equals("long"))
            return Long.parseLong(value);
        if (clazz.equals("float"))
            return Float.parseFloat(value);
        if (clazz.equals("double"))
            return Double.parseDouble(value);
        return value;
    }

    public static Terminal toTerminal(String value) {
        if (value.equals("true")) {
            return new Terminal("boolean", "true");
        } else if (value.equals("false")) {
            return new Terminal("boolean", "false");
        } else if (isInt(value)) {
            return new Terminal("int", value);
        } else if (isDouble(value)) {
            return new Terminal("double", value);
        } else {
            return new Terminal("string", value);
        }
    }

    private static boolean isInt(String str) {
        try {
            double v = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException nfe) {
        }
        return false;
    }

    private static boolean isDouble(String str) {
        try {
            double v = Double.parseDouble(str);
            return true;
        } catch (NumberFormatException nfe) {
        }
        return false;
    }

    private static Terminal convert(Terminal t, String toType) {
        if (toType.equals("boolean")) {
            if (t.type.equals("boolean"))
                return new Terminal("boolean", t.value);
            else
                return null;
        }

        if (toType.equals("char")) {
            if (t.type.equals("char")) {
                return new Terminal("char", t.value);
            } else
                return null;
        }

        if (toType.equals("int")) {
            if (t.type.equals("boolean")) {
                if (t.value.equals("true"))
                    return new Terminal("int", "1");
                else
                    return new Terminal("int", "0");
            } else if (t.type.equals("int"))
                return new Terminal("int", t.value);
            else if (t.type.equals("char")) {
                char chr = t.value.charAt(0);
                return new Terminal("int", (int) chr + "");
            } else
                return null;
        }

        if (toType.equals("long")) {
            if (t.type.equals("boolean")) {
                if (t.value.equals("true"))
                    return new Terminal("long", "1");
                else
                    return new Terminal("long", "0");
            } else if (t.type.equals("char")) {
                Character chr = t.value.charAt(0);
                return new Terminal("long", Integer.parseInt("" + chr) + "");
            } else if (t.type.equals("int"))
                return new Terminal("long", t.value);
            else if (t.type.equals("long"))
                return new Terminal("long", t.value);
            else
                return null;
        }

        if (toType.equals("float")) {
            if (t.type.equals("boolean")) {
                if (t.value.equals("true"))
                    return new Terminal("float", "1");
                else
                    return new Terminal("float", "0");
            } else if (t.type.equals("char")) {
                Character chr = t.value.charAt(0);
                return new Terminal("float", Integer.parseInt("" + chr) + "");
            } else if (t.type.equals("int"))
                return new Terminal("float", t.value);
            else if (t.type.equals("long"))
                return new Terminal("float", t.value);
            else if (t.type.equals("float"))
                return new Terminal("float", t.value);
            else
                return null;
        }

        if (toType.equals("double")) {
            if (t.type.equals("boolean")) {
                if (t.value.equals("true"))
                    return new Terminal("double", "1");
                else
                    return new Terminal("double", "0");
            } else if (t.type.equals("char")) {
                Character chr = t.value.charAt(0);
                return new Terminal("double", Integer.parseInt("" + chr) + "");
            } else if (t.type.equals("int"))
                return new Terminal("double", t.value);
            else if (t.type.equals("long"))
                return new Terminal("double", t.value);
            else if (t.type.equals("float"))
                return new Terminal("double", t.value);
            else if (t.type.equals("double"))
                return new Terminal("double", t.value);
            else
                return null;
        }

        if (toType.equals("string")) {
            return new Terminal("string", t.value);
        }

        return null;
    }

    private String getType(Terminal other) {
        int left_order = Arrays.asList(order).indexOf(type);
        int right_order = Arrays.asList(order).indexOf(other.type);
        return Terminal.order[Math.max(left_order, right_order)];
    }

    public Terminal addT(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Integer result = c1 + c2;
            return new Terminal("int", result.toString());
        } else if (l instanceof String) {
            return new Terminal(ttype, l.toString() + r.toString());
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Integer result = i1 + i2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Integer) {
            Integer result = (Integer) l + (Integer) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Long) {
            Long result = (Long) l + (Long) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Double) {
            Double result = (Double) l + (Double) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Float) {
            Float result = (Float) l + (Float) r;
            return new Terminal(ttype, result.toString());
        }

        throw new ArithmeticException("Invalid addition of '" + this + "' and '" + other + "'");
    }

    public Terminal subb(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Integer result = c1 - c2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Integer result = i1 - i2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Integer) {
            Integer result = (Integer) l - (Integer) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Long) {
            Long result = (Long) l - (Long) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Double) {
            Double result = (Double) l - (Double) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Float) {
            Float result = (Float) l - (Float) r;
            return new Terminal(ttype, result.toString());
        }

        throw new ArithmeticException("Invalid subtraction of '" + this + "' and '" + other + "'");
    }

    public Terminal mul(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Integer result = c1 * c2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Integer result = i1 * i2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Integer) {
            Integer result = (Integer) l * (Integer) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Long) {
            Long result = (Long) l * (Long) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Double) {
            Double result = (Double) l * (Double) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Float) {
            Float result = (Float) l * (Float) r;
            return new Terminal(ttype, result.toString());
        }

        throw new ArithmeticException("Invalid multiplication of '" + this + "' and '" + other + "'");
    }

    public Terminal div(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Integer result = c1 / c2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Integer result = i1 / i2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Integer) {
            Integer result = (Integer) l / (Integer) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Long) {
            Long result = (Long) l / (Long) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Double) {
            Double result = (Double) l / (Double) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Float) {
            Float result = (Float) l / (Float) r;
            return new Terminal(ttype, result.toString());
        }

        throw new ArithmeticException("Invalid division of '" + this + "' and '" + other + "'");
    }

    public Terminal mod(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Integer result = c1 % c2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Integer result = i1 % i2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Integer) {
            Integer result = (Integer) l % (Integer) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Long) {
            Long result = (Long) l % (Long) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Double) {
            Double result = (Double) l % (Double) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Float) {
            Float result = (Float) l % (Float) r;
            return new Terminal(ttype, result.toString());
        }

        throw new ArithmeticException("Invalid mod of '" + this + "' and '" + other + "'");
    }

    public Terminal gt(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Boolean result = c1 > c2;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Boolean result = i1 > i2;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Integer) {
            boolean result = (Integer) l > (Integer) r;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Long) {
            boolean result = (Long) l > (Long) r;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Double) {
            boolean result = (Double) l > (Double) r;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Float) {
            boolean result = (Float) l > (Float) r;
            return new Terminal("boolean", result ? "true" : "false");
        }

        throw new ArithmeticException("Invalid logic operation between '" + this + "' and '" + other + "'");
    }

    public Terminal lt(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Boolean result = c1 < c2;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Boolean result = i1 < i2;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Integer) {
            boolean result = (Integer) l < (Integer) r;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Long) {
            boolean result = (Long) l < (Long) r;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Double) {
            boolean result = (Double) l < (Double) r;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Float) {
            boolean result = (Float) l < (Float) r;
            return new Terminal("boolean", result ? "true" : "false");
        }

        throw new ArithmeticException("Invalid logic operation between '" + this + "' and '" + other + "'");
    }

    public Terminal ge(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Boolean result = c1 >= c2;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Boolean result = i1 >= i2;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Integer) {
            boolean result = (Integer) l >= (Integer) r;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Long) {
            boolean result = (Long) l >= (Long) r;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Double) {
            boolean result = (Double) l >= (Double) r;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Float) {
            boolean result = (Float) l >= (Float) r;
            return new Terminal("boolean", result ? "true" : "false");
        }

        throw new ArithmeticException("Invalid logic operation between '" + this + "' and '" + other + "'");
    }

    public Terminal le(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Boolean result = c1 <= c2;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Boolean result = i1 <= i2;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Integer) {
            boolean result = (Integer) l <= (Integer) r;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Long) {
            boolean result = (Long) l <= (Long) r;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Double) {
            boolean result = (Double) l <= (Double) r;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Float) {
            boolean result = (Float) l <= (Float) r;
            return new Terminal("boolean", result ? "true" : "false");
        }

        throw new ArithmeticException("Invalid logic operation between '" + this + "' and '" + other + "'");
    }

    public Terminal log_and(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Boolean) {
            Boolean result = (Boolean) l && (Boolean) r;
            return new Terminal("boolean", result ? "true" : "false");
        }

        throw new ArithmeticException("Invalid logic operation between '" + this + "' and '" + other + "'");
    }

    public Terminal log_or(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Boolean) {
            Boolean result = (Boolean) l || (Boolean) r;
            return new Terminal("boolean", result ? "true" : "false");
        }

        throw new ArithmeticException("Invalid logic operation between '" + this + "' and '" + other + "'");
    }

    public Terminal eq(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Boolean result = c1 == c2;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof String) {
            return new Terminal("boolean", l.equals(r) ? "true" : "false");
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Boolean result = i1 == i2;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Integer) {
            boolean result = l.equals(r);
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Long) {
            boolean result = l.equals(r);
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Double) {
            boolean result = l.equals(r);
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof Float) {
            boolean result = l.equals(r);
            return new Terminal("boolean", result ? "true" : "false");
        }

        throw new ArithmeticException("Invalid logic operation between '" + this + "' and '" + other + "'");
    }

    public Terminal ne(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Boolean result = c1 != c2;
            return new Terminal("boolean", result ? "true" : "false");
        } else if (l instanceof String) {
            return new Terminal("boolean", !l.equals(r) ? "true" : "false");
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Boolean result = i1 != i2;
            return new Terminal("boolean", !result ? "true" : "false");
        } else if (l instanceof Integer) {
            boolean result = l.equals(r);
            return new Terminal("boolean", !result ? "true" : "false");
        } else if (l instanceof Long) {
            boolean result = l.equals(r);
            return new Terminal("boolean", !result ? "true" : "false");
        } else if (l instanceof Double) {
            boolean result = l.equals(r);
            return new Terminal("boolean", !result ? "true" : "false");
        } else if (l instanceof Float) {
            boolean result = l.equals(r);
            return new Terminal("boolean", !result ? "true" : "false");
        }

        throw new ArithmeticException("Invalid logic operation between '" + this + "' and '" + other + "'");
    }

    public Terminal pow(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Double result = Math.pow(c1, c2);
            return new Terminal("double", result.toString());
        } else if (l instanceof Integer) {
            Double result = Math.pow((Integer) l, (Integer) r);
            return new Terminal("double", result.toString());
        } else if (l instanceof Long) {
            Double result = Math.pow((Long) l, (Long) r);
            return new Terminal("double", result.toString());
        } else if (l instanceof Double) {
            Double result = Math.pow((Double) l, (Double) r);
            return new Terminal("double", result.toString());
        } else if (l instanceof Float) {
            Double result = Math.pow((Float) l, (Float) r);
            return new Terminal("double", result.toString());
        }

        throw new ArithmeticException("Invalid power of '" + this + "' and '" + other + "'");
    }

    public Terminal and(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Integer result = c1 & c2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Integer result = i1 & i2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Integer) {
            Integer result = (Integer) l % (Integer) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Long) {
            Long result = (Long) l & (Long) r;
            return new Terminal(ttype, result.toString());
        }

        throw new ArithmeticException("Invalid and of '" + this + "' and '" + other + "'");
    }

    public Terminal or(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Integer result = c1 | c2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Integer result = i1 | i2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Integer) {
            Integer result = (Integer) l | (Integer) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Long) {
            Long result = (Long) l | (Long) r;
            return new Terminal(ttype, result.toString());
        }

        throw new ArithmeticException("Invalid or of '" + this + "' and '" + other + "'");
    }

    public Terminal xor(Terminal other) throws Exception {
        String ttype = getType(other);
        Terminal t1 = convert(this, ttype);
        Terminal t2 = convert(other, ttype);

        if (t1 == null || t2 == null)
            throw new RuntimeException("Incompatible type conversion of '" + this + "' and '" + other + "'");

        Object l = toObject(ttype, t1.value);
        Object r = toObject(ttype, t2.value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            char c2 = (Character) r;
            Integer result = c1 ^ c2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            int i2 = (Boolean) r ? 1 : 0;
            Integer result = i1 ^ i2;
            return new Terminal("int", result.toString());
        } else if (l instanceof Integer) {
            Integer result = (Integer) l ^ (Integer) r;
            return new Terminal(ttype, result.toString());
        } else if (l instanceof Long) {
            Long result = (Long) l ^ (Long) r;
            return new Terminal(ttype, result.toString());
        }

        throw new ArithmeticException("Invalid xor of '" + this + "' and '" + other + "'");
    }

    public Terminal not() throws Exception {

        Object l = toObject(type, value);

        if (l instanceof Character) {
            char c1 = (Character) l;
            Boolean result = (int) c1 == 0;
            return new Terminal("boolean", result.toString());
        } else if (l instanceof Boolean) {
            int i1 = (Boolean) l ? 1 : 0;
            Boolean result = i1 == 0;
            return new Terminal("boolean", result.toString());
        } else if (l instanceof Integer) {
            Boolean result = (Integer) l == 0;
            return new Terminal("boolean", result.toString());
        } else if (l instanceof Long) {
            Boolean result = (Long) l == 0;
            return new Terminal("boolean", result.toString());
        } else if (l instanceof Double) {
            Boolean result = (Double) l == 0;
            return new Terminal("boolean", result.toString());
        } else if (l instanceof Float) {
            Boolean result = (Float) l == 0;
            return new Terminal("boolean", result.toString());
        }

        throw new ArithmeticException("Invalid logical not operation of '" + this + "'");
    }

    @Override
    public String toString() {
        return "" + type + "(" + value + ")";
    }
}
