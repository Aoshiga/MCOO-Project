package expression;

import java.util.ArrayList;

public class Card extends NonTerminal {

    public void afficher(String prefixe) {
            System.out.println((prefixe==""? "" : prefixe + "|___") + " Card");
            for (Expression child:son) {
                child.afficher(prefixe+"        ");
            }

    }
}
