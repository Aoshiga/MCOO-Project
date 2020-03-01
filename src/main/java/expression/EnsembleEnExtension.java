package expression;
import Visitors.PrettyPrintVisitor;

import java.util.ArrayList;
import java.util.Iterator;


public class EnsembleEnExtension extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " EnsembleEnExtension");
        for (Expression child:son) {
            child.afficher(prefixe + "      ");
        }
    }

    public Object accept(PrettyPrintVisitor ppv) {
       return ppv.visit(this);
    }

}