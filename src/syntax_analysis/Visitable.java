package syntax_analysis;

public interface Visitable {

    public void accept(Visitor visitor);

}
