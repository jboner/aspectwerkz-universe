/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz.expression;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Expression Namespace. A namespace is usually defined by the name of the class defining the expression.
 *
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r</a>
 */
public class ExpressionNamespace {
    /**
     * Namespace container.
     */
    private static Map s_namespaces = new WeakHashMap();

    /**
     * Map with all the expressions in the namespace, [name:expression] pairs.
     */
    private Map m_expressions = new HashMap();

    /**
     * The namespace.
     */
    private String m_namespace;

    /**
     * Creates a new expression namespace.
     *
     * @param namespace
     */
    private ExpressionNamespace(final String namespace) {
        m_namespace = namespace;
    }

    /**
     * Returns the expression namespace for a specific namespace.
     *
     * @param namespace
     * @return the expression namespace
     */
    public static synchronized ExpressionNamespace getNamespace(final String namespace) {
        if (!s_namespaces.containsKey(namespace)) {
            s_namespaces.put(namespace, new ExpressionNamespace(namespace));
        }

        return (ExpressionNamespace)s_namespaces.get(namespace);
    }

    /**
     * Adds an expression info to the namespace.
     *
     * @param name           the name mapped to the expression
     * @param expressionInfo the expression info to add
     */
    public void addExpressionInfo(final String name, final ExpressionInfo expressionInfo) {
        m_expressions.put(name, expressionInfo);
    }

    /**
     * Returns the expression witha a specific name.
     *
     * @param name the name of the expression
     * @return the expression
     */
    public ExpressionVisitor getExpression(final String name) {
        return ((ExpressionInfo)m_expressions.get(name)).getExpression();
    }

    /**
     * Returns the cflow expression witha a specific name.
     *
     * @param name the name of the expression
     * @return the expression
     */
    public CflowExpressionVisitor getCflowExpression(final String name) {
        return ((ExpressionInfo)m_expressions.get(name)).getCflowExpression();
    }

    /**
     * Returns the advised class expression witha a specific name.
     *
     * @param name the name of the expression
     * @return the expression
     */
    public AdvisedClassFilterExpressionVisitor getAdvisedClassExpression(final String name) {
        return ((ExpressionInfo)m_expressions.get(name)).getAdvisedClassFilterExpression();
    }

    /**
     * Returns the advised cflow class expression witha a specific name.
     *
     * @param name the name of the expression
     * @return the expression
     */
    public AdvisedCflowClassFilterExpressionVisitor getAdvisedCflowClassExpression(final String name) {
        return ((ExpressionInfo)m_expressions.get(name)).getAdvisedCflowClassFilterExpression();
    }

    /**
     * Returns the name of the namespace.
     *
     * @return the name of the namespace
     */
    public String getName() {
        return m_namespace;
    }
}