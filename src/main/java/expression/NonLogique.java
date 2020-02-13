package expression;

public class NonLogique extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " NonLogique");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
}
