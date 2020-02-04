package expression;

public class Booleen extends Terminal {

    private boolean expr;

    public void set(boolean expr) {
        this.expr = expr;
    }

    public boolean get() {
        return expr;
    }

}