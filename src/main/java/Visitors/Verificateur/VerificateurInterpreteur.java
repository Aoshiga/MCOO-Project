package Visitors.Verificateur;

import expression.*;

import java.util.ArrayList;
import java.util.Arrays;

public class VerificateurInterpreteur implements VerificateurVisiteur {
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
    private ArrayList<ExpressionTypes> getTypeListFromClass(String className){
       // System.out.println("found : " +className);
        ArrayList<ExpressionTypes> types = new ArrayList<ExpressionTypes>();
        types.add(ExpressionTypes.Expr);
        switch(className){
            case "expression.Entier":
                types.addAll(Arrays.asList(ExpressionTypes.ExprArith,ExpressionTypes.Entier));
                break;
            case "expression.Litteral":
                types.addAll(Arrays.asList(ExpressionTypes.ExprArith,ExpressionTypes.Ensemble,ExpressionTypes.Litteral));
                break;
            case "expression.EnsembleEnExtension":
                types.addAll(Arrays.asList(ExpressionTypes.Ensemble));
                break;

        }
        return types;
    }
    public ArrayList<String> getErrorList() {
        return errorList;
    }

    @Override
    public int clear() {
        int nbErrors = errorList.size();
        errorList.clear();
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
        for(int i=0 ;i<elementsToVisit;i++){
            String childType = String.valueOf(n.son.get(i).accept(this));
            if(!childType.equals(String.valueOf(expected[i])))
                this.errorList.add("Appartient : le parametre " + (i+1) + " n'est pas un "+expected[i]);
        }
        return ExpressionTypes.ExprEnsembliste;
    }
    public Object visit(Booleen n) {
        return ExpressionTypes.ExprLogique ;
    }

    public Object visit(Card n) {
        int nbChildren = n.son.size();
        if (nbChildren != 1)
            this.errorList.add("Card prend un seul fils");
        //Expected types for Card
        ExpressionTypes expected[] = {ExpressionTypes.Ensemble};

        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            String childType = n.son.get(i).getClass().getName();
            if( !getTypeListFromClass(childType).contains(expected[i]))
                this.errorList.add("Card : le parametre " + (i+1) + " doit etre un "+expected[i]);
            n.son.get(i).accept(this);
        }
        return ExpressionTypes.ExprArith;
    }
    public Object visit(Different n) {
        int nbChildren = n.son.size();
        if (nbChildren != 2) {
            this.errorList.add("Different prend deux fils");
        }
        boolean isEnsemble = false;
        String ExpectedEnsemble = String.valueOf(ExpressionTypes.Ensemble);
        String ExpectedExprArith = String.valueOf(ExpressionTypes.ExprArith);
        //Expected types for diffrent
        int elementsToVisit = Math.min(nbChildren, 2);
        for(int i=0 ;i<elementsToVisit;i++){
            String ActualChildType = String.valueOf(n.son.get(i).accept(this));
            if( ActualChildType.equals(ExpectedEnsemble) && i==0) {
                isEnsemble = true;
            }
            else if(ActualChildType.equals(ExpectedExprArith)&& i==0){
                isEnsemble = false;
            }else if(! ActualChildType.equals(ExpectedEnsemble) && !ActualChildType.equals(ExpectedExprArith)){
                this.errorList.add("Different : le parametre " + (i + 1) + " n'est pas un " + ExpectedEnsemble + " ou " +ExpectedExprArith);
            }
            if(ActualChildType.equals(ExpectedEnsemble) && i==1 && !isEnsemble){
                    this.errorList.add("Different : le parametre " + (i + 1) + " n'a pas le meme type que le premier parameter");
            }else if(ActualChildType.equals(ExpectedExprArith) && i==1 && isEnsemble){
                    this.errorList.add("Different : le parametre " + (i + 1) + " n'a pas le meme type que le premier parameter");
            }
            n.son.get(i).accept(this);
        }
        if(isEnsemble)
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
            String childType = String.valueOf(n.son.get(i).accept(this));
            if( !childType.equals(String.valueOf(expected[i])))
                this.errorList.add("Moins : le parametre " + (i+1) + " n'est pas un "+expected[i]);
        }

        return ExpressionTypes.ExprArith;
    }

    public Object visit(OuLogique n) {
        /*boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" ∨ ");
                special =true;
            }
        }*/
        return "";
    }

    public Object visit(Egal n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" = ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(EnsembleEnExtension n) {
        int nbChildren = n.son.size();

        for(int i=0 ;i<nbChildren;i++){
            String childType = n.son.get(i).getClass().getName();
            if( !getTypeListFromClass(childType).contains(ExpressionTypes.ExprArith))
                this.errorList.add("EnsembleEnExtension : le parametre " + (i+1) + " doit etre une "+ExpressionTypes.ExprArith);
            n.son.get(i).accept(this);
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
            String childType = n.son.get(i).getClass().getName();
            if( !getTypeListFromClass(childType).contains(ExpressionTypes.Expr ))
                this.errorList.add("EtLogique:les fils de etLogique doivent etre des ensembles");
            n.son.get(i).accept(this);
        }

        return ExpressionTypes.ExprLogique;
    }
    public Object visit(IlExiste n) {
        String special []= {"∃ "," . "," ∧ "};
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
    public Object visit(Inclus n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" ⊂ ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(InclusEgal n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" ⊆ ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(Inferieur n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" < ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(InferieurEgal n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" ≤ ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(Litteral n) {
        return n.get();
    }
    public Object visit(NonLogique n) {
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append("not(");
            sb.append(child.accept(this));
            sb.append(")");
        }
        return sb.toString();
    }

    public Object visit(Plus n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" + ");
                special =true;
            }
        }
        return sb.toString();
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
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" > ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(SuperieurEgal n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" ≥ ");
                special =true;
            }
        }
        return sb.toString();
    }

}
