package expression;

import Visitors.PrettyPrintVisitor;

public class Litteral extends Terminal {

    private String expr;

    public void set(String expr){
        this.expr = expr;
    }

    public String get(){
        return expr;
    }
    public void afficher(String prefixe) {
        System.out.println(prefixe + "|___ Litteral : " + expr);

    }

    public Object accept(PrettyPrintVisitor ppv) {
        return ppv.visit(this);
    }

}
