package expression;

import java.util.ArrayList;

public class Appartient extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Appartient");
        for (Expression child:son) {
            System.out.print(prefixe+"|");
            child.afficher("        ");
        }
    }
}
