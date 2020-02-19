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
        StringBuilder sb = new StringBuilder();
        sb.append("not(");
        for (Expression child:son) {
            sb.append(child.accept(ppv));
        }
        sb.append(")");
        return sb.toString();
    }

}
