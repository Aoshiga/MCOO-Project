package expression;

import Visitors.PrettyPrintVisitor;

import java.util.ArrayList;

public class Egal extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " Egalite");
        for (Expression child:son) {
            child.afficher(prefixe+ "       ");
        }
    }

    public void accept(PrettyPrintVisitor ev) {
        boolean special= false;
        for (Expression child:son) {
            child.accept(ev);
            if(!special) {
                System.out.print("=");
                special =true;
            }
        }

    }
}
}
