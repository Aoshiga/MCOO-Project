package expression;

import java.util.ArrayList;

public class Inclus extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Inclus");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
}
