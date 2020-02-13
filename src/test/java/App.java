
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
            nb[i] = f.makeLeaf("Entier", i);


    };


        Expression ens123 = f.makeNode("EnsembleEnExtension", nb[1], nb[2], nb[3]);

        Expression exemple1 = f.makeNode("EtLogique",
                f.makeNode("Egal", X, ens123),
                f.makeNode("PourTout", x, f.makeNode("Appartient", x, X), f.makeNode("Superieur", x, nb[0]))
        );
        exemple1.afficher("");
    }catch(Exception exc){
        exc.printStackTrace();

        System.out.println(exc);

        System.out.println("exp");
    }
}


}