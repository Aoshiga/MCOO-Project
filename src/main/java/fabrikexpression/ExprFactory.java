package fabrikexpression;

import expression.*;


public class ExprFactory {
    private ExprFactory() {}

    public static ExprFactory getInstance() {
        if (instance == null) {
            instance = new ExprFactory();
        }
        return instance;
    }

    public Expression makeLeaf(String type, Object value) throws Exception {
        try {
            Class<?> c = Class.forName("expression." + type);
            Expression e = (Expression) c.getDeclaredConstructor().newInstance();
            if (e instanceof Litteral) {
                if(!value.getClass().getName().equals("java.lang.String"))
                     throw new IncorrectTypageException(type,"String");
                ((Litteral) e).set((String) value);
            } else if (e instanceof Entier) {
                if(!value.getClass().getName().equals("java.lang.Integer"))
                    throw new IncorrectTypageException(type,"Integer");
                ((Entier) e).set((Integer) value);
            } else if (e instanceof Booleen) {
                if(!value.getClass().getName().equals("java.lang.Boolean"))
                    throw new IncorrectTypageException(type,"Boolean");
                ((Booleen) e).set((Boolean) value);
            }
            return e;
        }catch (ClassNotFoundException ex){
            throw new NonExistentTypeException(type);
        }
    }

    public Expression makeNode(String type, Expression... children) throws Exception {
        try {
            Class<?> c;
            NonTerminal e;
            c = Class.forName("expression." + type);
            e = (NonTerminal) c.getDeclaredConstructor().newInstance();
            for (Expression child : children) {
                e.set(child);
            }
            return e;
        }catch (ClassNotFoundException ex){
                throw new NonExistentTypeException(type);
        }
    }

    private static ExprFactory instance;

}
