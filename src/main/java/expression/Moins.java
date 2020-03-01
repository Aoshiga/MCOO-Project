package expression;

import Visitors.PrettyPrintVisitor;

public class Moins extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Moins");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }

    public Object accept(PrettyPrintVisitor ppv) {
        return ppv.visit(this);
    }
}
