package expression;

import Visitors.Evaluateur.EvaluateurVisiteur;
import Visitors.PrettyPrint.PrettyPrintVisitor;
import Visitors.Verificateur.VerificateurVisiteur;

public class Litteral extends Terminal {

    private String expr;

    public void set(String expr){
        this.expr = expr;
    }

    public String get(){
        return expr;
    }
    public void afficher(String prefixe) {
        System.out.println(prefixe + "|___ Litteral : " + expr);

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
