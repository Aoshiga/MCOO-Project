package expression;

import java.util.ArrayList;

public abstract class NonTerminal extends Expression {
    public ArrayList<Expression> son = new ArrayList<Expression>();
    public void set(Expression expr){
        son.add(expr);
    }
    public void remove(Expression expr){
        son.remove(expr);
    }

}