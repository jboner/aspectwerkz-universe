/**************************************************************************************
 * Copyright (c) The AspectWerkz Team. All rights reserved.                           *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD style license *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz.definition2.attribute;

import attrib4j.Attribute;

/**
 * Attribute for the Introduction construct.
 *
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r</a>
 */
public class IntroductionAttribute implements Attribute {

    /**
     * The pointcut for the advice.
     */
    private String m_pointcut;

    /**
     * Create an AroundAdvice attribute.
     *
     * @param pointcut the pointcut for the advice
     */
    public IntroductionAttribute(final String pointcut) {
        m_pointcut = pointcut;
    }

    /**
     * Return the pointcut.
     *
     * @return the pointcut
     */
    public String getPointcut() {
        return m_pointcut;
    }
}