package expression;

import Visitors.PrettyPrintVisitor;

import java.util.ArrayList;

public class Different extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Different");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }

    public Object accept(PrettyPrintVisitor ppv) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:son) {
            sb.append(child.accept(ppv));
            if(!special) {
                sb.append("≠");
                special =true;
            }
        }
        return sb.toString();
    }
}
