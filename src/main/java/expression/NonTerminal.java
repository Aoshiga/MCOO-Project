package expression;

import java.util.ArrayList;

abstract class NonTerminal extends Expression {
    protected ArrayList<Expression> son;

    public
    void add(Expression expr){}
    void remove(Expression expr){}

}