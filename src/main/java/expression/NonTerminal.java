package expression;

import java.util.ArrayList;

public abstract class NonTerminal extends Expression {
    protected ArrayList<Expression> son;

    public
    void add(Expression expr){}
    void remove(Expression expr){}

}