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
        StringBuilder sb = new StringBuilder();
        Iterator<Expression> iterator = son.iterator();
        sb.append("{ ");
        while (iterator.hasNext()) {
            sb.append(iterator.next().accept(ppv));
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        sb.append(" }");
        return sb.toString();
    }

}