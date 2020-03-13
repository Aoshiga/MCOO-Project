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
                if (String.valueOf(definedLitterals.get(expr)).equals(String.valueOf(LitteralType.EXPRARITH))){
                    types.add(String.valueOf(ExpressionTypes.ExprArith));
                }
            }
            types.add(String.valueOf(ExpressionTypes.Litteral));

        }else{
            types.add(type);
        }

        return types;
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


    public Object visit(Appartient n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Appartient prend deux parametres");
        }
        //Expected types for appartient
        ExpressionTypes expected[] = {ExpressionTypes.ExprArith,ExpressionTypes.Ensemble};
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++) {
            boolean variableDefined = false;
            ArrayList<String> childType = getExpressionType(n.son.get(i));

            if (childType.contains(ExpressionTypes.Litteral.toString())) {
                if (definedLitterals.containsKey(n.son.get(i))) {
                    variableDefined = true;
                } else {
                    this.errorList.add("Appartient : le parametre " + (i+1) + " n'est pas defini");
                }
            }

            if(!childType.contains(String.valueOf(expected[i])) ||
                    (!variableDefined && definedLitterals.containsKey(n.son.get(i).toString().equals(expected[i])))) {
                this.errorList.add("Appartient : le parametre " + (i + 1) + " n'est pas un " + expected[i]);
            }
        }
        return ExpressionTypes.ExprEnsembliste;
    }

    public Object visit(Booleen n) {
        return ExpressionTypes.ExprLogique ;
    }

    public Object visit(Card n) {
        int nbChildren = n.son.size();
        if (nbChildren != 1) {
            this.errorList.add("Card ne prend qu'un seul fils");
        }

        int elementsToVisit = Math.min(nbChildren, 1);
        if(elementsToVisit>0){
            ArrayList<String> childType = getExpressionType(n.son.get(0));
            if (childType.contains(ExpressionTypes.Litteral.toString()))
                if (!definedLitterals.containsKey(n.son.get(0))) {
                    this.errorList.add("Card : le parametre n'est pas defini");
                }
            if(!childType.contains(String.valueOf(ExpressionTypes.Ensemble)))
                this.errorList.add("Card : le parametre doit etre un "+ExpressionTypes.Ensemble);

        }

        return ExpressionTypes.ExprArith;
    }

    public Object visit(Different n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Different prend deux parametres");
        }
        String ExpectedEnsemble =  String.valueOf(ExpressionTypes.Ensemble);
        String ExpectedExprArith = String.valueOf(ExpressionTypes.ExprArith);

        String firstChildType = null;
        int elementsToVisit = Math.min(nbChildren, 2);
        boolean litteralDefined = true;
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> ActualChildType = getExpressionType(n.son.get(i));
            if( ActualChildType.contains(ExpressionTypes.Litteral.toString())) {
                if(!definedLitterals.containsKey(n.son.get(i))) {
                    this.errorList.add("Different : le litteral en parametre " + (i+1) +" n'est pas defini ");
                    litteralDefined = false;
                }
            }
            if(ActualChildType.contains(ExpectedEnsemble) && i==0) {
                firstChildType = ExpectedEnsemble;
            }
            else if(ActualChildType.contains(ExpectedExprArith) && i==0){
                firstChildType = ExpectedExprArith;
            }
            if(!ActualChildType.contains(ExpectedEnsemble) && !ActualChildType.contains(ExpectedExprArith)){
                this.errorList.add("Different : le parametre " + (i + 1) + " doit etre un ensemble ou une expression arithmetique");
            }
            if(!ActualChildType.contains(firstChildType) && i==1 && litteralDefined){
                this.errorList.add("Different : les deux parametres ne sont pas du meme type");
            }
        }
        if(firstChildType!=null && firstChildType.equals(ExpectedEnsemble))
            return ExpressionTypes.ComparaisonEnsembliste;

        return ExpressionTypes.ComparaisonArithmetique;
    }

    public Object visit(Egal n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Egal prend deux parametres");
        }
        int elementsToVisit = Math.min(nbChildren, 2);
        if (elementsToVisit>=2 ) {
            Expression lhsChild = n.son.get(0);
            Expression rhsChild = n.son.get(1);

            boolean isDefinition = false;
            ArrayList<String> lhsChildType = getExpressionType(lhsChild);
            ArrayList<String> rhsChildType = getExpressionType(rhsChild);

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
                this.errorList.add("Egal : le 1 parametre de egal doit etre un ensemble ou une expression arithmetique");

            if(!rhsChildType.contains(ExpressionTypes.ExprArith.toString())&&!rhsChildType.contains(ExpressionTypes.Ensemble.toString()))
                this.errorList.add("Egal : le 2 parametre de egal doit etre un ensemble ou une expression arithmetique");

            if(!lhsChildType.equals(rhsChildType) && !isDefinition)
                this.errorList.add("Egal : les deux parametres ne sont pas du meme type");
        }

        return ExpressionTypes.ComparaisonArithmetique;
    }

    public Object visit(EnsembleEnExtension n) {
        int nbChildren = n.son.size();
        if(nbChildren == 0) {
            this.errorList.add("EnsembleEnExtension : ensemble vide non autorisé");
        }

        for(int i=0 ;i<nbChildren;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));

            if(!childType.contains(String.valueOf(ExpressionTypes.ExprArith))) {
                this.errorList.add("EnsembleEnExtension : le parametre " + (i + 1) + " doit etre une " + ExpressionTypes.ExprArith);
            }

            if(childType.contains(String.valueOf(ExpressionTypes.Litteral)) && !definedLitterals.containsKey(n.son.get(i)) ) {
                this.errorList.add("EnsembleEnExtension : le litteral en parametre " + (i + 1) + " n'est pas défini");
            }
        }
        return ExpressionTypes.Ensemble;
    }

    public Object visit(Entier n) {
        return ExpressionTypes.ExprArith ;
    }

    public Object visit(EtLogique n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Etlogique: prend deux parametres");
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

            //Param 1 of ilExiste expression : Litteral
            ArrayList<String> firstChildType = getExpressionType(firstChild);

            if( !firstChildType.contains(String.valueOf(expected[0])) )
                this.errorList.add("IlExiste : le parametre " + (1) + " n'est pas un "+expected[0]);
            else {
                definedLitterals.put((Litteral)n.son.get(0),LitteralType.DEFAULT);
            }

            //Param 2 of ilExiste expression : ExprEnsembliste
            if(!firstChild.equals(((NonTerminal)secondChild).son.get(0)))
                this.errorList.add("IlExiste : le premier parametre de l'expression ensembliste doit être identique au premier parametre de l'expression il existe");
            else{
                if(((NonTerminal)secondChild).getClass().getName().equals("expression.Appartient"))
                    definedLitterals.put(((Litteral)n.son.get(0)),LitteralType.EXPRARITH);
                else
                    definedLitterals.put(((Litteral)n.son.get(0)),LitteralType.ENSEMBLE);
            }

            ArrayList<String> secondChildType = getExpressionType(secondChild);
            if(!secondChildType.contains(expected[1].toString()))
                this.errorList.add("IlExiste : le parametre " + (2) + " doit avoir le type " + expected[1]);

            //Param 3 of ilExiste expression : Expr
            ArrayList<String> thirdChildType = getExpressionType(thirdChild);
            if(!thirdChildType.contains(expected[2].toString()))
                this.errorList.add("IlExiste : le parametre " + (3) + " doit avoir le type " + expected[2]);

        }
        nestedLevel-=1;
        return ExpressionTypes.ExprLogique;
    }

    public Object visit(Inclus n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Inclus prend deux parametres");
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
            this.errorList.add("InclusEgal prend deux parametres");
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
            this.errorList.add("Inferieur : prend deux parametres");
        }
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if(!childType.contains(ExpressionTypes.ExprArith.toString())) {
                this.errorList.add("Inferieur : le parametre " + (i+1) + " doit etre une expressions arithmetique");
            }

            if(childType.contains(ExpressionTypes.Litteral.toString())
                    && !definedLitterals.containsKey(n.son.get(i))) {
                this.errorList.add("Inferieur : le parametre n'est pas défini");
            }
        }

        return ExpressionTypes.ExprLogique;
    }

    public Object visit(InferieurEgal n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("InferieurEgal: prend deux parametres");
        }
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if(!childType.contains(ExpressionTypes.ExprArith.toString())) {
                this.errorList.add("InferieurEgal:les fils de InferieurEgal doivent etre des ensembles");
            }
            if(childType.contains(ExpressionTypes.Litteral.toString())
                    && !definedLitterals.containsKey(n.son.get(i))) {
                this.errorList.add("InferieurEgal : le parametre n'est pas défini");
            }
        }

        return ExpressionTypes.ExprLogique;
    }

    public Object visit(Litteral n) {
        return ExpressionTypes.Litteral;
    }

    public Object visit(Moins n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Moins prend deux parametres");
        }
        //Expected types for appartient
        ExpressionTypes expected[] = {ExpressionTypes.ExprArith,ExpressionTypes.ExprArith};
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++) {
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if (!childType.contains(String.valueOf(expected[i]))) {
                this.errorList.add("Moins : le parametre " + (i + 1) + " n'est pas un " + expected[i]);
            }
            if (childType.contains(ExpressionTypes.Litteral.toString())
                    && !definedLitterals.containsKey(n.son.get(i))) {
                this.errorList.add("Moins : le parametre n'est pas défini");
            }
        }

        return ExpressionTypes.ExprArith;
    }

    public Object visit(NonLogique n) {
        int nbChildren = n.son.size();
        if (nbChildren != 1) {
            this.errorList.add("notLogique: prend deux parametres");
        }
        int elementsToVisit = Math.min(nbChildren, 1);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if( childType.contains(ExpressionTypes.Expr))
                this.errorList.add("notLogique:les fils de ouLogique doivent etre des ensembles");
        }

        return ExpressionTypes.ExprLogique;
    }

    public Object visit(OuLogique n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("ouLogique : prend deux prametres");
        }
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if( childType.contains(ExpressionTypes.Expr))
                this.errorList.add("ouLogique : les fils de ouLogique doivent etre des ensembles");
        }

        return ExpressionTypes.ExprLogique;
    }

    public Object visit(Plus n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Plus : prend deux parametres");
        }
        //Expected types for appartient
        ExpressionTypes expected[] = {ExpressionTypes.ExprArith,ExpressionTypes.ExprArith};
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if( !childType.contains(String.valueOf(expected[i])))
                this.errorList.add("Plus : le parametre " + (i+1) + " n'est pas un "+expected[i]);
            if (childType.contains(ExpressionTypes.Litteral.toString())
                    && !definedLitterals.containsKey(n.son.get(i))) {
                this.errorList.add("Plus : le parametre " + i+1 + " n'est pas défini");
            }
        }

        return ExpressionTypes.ExprArith;
    }

    public Object visit(PourTout n) {

        int nbChildren = n.son.size();
        if (nbChildren != 3) {
            this.errorList.add("Pour tout prend trois fils");
        }
        //Expected types for appartient
        ExpressionTypes expected[] = {ExpressionTypes.Litteral,ExpressionTypes.ExprEnsembliste,ExpressionTypes.Expr};
        int elementsToVisit = Math.min(nbChildren, 3);
        nestedLevel+=1;
        if(elementsToVisit==3){
            Expression firstChild = n.son.get(0);
            Expression secondChild = n.son.get(1);
            Expression thirdChild = n.son.get(2);

            //Param 1 of pourTout expression : Litteral
            ArrayList<String> firstChildType = getExpressionType(firstChild);

            if( !firstChildType.contains(String.valueOf(expected[0])) )
                this.errorList.add("PourTout : le parametre " + (1) + " n'est pas un "+expected[0]);
            else {
                definedLitterals.put((Litteral)n.son.get(0),LitteralType.DEFAULT);
            }

            //Param 2 of pourTout expression : ExprEnsembliste
            if(!firstChild.equals(((NonTerminal)secondChild).son.get(0)))
                this.errorList.add("PourTout : le premier parametre de l'expression ensembliste doit être identique au premier parametre de l'expression il existe");
            else{
                if(((NonTerminal)secondChild).getClass().getName().equals("expression.Appartient"))
                    definedLitterals.put(((Litteral)n.son.get(0)),LitteralType.EXPRARITH);
                else
                    definedLitterals.put(((Litteral)n.son.get(0)),LitteralType.ENSEMBLE);
            }

            ArrayList<String> secondChildType = getExpressionType(secondChild);
            if(!secondChildType.contains(expected[1].toString()))
                this.errorList.add("PourTout : le parametre " + (2) + " doit avoir le type " + expected[1]);

            //Param 3 of ilExiste expression : Expr
            ArrayList<String> thirdChildType = getExpressionType(thirdChild);
            if(!thirdChildType.contains(expected[2].toString()))
                this.errorList.add("PourTout : le parametre " + (3) + " doit avoir le type " + expected[2]);

        }
        nestedLevel-=1;
        return ExpressionTypes.ExprLogique;
    }

    public Object visit(Superieur n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Superieur: prend deux parametres");
        }
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if( !childType.contains(ExpressionTypes.ExprArith.toString()))
                this.errorList.add("Superieur : le parametre " + i+1 + " doit etre une expression arithmetique");
            if(childType.contains(ExpressionTypes.Litteral.toString())
                    && !definedLitterals.containsKey(n.son.get(i))) {
                this.errorList.add("Superieur : le parametre " + i+1 + " n'est pas défini");
            }
        }

        return ExpressionTypes.ExprLogique;
    }

    public Object visit(SuperieurEgal n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("SuperieurEgal: prend deux parametres");
        }
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            ArrayList<String> childType = getExpressionType(n.son.get(i));
            if(!childType.contains(ExpressionTypes.ExprArith.toString()))
                this.errorList.add("SuperieurEgal : le parametre " + i+1 + " doit etre une expression arithmetique");
            if(childType.contains(ExpressionTypes.Litteral.toString())
                    && !definedLitterals.containsKey(n.son.get(i))) {
                this.errorList.add("SuperieurEgal : le parametre " + i+1 + " n'est pas défini");
            }
        }
        return ExpressionTypes.ExprLogique;
    }

}
