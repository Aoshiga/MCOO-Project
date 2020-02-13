package expression;

public class IlExiste extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " IlExiste");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
}
