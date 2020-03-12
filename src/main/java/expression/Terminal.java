package expression;

public abstract class Terminal extends Expression {
    public abstract void afficher(String prefixe);
    public abstract Object get();
}
