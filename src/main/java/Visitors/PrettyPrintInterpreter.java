package Visitors;

import expression.*;

import java.util.Iterator;

public class PrettyPrintInterpreter implements PrettyPrintVisitor{

    public Object visit(Entier n) {
        return n.get();
    }

    public Object visit(Appartient n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" ∈ ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(Booleen n) {
        return  n.get().toString();
    }
    public Object visit(Card n) {
        StringBuilder sb = new StringBuilder();
        sb.append("card("+n.son.get(0).accept(this)+") = " + n.son.get(1).accept(this));

        return sb.toString();
    }
    public Object visit(Different n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" ≠ ");
                special =true;
            }
        }
        return sb.toString();
    }

    public Object visit(Moins n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" - ");
                special =true;
            }
        }
        return sb.toString();
    }

    public Object visit(OuLogique n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" ∨ ");
                special =true;
            }
        }
        return sb.toString();
    }

    public Object visit(Egal n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" = ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(EnsembleEnExtension n) {
        StringBuilder sb = new StringBuilder();
        Iterator<Expression> iterator = n.son.iterator();
        sb.append("{");
        while (iterator.hasNext()) {
            sb.append(iterator.next().accept(this));
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        sb.append("}");
        return sb.toString();
    }
    public Object visit(EtLogique n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" ∧ ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(IlExiste n) {
        String special []= {"∃ "," . "," ∧ "};
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        int index = 0;
        for (Expression child:n.son) {
            sb.append(special[index]);
            sb.append(child.accept(this));
            index+=1;
        }
        sb.append(")");
        return sb.toString();
    }
    public Object visit(Inclus n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" ⊂ ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(InclusEgal n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" ⊆ ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(Inferieur n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" < ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(InferieurEgal n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" ≤ ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(Litteral n) {
        return n.get();
    }
    public Object visit(NonLogique n) {
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append("not(");
            sb.append(child.accept(this));
            sb.append(")");
        }
        return sb.toString();
    }

    public Object visit(Plus n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" + ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(PourTout n) {
        String special []= {"∀ "," . "," ⇒ "};
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        int index = 0;
        for (Expression child:n.son) {
            sb.append(special[index]);
            sb.append(child.accept(this));
            index+=1;
        }
        sb.append(")");

        return sb.toString();
    }
    public Object visit(Superieur n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" > ");
                special =true;
            }
        }
        return sb.toString();
    }
    public Object visit(SuperieurEgal n) {
        boolean special= false;
        StringBuilder sb = new StringBuilder();
        for (Expression child:n.son) {
            sb.append(child.accept(this));
            if(!special) {
                sb.append(" ≥ ");
                special =true;
            }
        }
        return sb.toString();
    }

    public Object visit(Terminal n) {
        return n.get();
    }


}
