package expression;

public class Plus extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Plus");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
}
