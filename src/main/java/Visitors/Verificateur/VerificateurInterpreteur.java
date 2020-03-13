package Visitors.Verificateur;

import expression.*;

import java.util.ArrayList;
import java.util.HashMap;

public class VerificateurInterpreteur implements VerificateurVisiteur {
    private enum LitteralType{
        ENSEMBLE,
        EXPRARITH,
        DEFAULT,
    }
    private HashMap<Litteral,LitteralType> definedLitterals = new HashMap<>();

    private int nestedLevel = 0;
    private ArrayList<String> getExpressionType(Object expr){
        ArrayList<String> types = new ArrayList<String>();
        types.add("Expr");
        String type = String.valueOf(((Expression)expr).accept(this));
        if(type.equals(String.valueOf(ExpressionTypes.Litteral))) {
            if(definedLitterals.containsKey((Litteral)expr)) {
                if (String.valueOf(definedLitterals.get(expr)).equals(String.valueOf(LitteralType.ENSEMBLE))) {
                    types.add(String.valueOf(ExpressionTypes.Ensemble));
                }
                if (definedLitterals.get(expr).equals(String.valueOf(LitteralType.EXPRARITH))){
                    types.add(String.valueOf(ExpressionTypes.ExprArith));
                }
            }
            types.add(String.valueOf(ExpressionTypes.Litteral));

        }else{
            types.add(type);
        }

        return types;
    }
    private enum ExpressionTypes{
        ExprArith,
        Ensemble,
        Litteral,
        Entier,
        Comparaison,
        ComparaisonArithmetique,
        ComparaisonEnsembliste,
        ExprLogique,
        Expr,
        ExprEnsembliste
    }

    public ArrayList<String> getErrorList() {
        return errorList;
    }

    public int clear() {
        int nbErrors = errorList.size();
        errorList.clear();
        nestedLevel= 0;
        definedLitterals.clear();
        return nbErrors;
    }

    public int getNumberErrors() {
        return errorList.size();
    }

    public Object visit(Entier n) {
        return ExpressionTypes.ExprArith ;
    }

