/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz.joinpoint;


/**
 * Implements the join point concept, e.g. defines a well defined point in the program flow.
 *
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r</a>
 */
public interface JoinPoint
{
    static final String METHOD_EXECUTION = "METHOD_EXECUTION";
    static final String METHOD_CALL = "METHOD_CALL";
    static final String CONSTRUCTOR_EXECUTION = "CONSTRUCTOR_EXECUTION";
    static final String CONSTRUCTOR_CALL = "CONSTRUCTOR_CALL";
    static final String FIELD_SET = "FIELD_SET";
    static final String FIELD_GET = "FIELD_GET";
    static final String CATCH_CLAUSE = "HANDLER";
    static final String STATIC_INITALIZATION = "STATIC_INITALIZATION";

    /**
     * Walks through the pointcuts and invokes all its advices. When the last advice of the last pointcut has been
     * invoked, the original method is invoked. Is called recursively.
     *
     * @return the result from the next invocation
     * @throws Throwable
     */
    Object proceed() throws Throwable;

    /**
     * Returns the signature for the join point.
     *
     * @return the signature
     */
    Signature getSignature();

    /**
     * Returns the RTTI for the join point.
     *
     * @return the RTTI
     */
    Rtti getRtti();

    /**
     * Returns the target instance or 'this'. If the join point is executing in a static context it returns null.
     *
     * @return the target instance
     */
    Object getTargetInstance();

    /**
     * Sets the target instance.
     *
     * @TODO: this method is bad for the API, dangerous
     *
     * @param targetInstance the target instance
     */
    void setTargetInstance(Object targetInstance);

    /**
     * Returns the target class.
     *
     * @return the target class
     */
    Class getTargetClass();

    /**
     * Returns the join point type.
     *
     * @return the type
     */
    String getType();
}
