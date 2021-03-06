/* Generated By:JJTree: Do not edit this line. ASTModifier.java */
package org.codehaus.aspectwerkz.expression.ast;

public class ASTModifier extends SimpleNode {
    private int m_modifier;

    private boolean m_not = false;

    public ASTModifier(int id) {
        super(id);
    }

    public ASTModifier(ExpressionParser p, int id) {
        super(p, id);
    }

    public Object jjtAccept(ExpressionParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }

    public void setNot() {
        m_not = true;
    }

    public boolean isNot() {
        return m_not;
    }

    public void setModifier(int modifier) {
        m_modifier = modifier;
    }

    public int getModifier() {
        return m_modifier;
    }
}