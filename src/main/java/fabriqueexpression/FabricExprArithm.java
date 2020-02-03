package fabriqueexpression;

import expression.NonTerminal;

import java.util.ArrayList;

public class FabricExprArithm {
    private FabricExprArithm() {}

    public ExprArith makeLeaf(...) throws  Exception {
        
    }

    public ExprArith makeNode(...) throws Exception {
        Class <?> c;
        NonTerminal e;
        c = Class.forName("expression." + type);
        e = (NonTerminal) c.getDeclaredConstructor().newInstance();
        ArrayList<ExprArith> childrenlist = new ArrayList<ExprArith>();
        for (ExrpArith child : children) {
            childrenlist.add(child);
        }

        e.set(childrenlist);
        return e;
    }

    private static FabricExprArithm instance;
}
