package expression;

import Visitors.PrettyPrint.PrettyPrintVisitor;
import Visitors.Verificateur.VerificateurVisiteur;

public class Booleen extends Terminal {
    public void afficher(String prefixe) {
        System.out.println(prefixe + "Boolean");
    }

    private boolean expr;

    public void set(boolean expr) {
        this.expr = expr;
    }

    public Object get() {
        return expr;
    }

    public Object accept(PrettyPrintVisitor ppv) {
        return ppv.visit(this);
    }
    public Object accept(VerificateurVisiteur vv) {
        return vv.visit(this);
    }
}