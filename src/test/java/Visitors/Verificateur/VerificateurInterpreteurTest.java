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

    /**
     * @Test Appartient
     */
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
    public void AppartientLitteralIsEntier() throws Exception {
        Expression litteral = f.makeLeaf("Litteral", "x");
        Expression equal = f.makeNode("Egal", litteral, numbers[1]);

        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression appartient = f.makeNode("Appartient", litteral, ens123);

        Expression et = f.makeNode("EtLogique", equal, appartient);
        System.out.println(et.accept(ppv));
        et.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());

    }

    @Test
    public void AppartientLitteralIsEnsemble() throws Exception {
        Expression litteral = f.makeLeaf("Litteral", "x");
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression equal = f.makeNode("Egal", litteral, ens123);

        Expression appartient = f.makeNode("Appartient", litteral, ens123);
        Expression et = f.makeNode("EtLogique", equal, appartient);
        System.out.println(et.accept(ppv));
        et.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());

    }

    @Test
    public void AppartientLitteralNotDefined() throws Exception {
        Expression litteral = f.makeLeaf("Litteral", "x");
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression appartient = f.makeNode("Appartient", litteral, ens123);

        System.out.println(appartient.accept(ppv));
        appartient.accept(vv);
        Assert.assertEquals(2, vv.getNumberErrors());

    }

    /**
     * @Test Booleen
     */
    @Test
    public void Booleen() throws Exception {
        Expression bool = f.makeLeaf("Booleen", true);
        System.out.println(bool.accept(ppv));
        bool.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());

    }

    /**
     * @Test Card
     */
    @Test
    public void CardEntier() throws Exception {
        Expression entier = f.makeLeaf("Entier", 5);
        Expression card = f.makeNode("Card", entier);
        System.out.println(card.accept(ppv));
        card.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());
    }

    @Test
    public void CardEnsemble() throws Exception {
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression card = f.makeNode("Card", ens123);
        System.out.println(card.accept(ppv));
        card.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());
    }

    @Test
    public void CardLitteralDefined() throws Exception {
        Expression litteral = f.makeLeaf("Litteral", "x");
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression equal = f.makeNode("Egal", litteral, ens123);
        Expression card = f.makeNode("Card", litteral);
        Expression et = f.makeNode("EtLogique", equal, card);
        System.out.println(et.accept(ppv));
        et.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());
    }

    @Test
    public void CardLitteralNotDefined() throws Exception {
        Expression litteral = f.makeLeaf("Litteral", "x");
        Expression card = f.makeNode("Card", litteral);
        System.out.println(card.accept(ppv));
        card.accept(vv);
        Assert.assertEquals(2, vv.getNumberErrors());
    }

    /**
     * @Test Different
     */
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
        Expression differentEnsemble = f.makeNode("Different", numbers[2], ens123);

        System.out.println(differentEnsemble.accept(ppv));
        differentEnsemble.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());

    }

    @Test
    public void DifferentEntierLitteral() throws Exception {
        Expression litteral1 = f.makeLeaf("Litteral", "x");
        Expression litteral2 = f.makeLeaf("Litteral", "y");

        Expression differentEnsemble = f.makeNode("Different", litteral1,litteral2);
        System.out.println(differentEnsemble.accept(ppv));
        differentEnsemble.accept(vv);
        Assert.assertEquals(4, vv.getNumberErrors());

    }

    @Test
    public void DifferentEntierLitteralDefined() throws Exception {
        Expression litteral = f.makeLeaf("Litteral", "x");
        Expression equal = f.makeNode("Egal", litteral, numbers[1]);
        Expression differentEnsemble = f.makeNode("Different", litteral, numbers[2]);
        Expression et = f.makeNode("EtLogique", equal, differentEnsemble);

        System.out.println(et.accept(ppv));
        et.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());

    }

    /**
     * @Test Egal
     */
    @Test // Test la comparaison
    public void EgalEntiers() throws Exception {
        Expression egal = f.makeNode("Egal", numbers[2], numbers[2]);
        System.out.println(egal.accept(ppv));
        egal.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());

    }

    @Test // Test la comparaison
    public void EgalEnsembles() throws Exception {

        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);

        Expression egal = f.makeNode("Egal", ens123, ens123);
        System.out.println(egal.accept(ppv));
        egal.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());

    }

    @Test // Test la comparaison
    public void EgalEntierEnsemble() throws Exception {

        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        Expression egal = f.makeNode("Egal", numbers[2], ens123);

        System.out.println(egal.accept(ppv));
        egal.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());

    }

    @Test // Test l'affectation
    public void EgalEntierLitteral() throws Exception {
        Expression litteral = f.makeLeaf("Litteral", "x");

        Expression affectation = f.makeNode("Egal", litteral,litteral);
        System.out.println(affectation.accept(ppv));
        affectation.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());

    }

    @Test // Test l'affectation
    public void EgalLitteralLitteral() throws Exception {
        Expression litteral1 = f.makeLeaf("Litteral", "x");
        Expression litteral2 = f.makeLeaf("Litteral", "y");

        Expression affectation1 = f.makeNode("Egal", litteral1, numbers[2]);
        Expression affectation2 = f.makeNode("Egal", litteral2,litteral1);

        Expression et = f.makeNode("EtLogique", affectation1, affectation2);

        System.out.println(et.accept(ppv));
        et.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());

    }

    /**
     * @Test EnsembleEnExtension
     */
    @Test
    public void EnsembleEntiers() throws Exception{
        Expression ens123 = f.makeNode("EnsembleEnExtension", numbers[1], numbers[2], numbers[3]);
        System.out.println(ens123.accept(ppv));
        ens123.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());

    }

    @Test
    public void EnsembleLitteralNotDefine() throws Exception{
        Expression x = f.makeLeaf("Litteral", "x");
        Expression y = f.makeLeaf("Litteral", "y");
        Expression z = f.makeLeaf("Litteral", "z");

        Expression ens123 = f.makeNode("EnsembleEnExtension",x ,y ,z);
        System.out.println(ens123.accept(ppv));
        ens123.accept(vv);
        Assert.assertEquals(6, vv.getNumberErrors());
    }

    @Test
    public void EnsembleLitteralDefine() throws Exception{
        Expression x = f.makeLeaf("Litteral", "x");
        Expression y = f.makeLeaf("Litteral", "y");

        Expression egal1 = f.makeNode("Egal", x, numbers[1]);
        Expression egal2 = f.makeNode("Egal", y, numbers[2]);

        Expression et1 = f.makeNode("EtLogique", egal1, egal2);
        Expression ensembleTest = f.makeNode("EnsembleEnExtension", x, y);

        Expression et2 = f.makeNode("EtLogique", et1, ensembleTest);

        System.out.println(et2.accept(ppv));
        et2.accept(vv);
        Assert.assertEquals(0, vv.getNumberErrors());
    }

    @Test
    public void EnsembleEmpty() throws Exception{
        Expression ens = f.makeNode("EnsembleEnExtension");

        System.out.println(ens.accept(ppv));
        ens.accept(vv);
        Assert.assertEquals(1, vv.getNumberErrors());

    }

    /**
     * @Test EtLogique
     */
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

    /**
     * @Test IlExiste
     */
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
        Assert.assertEquals(4, vv.getNumberErrors());

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
        Expression litteral = f.makeLeaf("Litteral", "x");

        Expression moinsEntiers = f.makeNode("Moins", numbers[2], litteral);
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







}
