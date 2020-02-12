package expression;

public class Litteral extends Terminal {


    private String expr;

    public void set(String expr){
        this.expr = expr;
    }

    public String get(){
        return expr;
    }

}
