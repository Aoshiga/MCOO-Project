
import expression.*;
import fabrikexpression.*;

import java.io.*;
public class App {


public static void main(String [] args){
    ExprFactory f = ExprFactory.getInstance();
    try {
        Expression x = f.makeLeaf("Litteral", "x");
        Expression X = f.makeLeaf("Litteral", "X");

    Expression[] nb = new Expression[4];
    for (int i=0; i < 4; i++) {
            nb[i] = f.makeLeaf("Number", i);

    };

        Expression ens123 = f.makeNode("EnsembleEnExtension", nb[1], nb[2], nb[3]);
        Expression exemple1 = f.makeNode("EtLogique",
                f.makeNode("Egalite", X, ens123),
                f.makeNode("QuelqueSoit", x, f.makeNode("Appartient", x, X), f.makeNode("Sup", x, nb[0]))
        );
    }catch(Exception exc){
        System.out.println("exp");
    }
}


}