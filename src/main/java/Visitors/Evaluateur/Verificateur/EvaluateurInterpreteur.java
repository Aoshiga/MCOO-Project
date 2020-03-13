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
        levels.clear();
    }

    public HashMap<Litteral, Object> getVariables(){
        return variables;
    }
    @Override
    public Object visit(Entier input) {
        return input.get();
    }

    private Object getVariable(Expression input){
        if(variables.containsKey((Litteral)input)&&levels.get((Litteral)input)<nestedLevel){
            return input;
        }
        return null;
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
        Expression firstChild = input.son.get(0);
        return ((ArrayList)firstChild.accept(this)).size();
    }

    @Override
    public Object visit(Different input) {
        Expression lhs = input.son.get(0);
        Expression rhs = input.son.get(1);

        return (lhs.accept(this) != rhs.accept(this));
    }

    @Override
    public Object visit(Moins input) {
        Expression lhs = input.son.get(0);
        Expression rhs = input.son.get(1);

        return ((Integer)lhs.accept(this) - (Integer)rhs.accept(this));
    }

    @Override
    public Object visit(OuLogique input) {
        Expression lhs = input.son.get(0);
        Expression rhs = input.son.get(1);

        return ((Boolean)lhs.accept(this) || (Boolean)rhs.accept(this));
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
        }
        if(isCompare){

            Boolean result = lhs.accept(this)==rhs.accept(this);

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
                }
            }
        }else if(secondChild.getClass().getName().equals("expression.InclusEgal")){
            if(getVariable(((NonTerminal)secondChild).son.get(1))!=null) {
                //Test equals
                variables.put((Litteral) firstChild,secondChild.accept(this));
                boolean lessThanResult1 = (Boolean) thirdChild.accept(this);
                ArrayList<Integer> reducedArray = new ArrayList<>((ArrayList)secondChild.accept(this));
                if(reducedArray.size()>0)
                    reducedArray.remove(reducedArray.size()-1);
                //Test lessThan
                variables.put((Litteral) firstChild,reducedArray);
                boolean lessThanResut2 = (Boolean) thirdChild.accept(this);
                //both must be true
                result =lessThanResut2||lessThanResult1;
            }
        }else{
            if(getVariable(((NonTerminal)secondChild).son.get(1))!=null) {
                //Test lessThan
                variables.put((Litteral) firstChild, secondChild.accept(this));
                result = (Boolean) thirdChild.accept(this);
            }
        }
        nestedLevel-=1;

        return result;
    }

    @Override
    public Object visit(Inclus input) {
        Expression firstChild = input.son.get(0);
        Expression secondChild = input.son.get(1);
        ArrayList<Integer> reducedArray = new ArrayList<>((ArrayList)secondChild.accept(this));
        if(reducedArray.size()>0)
            reducedArray.remove(reducedArray.size()-1);

        return reducedArray;
    }

    @Override
    public Object visit(InclusEgal input) {
        Expression firstChild = input.son.get(0);
        Expression secondChild = input.son.get(1);
        return (ArrayList)secondChild.accept(this);
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
        Expression lhs = input.son.get(0);
        Expression rhs = input.son.get(1);

        return ((Integer)lhs.accept(this) + (Integer)rhs.accept(this));
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
                }
            }
        }else if(secondChild.getClass().getName().equals("expression.InclusEgal")){
            if(getVariable(((NonTerminal)secondChild).son.get(1))!=null) {
                //Test equals
                variables.put((Litteral) firstChild,secondChild.accept(this));
                boolean lessThanResult1 = (Boolean) thirdChild.accept(this);
                ArrayList<Integer> reducedArray = new ArrayList<>((ArrayList)secondChild.accept(this));
                if(reducedArray.size()>0)
                    reducedArray.remove(reducedArray.size()-1);
                //Test lessThan
                variables.put((Litteral) firstChild,reducedArray);
                boolean lessThanResut2 = (Boolean) thirdChild.accept(this);
                //both must be true
                result =lessThanResut2&&lessThanResult1;
            }
        }else{
            if(getVariable(((NonTerminal)secondChild).son.get(1))!=null) {
                //Test lessThan
                variables.put((Litteral) firstChild, secondChild.accept(this));
                result = (Boolean) thirdChild.accept(this);
            }
        }
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
