import Visitors.PrettyPrintInterpreter;
import Visitors.PrettyPrintVisitor;
import expression.Entier;
import expression.Expression;
import fabrikexpression.ExprFactory;

public class testPrettyPrint {


    public static void main(String [] args){

        ExprFactory f = ExprFactory.getInstance();
        try {
            Expression integer = f.makeLeaf("Entier", 1);
            Expression falseBoolean = f.makeLeaf("Booleen", false);
            PrettyPrintInterpreter ppv = new PrettyPrintInterpreter();

            Expression plus1 =  f.makeNode("Plus", falseBoolean, integer);
            System.out.println(plus1.accept(ppv));




        }catch (Exception ex){
            System.out.println(ex.toString());

        }

    }

}
