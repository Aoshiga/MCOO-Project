package Visitors.Verificateur;

import Visitors.PrettyPrint.PrettyPrintInterpreter;
import expression.Expression;
import fabrikexpression.ExprFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class VerificateurInterpreteurTest {
    public static ExprFactory f;
    public static Expression[] numbers;
    public static PrettyPrintInterpreter ppv;
    public static VerificateurInterpreteur vv;

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
    }

    @After
    public void printResults() {
        for (String s : vv.getErrorList()) {
            System.out.println(s);
        }
        vv.clear();

    }

    @Test
    public void MoinsExprArithEnsemble() throws Exception {
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression moinsEnsemble = f.makeNode("Moins", numbers[2], ens123);

        System.out.println(moinsEnsemble.accept(ppv));
        moinsEnsemble.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());

    }

    @Test
    public void MoinsExprArithExprArith() throws Exception {
        Expression moinsEntiers = f.makeNode("Moins", numbers[2], numbers[1]);
        System.out.println(moinsEntiers.accept(ppv));
        moinsEntiers.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());


    }

    @Test
    public void MoinsMoins() throws Exception {
        Expression moinsEntiers = f.makeNode("Moins", numbers[2], numbers[1]);
        Expression moinsEntiersMoins = f.makeNode("Moins", moinsEntiers, numbers[1]);

        System.out.println(moinsEntiersMoins.accept(ppv));
        moinsEntiersMoins.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());


    }

    @Test
    public void AppartientNonEnsemble() throws Exception {
        Expression appartient = f.makeNode("Appartient", numbers[2], numbers[2]);
        System.out.println(appartient.accept(ppv));
        appartient.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());


    }

    @Test
    public void AppartientEnsembleExprArith() throws Exception {
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression appartient = f.makeNode("Appartient", ens123, numbers[2]);
        System.out.println(appartient.accept(ppv));
        appartient.accept(vv);
        Assert.assertEquals(2, vv.getNumberErrors());

    }

    @Test
    public void AppartientExprArithEnsemble() throws Exception {

        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression appartient = f.makeNode("Appartient", numbers[2], ens123);
        System.out.println(appartient.accept(ppv));
        appartient.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());


    }

    @Test
    public void AppartientEnsembleEnsemble() throws Exception {
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression appartient = f.makeNode("Appartient", ens123, ens123);
        System.out.println(appartient.accept(ppv));
        appartient.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());

    }

    @Test
    public void AppartientMoinsEnsemble() throws Exception {
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression moinsEntiers = f.makeNode("Moins", numbers[2], numbers[1]);
        Expression appartient = f.makeNode("Appartient", moinsEntiers, ens123);
        System.out.println(appartient.accept(ppv));
        appartient.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());

    }
    @Test
    public void AppartientMoinsLitteral() throws Exception {
        Expression litteral = f.makeLeaf("Litteral", "x");
        Expression moinsEntiers = f.makeNode("Moins", numbers[2], numbers[1]);
        Expression appartient = f.makeNode("Appartient", moinsEntiers, litteral);
        System.out.println(appartient.accept(ppv));
        appartient.accept(vv);
        Assert.assertEquals(2, vv.getNumberErrors());

    }

    @Test
    public void DifferentEntiers() throws Exception {
        Expression differentEnsemble3 = f.makeNode("Different", numbers[2], numbers[2]);
        System.out.println(differentEnsemble3.accept(ppv));
        differentEnsemble3.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());

    }

    @Test
    public void DifferentEnsembles() throws Exception {

        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);

        Expression differentEnsemble2 = f.makeNode("Different", ens123, ens123);
        System.out.println(differentEnsemble2.accept(ppv));
        differentEnsemble2.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());

    }

    @Test
    public void DifferentEntierEnsemble() throws Exception {

        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression differentEnsemble1 = f.makeNode("Different", numbers[2], ens123);

        System.out.println(differentEnsemble1.accept(ppv));
        differentEnsemble1.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());

    }
    @Test
    public void DifferentEntierLitteral() throws Exception {
        Expression litteral = f.makeLeaf("Litteral", "x");

        Expression differentEnsemble1 = f.makeNode("Different", litteral,litteral);
        System.out.println(differentEnsemble1.accept(ppv));
        differentEnsemble1.accept(vv);
        Assert.assertEquals(2, vv.getNumberErrors());

    }

    @Test
    public void EgalEntiers() throws Exception {
        Expression differentEnsemble3 = f.makeNode("Egal", numbers[2], numbers[2]);
        System.out.println(differentEnsemble3.accept(ppv));
        differentEnsemble3.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());

    }

    @Test
    public void EgalEnsembles() throws Exception {

        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);

        Expression differentEnsemble2 = f.makeNode("Egal", ens123, ens123);
        System.out.println(differentEnsemble2.accept(ppv));
        differentEnsemble2.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());

    }

    @Test
    public void EgalEntierEnsemble() throws Exception {

        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression differentEnsemble1 = f.makeNode("Egal", numbers[2], ens123);

        System.out.println(differentEnsemble1.accept(ppv));
        differentEnsemble1.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());

    }
    @Test
    public void EgalEntierLitteral() throws Exception {
        Expression litteral = f.makeLeaf("Litteral", "x");

        Expression differentEnsemble1 = f.makeNode("Egal", litteral,litteral);
        System.out.println(differentEnsemble1.accept(ppv));
        differentEnsemble1.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());

    }

    @Test
    public void EtLogiqueNombreEnfentsPlusQue2() throws Exception{

            Expression etLogique1 = f.makeNode("EtLogique", numbers[1],numbers[1],numbers[1]);
            System.out.println(etLogique1.accept(ppv));
            etLogique1.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());

    }
    @Test
    public void EtLogiqueNombreEnfentsEgal1() throws Exception{
        Expression etLogique1 = f.makeNode("EtLogique", numbers[1]);
        System.out.println(etLogique1.accept(ppv));
        etLogique1.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());

    }
    @Test
    public void EnsembleEntiers() throws Exception{
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        System.out.println(ens123.accept(ppv));
        ens123.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());

    }
    @Test
    public void EnsembleLitteral() throws Exception{
        Expression x = f.makeLeaf("Litteral", "x");
        Expression z = f.makeLeaf("Litteral", "z");
        Expression Z = f.makeLeaf("Litteral", "Z");

        Expression ens123 = f.makeNode("EnsembleEnExtension",x,z,Z);
        System.out.println(ens123.accept(ppv));
        ens123.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());
    }

    @Test
    public void EnsembleWrongType() throws Exception{

        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);

        Expression x = f.makeLeaf("Litteral", "x");
        Expression z = f.makeLeaf("Litteral", "z");
        Expression Z = f.makeLeaf("Litteral", "Z");
        Expression ensembleTest = f.makeNode("EnsembleEnExtension",x,z,Z,numbers[1]);

        System.out.println(ens123.accept(ppv));
        ens123.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());
    }

    @Test
    public void ilExisteInclus() throws Exception{
        Expression Z = f.makeLeaf("Litteral", "Z");
        Expression Y = f.makeLeaf("Litteral", "Y");
        Expression ens456 = f.makeNode("EnsembleEnExtension", numbers[4], numbers[5], numbers[6]);
        Expression exemple2 = f.makeNode("EtLogique",
                f.makeNode("Egal", Y, ens456),
                f.makeNode("IlExiste", Z, f.makeNode("Inclus", Z, Y),f.makeNode("Egal",f.makeNode("Card", Z),numbers[3]))
        );
        System.out.println( exemple2.accept(ppv));
        exemple2.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());
    }

    @Test
    public void ilExisteAppartient() throws Exception{
        Expression x = f.makeLeaf("Litteral", "x");
        Expression X = f.makeLeaf("Litteral", "X");
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);

        Expression exemple3 = f.makeNode("EtLogique",
                f.makeNode("Egal", X, ens123),
                f.makeNode("IlExiste", x, f.makeNode("Appartient", x, X), f.makeNode("Egal",f.makeNode("Card", x),numbers[1]))
        );
        System.out.println( exemple3.accept(ppv));
        exemple3.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());

    }
    @Test
    public void ilExisteAppartientNonDefini() throws Exception{
        Expression x = f.makeLeaf("Litteral", "x");
        Expression z = f.makeLeaf("Litteral", "z");

        Expression X = f.makeLeaf("Litteral", "X");
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);

        Expression exemple3 = f.makeNode("EtLogique",
                f.makeNode("Egal", X, ens123),
                f.makeNode("IlExiste", x, f.makeNode("Appartient", z, X), f.makeNode("Egal",f.makeNode("Card", x),numbers[1]))
        );
        System.out.println( exemple3.accept(ppv));
        exemple3.accept(vv);
        Assert.assertEquals(3, vv.getNumberErrors());

    }


}
