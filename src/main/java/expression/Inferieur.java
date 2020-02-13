package expression;

public class Inferieur extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Inferieur");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
}
