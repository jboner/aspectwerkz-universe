/* Generated By:JJTree: Do not edit this line. ASTCflowBelow.java */
package org.codehaus.aspectwerkz.expression.ast;

public class ASTCflowBelow extends SimpleNode {
    public ASTCflowBelow(int id) {
        super(id);
    }

    public ASTCflowBelow(ExpressionParser p, int id) {
        super(p, id);
    }

    public Object jjtAccept(ExpressionParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }
}