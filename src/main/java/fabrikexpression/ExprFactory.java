package fabrikexpression;

import expression.*;
import expression.EnsembleEnExtension;

public class ExprFactory {
    private ExprFactory() {}

    public static ExprFactory getInstance() {
        if (instance == null) {
            instance = new ExprFactory();
        }
        return instance;
    }

    public Expression makeLeaf(String type, Object value) throws Exception {
        Class<?> c = Class.forName("expression." + type);
        Expression e = (Expression) c.getDeclaredConstructor().newInstance();
        if(e instanceof Litteral) {
            ((Litteral)e).set((String)value);
        } else if (e instanceof Entier) {
            ((Entier)e).set((Integer)value);
        }
        return e;
    }

    public Expression makeNode(String type, Expression... children) throws Exception {

        Class <?> c;
        NonTerminal e;
        c = Class.forName("expression." + type);
        e = (NonTerminal) c.getDeclaredConstructor().newInstance();
        for (Expression child : children) {
            e.set(child);
        }
        return e;
    }

    private static ExprFactory instance;

}
