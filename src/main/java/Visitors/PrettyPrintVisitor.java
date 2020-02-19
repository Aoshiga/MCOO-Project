package Visitors;

import expression.NonTerminal;
import expression.Terminal;

public interface PrettyPrintVisitor {
     String visit(Object n);
}
