package semantic_analysis.table;

import semantic_analysis.GType;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ScopedSymbolTable {

    public String name;
    public int scope_level;
    public ScopedSymbolTable enclosing_scope;
    private LinkedHashMap<String, Symbol> symbols;
    private LinkedHashMap<FunctionKey, FunctionSymbol> functions;


    public ScopedSymbolTable(String scope_name, int scope_level, ScopedSymbolTable enclosing_scope) {
        this.scope_level = scope_level;
        this.name = scope_name;
        this.enclosing_scope = enclosing_scope;
        symbols = new LinkedHashMap<>();
        functions = new LinkedHashMap<>();
    }

    /**
     * Initializes built-ins symbols
     */
    public void init_builtins() {
        insert(new BuiltinTypeSymbol("boolean"));
        insert(new BuiltinTypeSymbol("int"));
        insert(new BuiltinTypeSymbol("long"));
        insert(new BuiltinTypeSymbol("char"));
        insert(new BuiltinTypeSymbol("string"));
        insert(new BuiltinTypeSymbol("float"));
        insert(new BuiltinTypeSymbol("double"));
        insert(new BuiltinTypeSymbol("void"));
    }

    /**
     * Inserts symbol into scope list
     *
     * @param symbol
     */
    public void insert(Symbol symbol) {
        if (symbol instanceof FunctionSymbol) {
            ArrayList<GType> types = new ArrayList<>();
            for (Symbol symbol1 : ((FunctionSymbol) symbol).params) {
                types.add(new GType(symbol1.type.name));
            }
            functions.put(new FunctionKey(symbol.name, types), (FunctionSymbol) symbol);
        }

        symbols.put(symbol.name, symbol);
    }

    /**
     * Looks up for symbol
     *
     * @param name               name of the symbol to look for.
     * @param current_scope_only tells whether to look in current scope only or recursively in its parent
     * @return if symbol is found returns instance of Symbol else false
     */
    public Symbol lookup(String name, boolean current_scope_only) {
        Symbol symbol = symbols.get(name);

        if (symbol != null)
            return symbol;

        if (current_scope_only)
            return null;

        if (enclosing_scope != null) {
            return enclosing_scope.lookup(name, false);
        } else
            return null;
    }

    public FunctionSymbol lookup(FunctionKey key, boolean current_scope_only) {
        FunctionSymbol symbol = functions.get(key);

        if (symbol != null)
            return symbol;

        if (current_scope_only)
            return null;

        if (enclosing_scope != null)
            return enclosing_scope.lookup(key, false);
        else
            return null;
    }

}
