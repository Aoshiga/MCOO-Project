package expression;

import Visitors.PrettyPrintVisitor;

public class Booleen extends Terminal {

    private boolean expr;

    public void set(boolean expr) {
        this.expr = expr;
    }

    public boolean get() {
        return expr;
    }
    public void afficher(String prefixe) {
        System.out.println(prefixe + " Boolean : " + (expr ? "TRUE" : "False")) ;

    }

    public Object accept(PrettyPrintVisitor ppv) {
        return expr;
    }
}