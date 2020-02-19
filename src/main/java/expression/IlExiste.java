package expression;

import Visitors.PrettyPrintVisitor;

public class IlExiste extends NonTerminal {

    public void afficher(String prefixe) {
        System.out.println((prefixe==""? "" : prefixe + "|___") + " IlExiste");
        for (Expression child:son) {
            child.afficher(prefixe+"        ");
        }
    }
    public void accept(PrettyPrintVisitor ev) {
        String [] symbols= {"∃" , "." ,"∧"};
        int counter = 0;

        System.out.print("(");
        for (Expression child:son) {
            System.out.print(symbols[counter]);
            child.accept(ev);
            counter++;
        }
        System.out.print(")");
    }

}
