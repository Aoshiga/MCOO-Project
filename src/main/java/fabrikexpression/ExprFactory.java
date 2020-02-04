package fabrikexpression;

import expression.Entier;
import expression.Litteral;
import expression.NonTerminal;
import expression.Terminal;

import java.beans.Expression;

public class ExprFactory {
    private ExprFactory() {}

    public static ExprFactory getInstance() {
        if (instance == null) {
            instance = new ExprFactory();
        }
        return instance;
    }

    public Expression makeLeaf(String type, Object value) throws Exception {
        Class<?> c = Class.forName("expression" + type);
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
            e.add(child);
        }
        return e;
    }

    private static ExprFactory instance;

}
