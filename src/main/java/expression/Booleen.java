package expression;

import Visitors.PrettyPrintVisitor;

public class Booleen extends Terminal {
    public void afficher(String prefixe) {
        System.out.println(prefixe + "Boolean");
    }

    private boolean expr;

    public void set(boolean expr) {
        this.expr = expr;
    }

    public boolean get() {
        return expr;
    }

    public Object accept(PrettyPrintVisitor ppv) {
        return expr;
    }
}