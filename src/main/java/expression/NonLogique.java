package expression;

import Visitors.PrettyPrint.PrettyPrintVisitor;
import Visitors.Verificateur.VerificateurVisiteur;

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
    public Object accept(VerificateurVisiteur vv) {
        return vv.visit(this);
    }
}
