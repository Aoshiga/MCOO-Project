package Visitors.PrettyPrint;

import expression.*;
import expression.Appartient;
import expression.Inclus;
import expression.InclusEgal;

 public interface PrettyPrintVisitor {
      Object visit(Entier n) ;
      Object visit(Appartient n);
      Object visit(Booleen n) ;
      Object visit(Card n) ;
      Object visit(Different n) ;
      Object visit(Moins n) ;
      Object visit(OuLogique n) ;
      Object visit(Egal n) ;
      Object visit(EnsembleEnExtension n) ;
      Object visit(EtLogique n) ;
      Object visit(IlExiste n) ;
      Object visit(Inclus n) ;
      Object visit(InclusEgal n) ;
      Object visit(Inferieur n) ;
      Object visit(InferieurEgal n) ;
      Object visit(Litteral n) ;
      Object visit(NonLogique n) ;
      Object visit(Plus n) ;
      Object visit(PourTout n) ;
      Object visit(Superieur n) ;
      Object visit(SuperieurEgal n) ;
}
