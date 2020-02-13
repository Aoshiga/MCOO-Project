package expression;

import java.util.ArrayList;

public class Egal extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Egalite");
        for (Expression child:son) {
            child.afficher(prefixe+ "       ");
        }
    }
}
