package expression;

import Visitors.PrettyPrintVisitor;

public class EtLogique extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + "EtLogique");

        for (Expression child:son) {
            child.afficher(prefixe+"    ");
        }
    }

    public Object accept(PrettyPrintVisitor ppv) {
        return ppv.visit(this);

    }
}
