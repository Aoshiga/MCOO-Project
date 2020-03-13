package Visitors.Evaluateur.Verificateur;

import expression.*;

import java.util.ArrayList;
import java.util.HashMap;

public class EvaluateurInterpreteur implements EvaluateurVisiteur {

    private HashMap<Litteral,Object> variables = new HashMap<>();
    private HashMap<Litteral,Integer> levels = new HashMap<>();
    private  Integer nestedLevel = 0;
    public void clear(){
        variables.clear();
    }

    public HashMap<Litteral, Object> getVariables(){
        return variables;
    }
    @Override
    public Object visit(Entier input) {
        return input.get();
    }

    @Override
    public Object visit(Appartient input)
    {
        return  input.son.get(1).accept(this);
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
            levels.put((Litteral)lhs, nestedLevel);

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
        Expression firstChild = input.son.get(0);
        Expression secondChild = input.son.get(1);
        return (Integer)firstChild.accept(this) < (Integer)secondChild.accept(this);
    }

    @Override
    public Object visit(InferieurEgal input) {
        Expression firstChild = input.son.get(0);
        Expression secondChild = input.son.get(1);
        return (Integer)firstChild.accept(this) <= (Integer)secondChild.accept(this);
    }

    @Override
    public Object visit(Litteral input) {
        return variables.get(input);
    }

    @Override
    public Object visit(NonLogique input) {
        return (!(Boolean)input.accept(this));
    }

    @Override
    public Object visit(Plus input) {
    return true;
    }

    private Object getVariable(Expression input){
        if(variables.containsKey((Litteral)input)&&levels.get((Litteral)input)<nestedLevel){
            return input;
        }
        return null;
    }
    @Override
    public Object visit(PourTout input) {
        nestedLevel+=1;
        Expression firstChild = input.son.get(0);
        Expression secondChild = input.son.get(1);
        Expression thirdChild = input.son.get(2);
        boolean result = true;
        if(firstChild.getClass().getName().equals("expression.Litteral"))
            variables.put((Litteral) firstChild,"");
            levels.put((Litteral) firstChild,nestedLevel);

        if(secondChild.getClass().getName().equals("expression.Appartient")){
            if(getVariable(((NonTerminal)secondChild).son.get(1))!=null){
                for(Object obj : (ArrayList)secondChild.accept(this)){
                    variables.put((Litteral) firstChild,obj);
                    Boolean tmp = (Boolean) thirdChild.accept(this);
                    result &= tmp;
                    System.out.println(result);
                }
            }
        }
        System.out.println(levels);
        nestedLevel-=1;

        return result;
    }

    @Override
    public Object visit(Superieur input) {
        Expression firstChild = input.son.get(0);
        Expression secondChild = input.son.get(1);
        return (Integer)firstChild.accept(this) > (Integer)secondChild.accept(this);
    }

    @Override
    public Object visit(SuperieurEgal input) {
        Expression firstChild = input.son.get(0);
        Expression secondChild = input.son.get(1);
        return (Integer)firstChild.accept(this) >= (Integer)secondChild.accept(this);
    }
}
