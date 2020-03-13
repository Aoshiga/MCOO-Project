package expression;

import Visitors.Evaluateur.EvaluateurVisiteur;
import Visitors.PrettyPrint.PrettyPrintVisitor;
import Visitors.Verificateur.VerificateurVisiteur;

public abstract class Expression {
    public abstract void afficher(String prefixe);
    public abstract Object accept(PrettyPrintVisitor ppv);
    public abstract Object accept(VerificateurVisiteur vv);
    public abstract Object accept(EvaluateurVisiteur ev);

}
