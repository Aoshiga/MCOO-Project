package expression;

public class Entier extends Terminal {
    private int expr;

   public void set(int expr){
        this.expr = expr;
    }

    public int get(){
        return expr;
    }

}
