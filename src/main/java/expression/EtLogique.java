package expression;

public class EtLogique extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + "EtLogique");

        for (Expression child:son) {
            child.afficher(prefixe+"    ");
        }
    }
}
