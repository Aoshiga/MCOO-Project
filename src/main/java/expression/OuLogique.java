package expression;

import java.util.ArrayList;

public class OuLogique extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " OuLogique");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
}
