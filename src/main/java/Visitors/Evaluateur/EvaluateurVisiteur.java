package Visitors.Evaluateur;

import expression.*;

import java.util.HashMap;

public interface EvaluateurVisiteur {
      HashMap<Litteral, Object> getVariables();
      void clear();
      Object visit(Entier input) ;
      Object visit(Appartient input);
      Object visit(Booleen input) ;
      Object visit(Card input) ;
      Object visit(Different input) ;
      Object visit(Moins input) ;
      Object visit(OuLogique input) ;
      Object visit(Egal input) ;
      Object visit(EnsembleEnExtension input) ;
      Object visit(EtLogique input) ;
      Object visit(IlExiste input) ;
      Object visit(Inclus input) ;
      Object visit(InclusEgal input) ;
      Object visit(Inferieur input) ;
      Object visit(InferieurEgal input) ;
      Object visit(Litteral input) ;
      Object visit(NonLogique input) ;
      Object visit(Plus input) ;
      Object visit(PourTout input) ;
      Object visit(Superieur input) ;
      Object visit(SuperieurEgal input) ;
}
