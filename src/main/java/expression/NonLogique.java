package expression;

import Visitors.PrettyPrintVisitor;

public class NonLogique extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " NonLogique");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }

    public Object accept(PrettyPrintVisitor ppv) {
       return ppv.visit(this);
    }

}