    public Object visit(Appartient n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Appartient prend deux fils");
        }
        //Expected types for appartient
        ExpressionTypes expected[] = {ExpressionTypes.ExprArith,ExpressionTypes.Ensemble};
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++) {
            boolean variableDefined = false;
            ArrayList<String> childType = getExpressionType(n.son.get(i));

            if (childType.contains(ExpressionTypes.Litteral.toString()))
                if (definedLitterals.containsKey(n.son.get(i))) {
                    variableDefined = true;
                } else {
                    this.errorList.add("Appartient : le parametre " + (i + 1) + " n'est pas defini");
                }

            if(!childType.contains(String.valueOf(expected[i])))
                this.errorList.add("Appartient : le parametre " + (i+1) + " n'est pas un " + expected[i]);
            else
                if(variableDefined){
                    if(definedLitterals.containsKey(n.son.get(i).toString().equals(expected[i])))
                        this.errorList.add("Appartient : le parametre " + (i+1) + " n'est pas un " + expected[i]);

                }
        }
        return ExpressionTypes.ExprEnsembliste;
    }
    public Object visit(Booleen n) {
        return ExpressionTypes.ExprLogique ;
    }

    public Object visit(Card n) {
        System.out.println("IN CARD");
        int nbChildren = n.son.size();
        if (nbChildren != 1) {
            this.errorList.add("Card prend un seul fils");
        }
        //Expected types for Card
        ExpressionTypes expected = ExpressionTypes.Ensemble;
        System.out.println(definedLitterals);

        int elementsToVisit = Math.min(nbChildren, 2);
        if(elementsToVisit>0){
            ArrayList<String> childType = getExpressionType(n.son.get(0));
            if(!childType.contains(String.valueOf(expected)))
                this.errorList.add("Card : le parametre doit etre un "+expected);

        }
        System.out.println("OUT CARD");

        return ExpressionTypes.ExprArith;
    }

    public Object visit(Different n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Different prend deux fils");
        }
        String ExpectedEnsemble =  String.valueOf(ExpressionTypes.Ensemble);
        String ExpectedExprArith = String.valueOf(ExpressionTypes.ExprArith);

        String firstChildType = null;
        int elementsToVisit = Math.min(nbChildren, 2);
        boolean litteralDefined = true;
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> ActualChildType = getExpressionType(n.son.get(i));
            if( ActualChildType.contains(ExpressionTypes.Litteral.toString()) && i==0) {
                System.out.println("HERE");
                if(!definedLitterals.containsKey(n.son.get(i))) {
                    this.errorList.add("Different : le litteral n'est pas defini ");
                    litteralDefined = false;
                }
            }
            if( ActualChildType.contains(ExpectedEnsemble) && i==0) {
                firstChildType = ExpectedEnsemble;
            }
            else if(ActualChildType.contains(ExpectedExprArith)&& i==0){
                firstChildType = ExpectedExprArith;
            }else if(i==0){
                this.errorList.add("Different : le parametre " + (i + 1) + " n'est pas un " + ExpectedEnsemble + " ou " +ExpectedExprArith);
            }
            if(!ActualChildType.contains(firstChildType) &&i==1 &&litteralDefined){
                this.errorList.add("Different : le parametre " + (i + 1) + " n'a pas le meme type que le premier parameter");
            }
        }
        if(firstChildType!=null && firstChildType.equals(ExpectedEnsemble))
            return ExpressionTypes.ComparaisonEnsembliste;

        return ExpressionTypes.ComparaisonArithmetique;
    }

    public Object visit(Moins n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Moins prend deux fils");
        }
        //Expected types for appartient
        ExpressionTypes expected[] = {ExpressionTypes.ExprArith,ExpressionTypes.ExprArith};
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if( !childType.contains(String.valueOf(expected[i])))
                this.errorList.add("Moins : le parametre " + (i+1) + " n'est pas un "+expected[i]);
        }

        return ExpressionTypes.ExprArith;
    }

    public Object visit(OuLogique n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("ouLogique: prend deux fils");
        }
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if( childType.contains(ExpressionTypes.Expr))
                this.errorList.add("ouLogique:les fils de ouLogique doivent etre des ensembles");
        }

        return ExpressionTypes.ExprLogique;
    }

    public Object visit(Egal n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Egal prend deux fils");
        }
        int elementsToVisit = Math.min(nbChildren, 2);
        if (elementsToVisit>=2 ) {
            Expression lhsChild = n.son.get(0);
            Expression rhsChild = n.son.get(1);
            boolean isDefinition = false;
            ArrayList<String> lhsChildType = getExpressionType(lhsChild);
            ArrayList<String> rhsChildType = getExpressionType(rhsChild);
            System.out.println(definedLitterals);
            if (lhsChildType.contains(String.valueOf(ExpressionTypes.Litteral))) {
                if(!definedLitterals.containsKey(lhsChild)) {
                    if (rhsChildType.contains(String.valueOf(ExpressionTypes.Ensemble))) {
                        definedLitterals.put((Litteral) n.son.get(0), LitteralType.ENSEMBLE);
                    } else if (rhsChildType.contains(String.valueOf(ExpressionTypes.ExprArith))) {
                        definedLitterals.put((Litteral) n.son.get(0), LitteralType.EXPRARITH);
                    }
                    isDefinition = true;
                }
            }else if(!lhsChildType.contains(ExpressionTypes.ExprArith.toString())&&!lhsChildType.contains(ExpressionTypes.Ensemble.toString()))
                this.errorList.add("Egal le 1 fils de egal doivent etre des ensembles ou un expression arithmetique");

            if(!rhsChildType.contains(ExpressionTypes.ExprArith.toString())&&!rhsChildType.contains(ExpressionTypes.Ensemble.toString()))
                this.errorList.add("Egal le 2 fils de egal doivent etre des ensembles ou un expression arithmetique");

                if(!lhsChild.getClass().getName().equals(rhsChild.getClass().getName()) && !isDefinition)
                    this.errorList.add("Egal le 2 fils de egal doivent avoir le meme type pour les comparer");
        }

        return ExpressionTypes.ComparaisonArithmetique;
    }


    public Object visit(EnsembleEnExtension n) {
        int nbChildren = n.son.size();

        for(int i=0 ;i<nbChildren;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if(childType.contains(ExpressionTypes.ExprArith))
                this.errorList.add("EnsembleEnExtension : le parametre " + (i+1) + " doit etre une "+ExpressionTypes.ExprArith);
        }
        return ExpressionTypes.Ensemble;
    }


    public Object visit(EtLogique n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Etlogique: prend deux fils");
        }
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if( childType.contains(ExpressionTypes.Expr))
                this.errorList.add("EtLogique:les fils de etLogique doivent etre des ensembles");
        }

        return ExpressionTypes.ExprLogique;
    }
    public Object visit(IlExiste n) {
        int nbChildren = n.son.size();
        if (nbChildren != 3) {
            this.errorList.add("Il existe prend trois fils");
        }
        //Expected types for appartient
        ExpressionTypes expected[] = {ExpressionTypes.Litteral,ExpressionTypes.ExprEnsembliste,ExpressionTypes.Expr};
        int elementsToVisit = Math.min(nbChildren, 3);
        nestedLevel+=1;
        if(elementsToVisit==3){
            Expression firstChild = n.son.get(0);
            Expression secondChild = n.son.get(1);
            Expression thirdChild = n.son.get(2);

            ArrayList<String> firstChildType = getExpressionType(firstChild);

            if( !firstChildType.contains(String.valueOf(expected[0])) )
                this.errorList.add("IlExiste : le parametre " + (1) + " n'est pas un "+expected[0]);
            else {
                definedLitterals.put((Litteral)n.son.get(0),LitteralType.DEFAULT);
            }

            System.out.println(firstChildType);
            if(!firstChild.equals(((NonTerminal)secondChild).son.get(0)))
                this.errorList.add("IlExiste : le parametre n'est pas le meme que la premier expression");
            else{
                if(((NonTerminal)secondChild).getClass().getName().equals("expression.Appartient"))
                    definedLitterals.put(((Litteral)n.son.get(0)),LitteralType.EXPRARITH);
                else
                    definedLitterals.put(((Litteral)n.son.get(0)),LitteralType.ENSEMBLE);
            }
            System.out.println("Defined list after : " + definedLitterals);
            ArrayList<String> secondChildType = getExpressionType(secondChild);

            if(!secondChildType.contains(expected[1].toString()))
                this.errorList.add("IlExiste : le parametre " + (2) + " doit avoir le type " + expected[1]);
            else{

            }
            ArrayList<String> thirdChildType = getExpressionType(firstChild);

            if(!thirdChildType.contains(expected[2].toString()))
                this.errorList.add("IlExiste : le parametre " + (2) + " doit avoir le type " + expected[1]);

        }
        nestedLevel-=1;
        return ExpressionTypes.ExprLogique;
    }
    public Object visit(Inclus n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Inclus prend deux fils");
        }
        //Expected types for appartient
        ExpressionTypes expected[] = {ExpressionTypes.Ensemble,ExpressionTypes.Ensemble};
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++) {
            boolean variableDefined = false;
            ArrayList<String> childType = getExpressionType(n.son.get(i));

            if (childType.contains(ExpressionTypes.Litteral.toString()))
                if (definedLitterals.containsKey(n.son.get(i))) {
                    variableDefined = true;
                } else {
                    this.errorList.add("Inclus : le parametre " + (i + 1) + " n'est pas defini");
                }

            if(!childType.contains(String.valueOf(expected[i])))
                this.errorList.add("Inclus : le parametre " + (i+1) + " n'est pas un " + expected[i]);
            else
            if(variableDefined){
                if(definedLitterals.containsKey(n.son.get(i).toString().equals(expected[i])))
                    this.errorList.add("Inclus : le parametre " + (i+1) + " n'est pas un " + expected[i]);
            }
        }
        return ExpressionTypes.ExprEnsembliste;
    }
    public Object visit(InclusEgal n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("InclusEgal prend deux fils");
        }
        //Expected types for appartient
        ExpressionTypes expected[] = {ExpressionTypes.Ensemble,ExpressionTypes.Ensemble};
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++) {
            boolean variableDefined = false;
            ArrayList<String> childType = getExpressionType(n.son.get(i));

            if (childType.contains(ExpressionTypes.Litteral.toString()))
                if (definedLitterals.containsKey(n.son.get(i))) {
                    variableDefined = true;
                } else {
                    this.errorList.add("InclusEgal : le parametre " + (i + 1) + " n'est pas defini");
                }

            if(!childType.contains(String.valueOf(expected[i])))
                this.errorList.add("InclusEgal : le parametre " + (i+1) + " n'est pas un " + expected[i]);
            else
            if(variableDefined){
                if(definedLitterals.containsKey(n.son.get(i).toString().equals(expected[i])))
                    this.errorList.add("InclusEgal : le parametre " + (i+1) + " n'est pas un " + expected[i]);
            }
        }
        return ExpressionTypes.ExprEnsembliste;
    }
    public Object visit(Inferieur n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Inferieur: prend deux fils");
        }
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if( childType.contains(ExpressionTypes.ExprArith))
                this.errorList.add("Inferieur:les fils de Inferieur doivent etre des ensembles");
        }

        return ExpressionTypes.ExprLogique;
    }
    public Object visit(InferieurEgal n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("InferieurEgal: prend deux fils");
        }
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if( childType.contains(ExpressionTypes.ExprArith))
                this.errorList.add("InferieurEgal:les fils de InferieurEgal doivent etre des ensembles");
        }

        return ExpressionTypes.ExprLogique;
    }
    public Object visit(Litteral n) {
        return ExpressionTypes.Litteral;
    }
    public Object visit(NonLogique n) {
        int nbChildren = n.son.size();
        if (nbChildren != 1) {
            this.errorList.add("notLogique: prend deux fils");
        }
        int elementsToVisit = Math.min(nbChildren, 1);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if( childType.contains(ExpressionTypes.Expr))
                this.errorList.add("notLogique:les fils de ouLogique doivent etre des ensembles");
        }

        return ExpressionTypes.ExprLogique;
    }

    public Object visit(Plus n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Plus prend deux fils");
        }
        //Expected types for appartient
        ExpressionTypes expected[] = {ExpressionTypes.ExprArith,ExpressionTypes.ExprArith};
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if( !childType.contains(String.valueOf(expected[i])))
                this.errorList.add("Plus : le parametre " + (i+1) + " n'est pas un "+expected[i]);
        }

        return ExpressionTypes.ExprArith;
    }
    public Object visit(PourTout n) {
        String special []= {"∀ "," . "," ⇒ "};
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        int index = 0;
        for (Expression child:n.son) {
            sb.append(special[index]);
            sb.append(child.accept(this));
            index+=1;
        }
        sb.append(")");

        return sb.toString();
    }
    public Object visit(Superieur n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Superieur: prend deux fils");
        }
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if( childType.contains(ExpressionTypes.ExprArith))
                this.errorList.add("Superieur:les fils de superieur doivent etre des ensembles");
        }

        return ExpressionTypes.ExprLogique;
    }
    public Object visit(SuperieurEgal n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Superieur: prend deux fils");
        }
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if( childType.contains(ExpressionTypes.ExprArith))
                this.errorList.add("SuperieurEgal:les fils de superieurEgal doivent etre des ensembles");
        }
        return ExpressionTypes.ExprLogique;
    }

}
