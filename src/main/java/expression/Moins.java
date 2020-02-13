package expression;

public class Moins extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Moins");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
}
