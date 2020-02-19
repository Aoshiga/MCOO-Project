package expression;

import Visitors.PrettyPrintVisitor;

public class PourTout extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " PourTout");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }

    public Object accept(PrettyPrintVisitor ppv) {
        String [] symbols= {"∀" , "." ,"⇒"};
        int counter = 0;

        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Expression child:son) {
            sb.append(symbols[counter]);
            sb.append(child.accept(ppv));
            counter++;
        }
        sb.append(")");
        return sb.toString();
    }
}
