package expression;

import Visitors.PrettyPrintVisitor;

public abstract class Terminal extends Expression {
    public abstract void afficher(String prefixe);
    public abstract Object accept(PrettyPrintVisitor ppv);
}
