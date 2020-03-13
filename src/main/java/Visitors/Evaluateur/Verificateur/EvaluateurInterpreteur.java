package Visitors.Evaluateur.Verificateur;

import expression.*;

import java.util.ArrayList;
import java.util.HashMap;

public class EvaluateurInterpreteur implements EvaluateurVisiteur {

    private HashMap<Litteral,Object> variables = new HashMap<>();
    public void clear(){
        variables.clear();
    }

    public HashMap<Litteral,Object> getVariables(){
        return variables;
    }
    @Override
    public Object visit(Entier input) {
        return input.get();
    }

    @Override
    public Object visit(Appartient input)
    {
        return null;
    }

    @Override
    public Object visit(Booleen input) {
        return input.get();
    }

    @Override
    public Object visit(Card input) {
        return null;
    }

    @Override
    public Object visit(Different input) {
        return null;
    }

    @Override
    public Object visit(Moins input) {
        return null;
    }

    @Override
    public Object visit(OuLogique input) {
        return null;
    }

    @Override
    public Object visit(Egal input) {
        Expression lhs = input.son.get(0);
        Expression rhs = input.son.get(1);
        boolean isCompare = true;

        if(lhs.getClass().getName().equals("expression.Litteral")&&!variables.containsKey((Litteral)lhs)) {
            isCompare = false;
            variables.put((Litteral) lhs, rhs.accept(this));
            System.out.println("IS definition");
        }
        if(isCompare){

            Boolean result = lhs.accept(this)==rhs.accept(this);
            System.out.println("IS compare" + result);
            return lhs.accept(this)==rhs.accept(this);

        }
        return true;
    }

    @Override
    public Object visit(EnsembleEnExtension input) {

        ArrayList<Object> list = new ArrayList<Object>();
        for (Expression exp: input.son){
            list.add(exp.accept(this));
        }
        return  list;
    }

    @Override
    public Object visit(EtLogique input) {
        Expression lhs = input.son.get(0);
        Expression rhs = input.son.get(1);

        return ((Boolean)lhs.accept(this) && (Boolean)rhs.accept(this));
    }

    @Override
    public Object visit(IlExiste input) {
        return null;
    }

    @Override
    public Object visit(Inclus input) {
        return null;
    }

    @Override
    public Object visit(InclusEgal input) {
        return null;
    }

    @Override
    public Object visit(Inferieur input) {
        return null;
    }

    @Override
    public Object visit(InferieurEgal input) {
        return null;
    }

    @Override
    public Object visit(Litteral input) {
        return variables.get(input);
    }

    @Override
    public Object visit(NonLogique input) {
        return null;
    }

    @Override
    public Object visit(Plus input) {
    return true;
    }

    @Override
    public Object visit(PourTout input) {
        Expression firstChild = input.son.get(0);
        Expression secondChild = input.son.get(1);
        Expression thirdChild = input.son.get(1);

        if(secondChild.getClass().getName().equals("expression.Appartient")){

            if(variables.containsKey(((NonTerminal)secondChild).son.get(1))){
                System.out.println(variables.get(((NonTerminal)secondChild)));
            }
        }

        return true;
    }

    @Override
    public Object visit(Superieur input) {
        return null;
    }

    @Override
    public Object visit(SuperieurEgal input) {
        return null;
    }
}
