package expression;

import Visitors.Evaluateur.EvaluateurVisiteur;
import Visitors.PrettyPrint.PrettyPrintVisitor;
import Visitors.Verificateur.VerificateurVisiteur;

public class OuLogique extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " OuLogique");
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
    public Object accept(EvaluateurVisiteur ev) {
        return ev.visit(this);
    }

}
