package Visitors.Evaluateur;

import Visitors.PrettyPrint.PrettyPrintInterpreter;
import Visitors.Verificateur.VerificateurInterpreteur;
import expression.Expression;
import fabrikexpression.ExprFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class EvaluateurInterpreteurTest {
    private static ExprFactory f;
    private static Expression[] numbers;
    private static PrettyPrintInterpreter ppv;
    private static VerificateurInterpreteur vv;
    private static EvaluateurVisiteur ev;

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
        System.out.println("Result : " + result);
        Assert.assertEquals(false, result);

    }
    @Test
    public void testAffectiationEstVrai() throws Exception {
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression X = f.makeLeaf("Litteral", "X");
        Expression Equals = f.makeNode("Egal", X, ens123);
        Expression et = f.makeNode("EtLogique", Equals ,f.makeLeaf("Booleen",true));
        System.out.println(et.accept(ppv));
        Boolean result = (Boolean)et.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(true, result);
    }
    @Test
    public void egalTwoNumbersTrue() throws Exception {

        Expression Equals = f.makeNode("Egal", numbers[0], numbers[0]);
        System.out.println(Equals.accept(ppv));
        Boolean result = (Boolean)Equals.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(true, result);

    }
    @Test
    public void egalTwoNumbersFalse() throws Exception {

        Expression Equals = f.makeNode("Egal", numbers[0], numbers[1]);
        System.out.println(Equals.accept(ppv));
        Boolean result = (Boolean)Equals.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(false, result);

    }
    @Test
    public void egalitteralBooleenTrue() throws Exception {
        Expression X = f.makeLeaf("Litteral", "X");
        Expression Equals = f.makeNode("Egal", X, f.makeLeaf("Booleen",true));
        Expression et = f.makeNode("EtLogique", Equals ,f.makeNode("Egal",X,f.makeLeaf("Booleen",true)));
        System.out.println(et.accept(ppv));
        Boolean result = (Boolean)et.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(true, result);

    }
    @Test
    public void egalitteralBooleenFalse() throws Exception {
        Expression X = f.makeLeaf("Litteral", "X");
        Expression Equals = f.makeNode("Egal", X, f.makeLeaf("Booleen",false));
        Expression et = f.makeNode("EtLogique", Equals ,f.makeNode("Egal",X,f.makeLeaf("Booleen",true)));
        System.out.println(et.accept(ppv));
        Boolean result = (Boolean)et.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(false, result);

    }
    @Test
    public void pourToutFalse() throws Exception {
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);

        Expression X = f.makeLeaf("Litteral", "X");
        Expression Equals = f.makeNode("Egal", X, ens123);
        Expression x = f.makeLeaf("Litteral","x");
        Expression et = f.makeNode("EtLogique", Equals ,f.makeNode("PourTout",x,f.makeNode("Appartient",x,X),f.makeNode("Inferieur",x,f.makeLeaf("Entier",3))));
        System.out.println(et.accept(ppv));
        Boolean result = (Boolean)et.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(false, result);

    }
    @Test
    public void pourToutTrue() throws Exception {
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);

        Expression X = f.makeLeaf("Litteral", "X");
        Expression Equals = f.makeNode("Egal", X, ens123);
        Expression x = f.makeLeaf("Litteral","x");
        Expression et = f.makeNode("EtLogique", Equals ,f.makeNode("PourTout",x,f.makeNode("Appartient",x,X),f.makeNode("Superieur",x,f.makeLeaf("Entier",0))));
        System.out.println(et.accept(ppv));
        Boolean result = (Boolean)et.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(true, result);

    }
    @Test
    public void plusEnsembleFalse() throws Exception {
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);

        Expression X = f.makeLeaf("Litteral", "X");
        Expression Equals = f.makeNode("Egal", X, ens123);
        Expression x = f.makeLeaf("Litteral","x");
        Expression et = f.makeNode("EtLogique", Equals ,f.makeNode("PourTout",x,f.makeNode("Appartient",x,X),f.makeNode("Inferieur",f.makeNode("Plus",x,numbers[1]),f.makeLeaf("Entier",4))));
        System.out.println(et.accept(ppv));
        Boolean result = (Boolean)et.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(false, result);

    }
    @Test
    public void moinsEnsembleTrue() throws Exception {
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);

        Expression X = f.makeLeaf("Litteral", "X");
        Expression Equals = f.makeNode("Egal", X, ens123);
        Expression x = f.makeLeaf("Litteral","x");
        Expression et = f.makeNode("EtLogique", Equals ,f.makeNode("PourTout",x,f.makeNode("Appartient",x,X),f.makeNode("Inferieur",f.makeNode("Moins",x,numbers[1]),f.makeLeaf("Entier",3))));
        System.out.println(et.accept(ppv));
        Boolean result = (Boolean)et.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(true, result);

    }

    @Test
    public void exempleSujet1() throws Exception {
        Expression X = f.makeLeaf("Litteral", "X");
        Expression x = f.makeLeaf("Litteral","x");

        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression exemple1 = f.makeNode("EtLogique",
                f.makeNode("Egal", X, ens123),
                f.makeNode("PourTout", x, f.makeNode("Appartient", x, X), f.makeNode("Superieur", x, numbers[0]))
        );
        System.out.println( exemple1.accept(ppv));
        Boolean result = (Boolean)exemple1.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(true, result);

    }
    @Test
    public void pourToutInclus() throws Exception {
        Expression Y = f.makeLeaf("Litteral","Y");
        Expression Z = f.makeLeaf("Litteral","Z");

        Expression ens456 = f.makeNode("EnsembleEnExtension", numbers[4], numbers[5], numbers[6]);
        Expression exemple2 = f.makeNode("EtLogique",
                f.makeNode("Egal", Y, ens456),
                f.makeNode("PourTout", Z, f.makeNode("Inclus", Z, Y),f.makeNode("Egal",f.makeNode("Card", Z),numbers[3]))
        );
        System.out.println( exemple2.accept(ppv));
        Boolean result = (Boolean)exemple2.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(false, result);

    }

    @Test
    public void pourToutInclus2() throws Exception {
        Expression Y = f.makeLeaf("Litteral","Y");
        Expression Z = f.makeLeaf("Litteral","Z");

        Expression ens456 = f.makeNode("EnsembleEnExtension", numbers[4], numbers[5], numbers[6]);
        Expression exemple2 = f.makeNode("EtLogique",
                f.makeNode("Egal", Y, ens456),
                f.makeNode("PourTout", Z, f.makeNode("Inclus", Z, Y),f.makeNode("Egal",Z,Y))
        );
        System.out.println( exemple2.accept(ppv));
        Boolean result = (Boolean)exemple2.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(false, result);

    }

    @Test
    public void pourToutInclusEgalTestEqual() throws Exception {
        Expression Y = f.makeLeaf("Litteral","Y");
        Expression Z = f.makeLeaf("Litteral","Z");

        Expression ens456 = f.makeNode("EnsembleEnExtension", numbers[4], numbers[5], numbers[6]);
        Expression exemple2 = f.makeNode("EtLogique",
                f.makeNode("Egal", Y, ens456),
                f.makeNode("PourTout", Z, f.makeNode("InclusEgal", Z, Y),f.makeNode("Egal",Z,Y))
        );
        System.out.println( exemple2.accept(ppv));
        Boolean result = (Boolean)exemple2.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(false, result);
    }
    @Test
    public void pourToutInclusEgalTestCard1() throws Exception {
        Expression Y = f.makeLeaf("Litteral","Y");
        Expression Z = f.makeLeaf("Litteral","Z");

        Expression ens456 = f.makeNode("EnsembleEnExtension", numbers[4], numbers[5], numbers[6]);
        Expression exemple2 = f.makeNode("EtLogique",
                f.makeNode("Egal", Y, ens456),
                f.makeNode("PourTout", Z, f.makeNode("InclusEgal", Z, Y),f.makeNode("Inferieur",f.makeNode("Card", Z),numbers[3]))
        );
        System.out.println( exemple2.accept(ppv));
        Boolean result = (Boolean)exemple2.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(false, result);
    }
    @Test
    public void pourToutInclusEgalTestCard2() throws Exception {
        Expression Y = f.makeLeaf("Litteral","Y");
        Expression Z = f.makeLeaf("Litteral","Z");

        Expression ens456 = f.makeNode("EnsembleEnExtension", numbers[4], numbers[5], numbers[6]);
        Expression exemple2 = f.makeNode("EtLogique",
                f.makeNode("Egal", Y, ens456),
                f.makeNode("PourTout", Z, f.makeNode("InclusEgal", Z, Y),f.makeNode("InferieurEgal",f.makeNode("Card", Z),numbers[3]))
        );
        System.out.println( exemple2.accept(ppv));
        Boolean result = (Boolean)exemple2.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(true, result);
    }
    @Test
    public void exempleSujet2() throws Exception {
        Expression Y = f.makeLeaf("Litteral","Y");
        Expression Z = f.makeLeaf("Litteral","Z");

        Expression ens456 = f.makeNode("EnsembleEnExtension", numbers[4], numbers[5], numbers[6]);
        Expression exemple2 = f.makeNode("EtLogique",
                f.makeNode("Egal", Y, ens456),
                f.makeNode("IlExiste", Z, f.makeNode("Inclus", Z, Y),f.makeNode("Egal",f.makeNode("Card", Z),numbers[3]))
        );
        System.out.println( exemple2.accept(ppv));
        Boolean result = (Boolean)exemple2.accept(ev);
        System.out.println("Result : " + result);
        Assert.assertEquals(false, result);

    }

}