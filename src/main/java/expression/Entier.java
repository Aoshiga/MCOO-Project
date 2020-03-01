package expression;

import Visitors.PrettyPrintVisitor;

public class Entier extends Terminal {

    private int expr;

   public void set(int expr){
        this.expr = expr;
    }

    public Integer get(){
        return expr;
    }

    public void afficher(String prefixe) {
        System.out.println(prefixe + "|___ Entier : " + expr);
    }

    public Object accept(PrettyPrintVisitor ppv) {
      return ppv.visit(this);
    }
}
