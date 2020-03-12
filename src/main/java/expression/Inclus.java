package expression;

import Visitors.PrettyPrint.PrettyPrintVisitor;
import Visitors.Verificateur.VerificateurVisiteur;
import expression.Expression;
import expression.NonTerminal;

public class Inclus extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Inclus");
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
