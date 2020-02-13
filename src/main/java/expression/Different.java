package expression;

import java.util.ArrayList;

public class Different extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Different");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
}
