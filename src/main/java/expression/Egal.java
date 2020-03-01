package expression;

import Visitors.PrettyPrintVisitor;

import java.util.ArrayList;

public class Egal extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Egalite");
        for (Expression child:son) {
            child.afficher(prefixe+ "       ");
        }
    }

    public Object accept(PrettyPrintVisitor ppv) {
        return ppv.visit(this);
    }
}
