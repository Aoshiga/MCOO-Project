package expression;

public class InferieurEgal extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " InferieurEgal");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
}
