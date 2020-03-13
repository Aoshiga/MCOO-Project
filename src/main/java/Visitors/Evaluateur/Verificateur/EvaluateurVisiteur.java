package Visitors.Evaluateur.Verificateur;

import expression.*;

import java.util.HashMap;

public interface EvaluateurVisiteur {
     public HashMap<Litteral, Object> getVariables();
     public void clear();
          public Object visit(Entier input) ;
     public Object visit(Appartient input);
     public Object visit(Booleen input) ;
     public Object visit(Card input) ;
     public Object visit(Different input) ;
     public Object visit(Moins input) ;
     public Object visit(OuLogique input) ;
     public Object visit(Egal input) ;
     public Object visit(EnsembleEnExtension input) ;
     public Object visit(EtLogique input) ;
     public Object visit(IlExiste input) ;
     public Object visit(Inclus input) ;
     public Object visit(InclusEgal input) ;
     public Object visit(Inferieur input) ;
     public Object visit(InferieurEgal input) ;
     public Object visit(Litteral input) ;
     public Object visit(NonLogique input) ;
     public Object visit(Plus input) ;
     public Object visit(PourTout input) ;
     public Object visit(Superieur input) ;
     public Object visit(SuperieurEgal input) ;
}
