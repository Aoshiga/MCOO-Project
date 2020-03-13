package Visitors.Evaluateur.Verificateur;

import Visitors.PrettyPrint.PrettyPrintInterpreter;
import Visitors.Verificateur.VerificateurInterpreteur;
import expression.Expression;
import fabrikexpression.ExprFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class EvaluateurInterpreteurTest {
    public static ExprFactory f;
    public static Expression[] numbers;
    public static PrettyPrintInterpreter ppv;
    public static VerificateurInterpreteur vv;
    public static EvaluateurVisiteur ev;

    @BeforeClass
    public static void initExprFactory() {
        f = ExprFactory.getInstance();
        numbers = new Expression[10];
        for (int i = 0; i < 10; i++) {
            try {
                numbers[i] = f.makeLeaf("Entier", i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ;
        ppv = new PrettyPrintInterpreter();
        vv = new VerificateurInterpreteur();
        ev = new EvaluateurInterpreteur();

    }

    @After
    public void printResults() {
        for (String s : vv.getErrorList()) {
            System.out.println(s);
        }
        vv.clear();
        if(ev.getVariables().size()>0)
            System.out.println(ev.getVariables());
        ev.clear();

    }

    @Test
    public void expectFalse() throws Exception {
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression X = f.makeLeaf("Litteral", "X");
        Expression Equals = f.makeNode("Egal", X, ens123);
        Expression et = f.makeNode("EtLogique", Equals ,f.makeLeaf("Booleen",false));
        System.out.println(et.accept(ppv));
        Boolean result = (Boolean)et.accept(ev);
        Assert.assertEquals(false, result);

    }
    @Test
    public void expectTrue() throws Exception {
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression X = f.makeLeaf("Litteral", "X");
        Expression Equals = f.makeNode("Egal", X, ens123);
        Expression et = f.makeNode("EtLogique", Equals ,f.makeLeaf("Booleen",true));
        System.out.println(et.accept(ppv));
        Boolean result = (Boolean)et.accept(ev);
        Assert.assertEquals(true, result);

    }
    @Test
    public void egalTwoNumbersTrue() throws Exception {

        Expression Equals = f.makeNode("Egal", numbers[0], numbers[0]);
        System.out.println(Equals.accept(ppv));
        Boolean result = (Boolean)Equals.accept(ev);
        Assert.assertEquals(true, result);

    }
    @Test
    public void egalTwoNumbersFalse() throws Exception {

        Expression Equals = f.makeNode("Egal", numbers[0], numbers[1]);
        System.out.println(Equals.accept(ppv));
        Boolean result = (Boolean)Equals.accept(ev);
        Assert.assertEquals(false, result);

    }
    @Test
    public void egalitteralBooleenTrue() throws Exception {
        Expression X = f.makeLeaf("Litteral", "X");
        Expression Equals = f.makeNode("Egal", X, f.makeLeaf("Booleen",true));
        Expression et = f.makeNode("EtLogique", Equals ,f.makeNode("Egal",X,f.makeLeaf("Booleen",true)));
        System.out.println(et.accept(ppv));
        Boolean result = (Boolean)et.accept(ev);
        Assert.assertEquals(true, result);

    }
    @Test
    public void egalitteralBooleenFalse() throws Exception {
        Expression X = f.makeLeaf("Litteral", "X");
        Expression Equals = f.makeNode("Egal", X, f.makeLeaf("Booleen",false));
        Expression et = f.makeNode("EtLogique", Equals ,f.makeNode("Egal",X,f.makeLeaf("Booleen",true)));
        System.out.println(et.accept(ppv));
        Boolean result = (Boolean)et.accept(ev);
        Assert.assertEquals(false, result);

    }
}