package expression;

public class PourTout extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " PourTout");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
}
