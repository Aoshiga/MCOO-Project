package expression;

public class Superieur extends NonTerminal {
    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Supperieur");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
}
