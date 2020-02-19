package expression;

import Visitors.PrettyPrintVisitor;

public class Superieur extends NonTerminal {
    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Supperieur");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
    public void accept(PrettyPrintVisitor ev) {
        boolean special= false;
        for (Expression child:son) {
            child.accept(ev);
            if(!special) {
                System.out.print(">");
                special =true;
            }
        }

    }
}
