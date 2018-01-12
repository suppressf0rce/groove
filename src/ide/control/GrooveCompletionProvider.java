package ide.control;

import org.fife.ui.autocomplete.BasicCompletion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.autocomplete.ShorthandCompletion;

public class GrooveCompletionProvider extends DefaultCompletionProvider {

    public GrooveCompletionProvider() {
        this.addCompletion(new BasicCompletion(this, "void"));
        this.addCompletion(new BasicCompletion(this, "boolean"));
        this.addCompletion(new BasicCompletion(this, "int"));
        this.addCompletion(new BasicCompletion(this, "long"));
        this.addCompletion(new BasicCompletion(this, "float"));
        this.addCompletion(new BasicCompletion(this, "double"));
        this.addCompletion(new BasicCompletion(this, "char"));
        this.addCompletion(new BasicCompletion(this, "string"));
        this.addCompletion(new BasicCompletion(this, "package"));
        this.addCompletion(new BasicCompletion(this, "import"));
        this.addCompletion(new BasicCompletion(this, "let"));
        this.addCompletion(new BasicCompletion(this, "be"));
        this.addCompletion(new BasicCompletion(this, "function"));
        this.addCompletion(new BasicCompletion(this, "class"));
        this.addCompletion(new BasicCompletion(this, "interface"));
        this.addCompletion(new BasicCompletion(this, "enum"));
        this.addCompletion(new BasicCompletion(this, "extends"));
        this.addCompletion(new BasicCompletion(this, "implements"));
        this.addCompletion(new BasicCompletion(this, "true"));
        this.addCompletion(new BasicCompletion(this, "false"));
        this.addCompletion(new BasicCompletion(this, "private"));
        this.addCompletion(new BasicCompletion(this, "public"));
        this.addCompletion(new BasicCompletion(this, "protected"));
        this.addCompletion(new BasicCompletion(this, "abstract"));
        this.addCompletion(new BasicCompletion(this, "static"));
        this.addCompletion(new BasicCompletion(this, "final"));
        this.addCompletion(new BasicCompletion(this, "super"));
        this.addCompletion(new BasicCompletion(this, "break"));
        this.addCompletion(new BasicCompletion(this, "continue"));
        this.addCompletion(new BasicCompletion(this, "return"));
        this.addCompletion(new BasicCompletion(this, "if"));
        this.addCompletion(new BasicCompletion(this, "else"));
        this.addCompletion(new BasicCompletion(this, "switch"));
        this.addCompletion(new BasicCompletion(this, "case"));
        this.addCompletion(new BasicCompletion(this, "default"));
        this.addCompletion(new BasicCompletion(this, "while"));
        this.addCompletion(new BasicCompletion(this, "for"));
        this.addCompletion(new BasicCompletion(this, "do"));
        this.addCompletion(new BasicCompletion(this, "try"));
        this.addCompletion(new BasicCompletion(this, "catch"));
        this.addCompletion(new BasicCompletion(this, "throw"));
        this.addCompletion(new BasicCompletion(this, "throws"));
        this.addCompletion(new BasicCompletion(this, "new"));
        this.addCompletion(new BasicCompletion(this, "this"));
        this.addCompletion(new BasicCompletion(this, "end"));
        this.addCompletion(new BasicCompletion(this, "and"));
        this.addCompletion(new BasicCompletion(this, "or"));
        this.addCompletion(new BasicCompletion(this, "is"));
        this.addCompletion(new BasicCompletion(this, "not"));

        ShorthandCompletion main = new ShorthandCompletion(this, "main", "let function main() be void:\n\nend");
        main.setSummary("Groove mandatory main function:\n\n\nlet function main() be void:\n\nend");
        this.addCompletion(main);
    }

}
