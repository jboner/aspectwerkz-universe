/* Generated By:JJTree: Do not edit this line. ASTAnd.java */
package org.codehaus.aspectwerkz.expression.ast;

public class ASTAnd extends SimpleNode {
    public ASTAnd(int id) {
        super(id);
    }

    public ASTAnd(ExpressionParser p, int id) {
        super(p, id);
    }

    public Object jjtAccept(ExpressionParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
