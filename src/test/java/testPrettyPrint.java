import expression.Expression;
import fabrikexpression.ExprFactory;

public class testPrettyPrint {


    public static void main(String [] args){

        ExprFactory f = ExprFactory.getInstance();
        try {
            Expression litteral = f.makeLeaf("Litteral", 1);
            Expression integer = f.makeLeaf("dsd", 1);

        }catch (Exception ex){
            System.out.println(ex.toString());

        }

    }

}
