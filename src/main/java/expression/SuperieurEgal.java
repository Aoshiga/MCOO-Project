package expression;

public class SuperieurEgal extends Superieur {
    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " SuperieurEgal");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
}
