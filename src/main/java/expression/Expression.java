package expression;

import Visitors.PrettyPrintVisitor;

public abstract class Expression {
    public abstract void afficher(String prefixe);
    public abstract Object accept(PrettyPrintVisitor e);
}
