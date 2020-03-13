package fabrikexpression;

import expression.Expression;
import org.junit.Test;

public class ExprFactoryTest {


    @Test(expected = NonExistentTypeException.class)
    public void makeLeafWrongClassName() throws Exception{
        ExprFactory f = ExprFactory.getInstance();
        Expression tmp = f.makeLeaf("dsd", 1);
    }
    @Test
    public void makeLeafCorrectClassName() throws Exception{
        ExprFactory f = ExprFactory.getInstance();
        Expression integer = f.makeLeaf("Entier", 1);
    }

    @Test(expected = IncorrectTypageException.class)
    public void makeLeafIncorrectClassName() throws Exception{
        ExprFactory f = ExprFactory.getInstance();
        Expression integer = f.makeLeaf("Litteral", 1);
    }

    @Test(expected = NonExistentTypeException.class)
    public void makeNodeWrongClassName() throws Exception{
        ExprFactory f = ExprFactory.getInstance();
        Expression tmp = f.makeLeaf("dsd", 1);
    }

    @Test
    public void makeNodeCorrectClassName() throws Exception{
        ExprFactory f = ExprFactory.getInstance();
        Expression integer = f.makeLeaf("Entier", 1);
    }
}
