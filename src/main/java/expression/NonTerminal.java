package expression;

import Visitors.PrettyPrintVisitor;

import java.util.ArrayList;

public abstract class NonTerminal extends Expression {
    protected ArrayList<Expression> son = new ArrayList<Expression>();
    public abstract Object accept(PrettyPrintVisitor ppv);
    public void set(Expression expr){
        son.add(expr);
    }
    public void remove(Expression expr){
        son.remove(expr);
    }

}