/* Generated By:JJTree: Do not edit this line. ASTNot.java */
package org.codehaus.aspectwerkz.expression.ast;

public class ASTNot extends SimpleNode {
    public ASTNot(int id) {
        super(id);
    }

    public ASTNot(ExpressionParser p, int id) {
        super(p, id);
    }

    public Object jjtAccept(ExpressionParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}
