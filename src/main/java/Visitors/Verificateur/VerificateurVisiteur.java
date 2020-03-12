package Visitors.Verificateur;

import expression.*;
import expression.Appartient;
import expression.Inclus;
import expression.InclusEgal;

import java.util.ArrayList;

public interface VerificateurVisiteur {
     public ArrayList<String> errorList = new ArrayList<String>();
     public ArrayList<String> getErrorList ();
     public int clear();

     public int getNumberErrors ();

     public Object visit(Entier n) ;
     public Object visit(Appartient n);
     public Object visit(Booleen n) ;
     public Object visit(Card n) ;
     public Object visit(Different n) ;
     public Object visit(Moins n) ;
     public Object visit(OuLogique n) ;
     public Object visit(Egal n) ;
     public Object visit(EnsembleEnExtension n) ;
     public Object visit(EtLogique n) ;
     public Object visit(IlExiste n) ;
     public Object visit(Inclus n) ;
     public Object visit(InclusEgal n) ;
     public Object visit(Inferieur n) ;
     public Object visit(InferieurEgal n) ;
     public Object visit(Litteral n) ;
     public Object visit(NonLogique n) ;
     public Object visit(Plus n) ;
     public Object visit(PourTout n) ;
     public Object visit(Superieur n) ;
     public Object visit(SuperieurEgal n) ;
}
