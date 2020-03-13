
import Visitors.PrettyPrint.PrettyPrintInterpreter;
import Visitors.Verificateur.VerificateurInterpreteur;
import expression.*;
import fabrikexpression.*;

public class App {


public static void main(String [] args){
    ExprFactory f = ExprFactory.getInstance();
    PrettyPrintInterpreter ppv = new PrettyPrintInterpreter();
    VerificateurInterpreteur vv = new VerificateurInterpreteur();

    try {
        Expression x = f.makeLeaf("Litteral", "x");
        Expression X = f.makeLeaf("Litteral", "X");
        Expression Y = f.makeLeaf("Litteral", "Y");
        Expression Z = f.makeLeaf("Litteral", "Z");
        Expression[] numbers = new Expression[10];
        for (int i=0; i < 10; i++) {
            numbers[i] = f.makeLeaf("Entier", i);
        };

        //--------------------------------------------
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression exemple1 = f.makeNode("EtLogique",
                f.makeNode("Egal", X, ens123),
                f.makeNode("PourTout", x, f.makeNode("Appartient", x, X), f.makeNode("Superieur", x, numbers[0]))
        );
       // exemple1.afficher("");
        System.out.println( exemple1.accept(ppv));
        //--------------------------------------------
        Expression ens456 = f.makeNode("EnsembleEnExtension", numbers[4], numbers[5], numbers[6]);
        Expression exemple2 = f.makeNode("EtLogique",
                f.makeNode("Egal", Y, ens456),
                f.makeNode("IlExiste", Z, f.makeNode("Inclus", Z, Y),f.makeNode("Egal",f.makeNode("Card", Z),numbers[3]))
        );
        //exemple2.afficher("");
        System.out.println( exemple2.accept(ppv));
        //--------------------------------------------
        Expression exemple3 = f.makeNode("EtLogique",
                f.makeNode("Egal", X, ens123),
                f.makeNode("IlExiste", x, f.makeNode("Appartient", x, X), f.makeNode("Egal",f.makeNode("Card", x),numbers[1]))
        );
        //exemple3.afficher("");
        System.out.println( exemple3.accept(ppv));
        //--------------------------------------------
        Expression ensVide = f.makeNode("EnsembleEnExtension");

        Expression exemple4 = f.makeNode("Egal", X, f.makeNode("OuLogique",
                ensVide,ens123
        ));
        //exemple2.afficher("");
        System.out.println( exemple4.accept(ppv));
       exemple4.accept(vv);
       for(String s : vv.getErrorList()){
           System.out.println(s);
        }

    }catch(Exception exc){
        exc.printStackTrace();

        System.out.println(exc);

        System.out.println("exp");
    }
}


}