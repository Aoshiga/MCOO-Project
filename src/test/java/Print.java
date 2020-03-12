
import Visitors.PrettyPrint.PrettyPrintInterpreter;
import Visitors.Verificateur.VerificateurInterpreteur;
import expression.Expression;
import fabrikexpression.ExprFactory;
public class Print {


    public static void main(String [] args){
        ExprFactory f = ExprFactory.getInstance();
        PrettyPrintInterpreter ppv = new PrettyPrintInterpreter();
        VerificateurInterpreteur vv = new VerificateurInterpreteur();

        try {

            Expression[] numbers = new Expression[10];
            for (int i=0; i < 10; i++) {
                numbers[i] = f.makeLeaf("Entier", i);
            };

            //-------------Test second parameter--------------------//
            /*{
                Expression appartient = f.makeNode("Appartient", numbers[2], numbers[2]);
                System.out.println(appartient.accept(ppv));
                appartient.accept(vv);
                for (String s : vv.getErrorList()) {
                    System.out.println(s);
                }
                vv.clear();
            }

            {
                Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
                Expression appartient = f.makeNode("Appartient", ens123, numbers[2]);
                System.out.println(appartient.accept(ppv));
                appartient.accept(vv);
                for (String s : vv.getErrorList()) {
                    System.out.println(s);
                }
                vv.clear();
            }

            {
                Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
                Expression appartient = f.makeNode("Appartient", numbers[2], ens123);
                System.out.println(appartient.accept(ppv));
                appartient.accept(vv);
                for (String s : vv.getErrorList()) {
                    System.out.println(s);
                }

                vv.clear();
            }
                //Testing Ensemble en extension
            {
                Expression etLogique1 = f.makeNode("EtLogique", numbers[2], numbers[1]);
                Expression etLogique2 = f.makeNode("EtLogique", numbers[1], numbers[1]);
                Expression etLogique3= f.makeNode("EtLogique", numbers[3], numbers[1]);

                Expression ensLogique = f.makeNode("EnsembleEnExtension", etLogique1, etLogique2,etLogique3);

                Expression appartient = f.makeNode("Appartient", numbers[2], ensLogique);
                System.out.println(appartient.accept(ppv));
                appartient.accept(vv);
                for (String s : vv.getErrorList()) {
                    System.out.println(s);
                }
                vv.clear();
            }*/
        /*    {
                Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);

                Expression differentEnsemble1 = f.makeNode("Different", numbers[2], ens123);
                Expression differentEnsemble2 = f.makeNode("Different", ens123, ens123);
                Expression differentEnsemble3 = f.makeNode("Different", numbers[2], numbers[2]);

                System.out.println(differentEnsemble1.accept(ppv));
                differentEnsemble1.accept(vv);
                for (String s : vv.getErrorList()) {
                    System.out.println(s);
                }
                vv.clear();

                System.out.println(differentEnsemble2.accept(ppv));
                differentEnsemble2.accept(vv);
                for (String s : vv.getErrorList()) {
                    System.out.println(s);
                }
                vv.clear();
                System.out.println(differentEnsemble3.accept(ppv));
                differentEnsemble3.accept(vv);
                for (String s : vv.getErrorList()) {
                    System.out.println(s);
                }
                vv.clear();
            }*/
            {
                Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);

                Expression moinsEnsemble = f.makeNode("Moins", numbers[2], ens123);
                Expression moinsEntiers = f.makeNode("Moins", numbers[2], numbers[1]);
                Expression moinsEntiersMoins = f.makeNode("Moins", f.makeNode("Moins", numbers[2], numbers[1]), numbers[1]);


                System.out.println(moinsEnsemble.accept(ppv));
                moinsEnsemble.accept(vv);
                for (String s : vv.getErrorList()) {
                    System.out.println(s);
                }
                vv.clear();

                System.out.println(moinsEntiers.accept(ppv));
                moinsEntiers.accept(vv);
                for (String s : vv.getErrorList()) {
                    System.out.println(s);
                }
                vv.clear();
                System.out.println(moinsEntiersMoins.accept(ppv));
                moinsEntiersMoins.accept(vv);
                for (String s : vv.getErrorList()) {
                    System.out.println(s);
                }
                vv.clear();

            }

        }catch(Exception exc){
            exc.printStackTrace();

            System.out.println(exc);

        }
    }


}