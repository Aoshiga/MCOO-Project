package expression;

import Visitors.PrettyPrintVisitor;

import java.util.ArrayList;

public class Card extends NonTerminal {

    public void afficher(String prefixe) {
            System.out.println((prefixe==""? "" : prefixe + "|___") + " Card");
            for (Expression child:son) {
                child.afficher(prefixe+"        ");
            }

    }
    public Object accept(PrettyPrintVisitor ev) {
        StringBuilder sb = new StringBuilder();
        sb.append("card(");
        for (Expression child:son) {
            sb.append(child.accept(ev));
        }
        sb.append(")");
        return sb.toString();
    }

}
