/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package examples.logging;

import org.codehaus.aspectwerkz.joinpoint.JoinPoint;
import org.codehaus.aspectwerkz.joinpoint.MemberSignature;
import org.codehaus.aspectwerkz.CrossCuttingInfo;

/**
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r</a>
 */
public abstract class AbstractLoggingAspect {

    private int m_level = 0;

    private CrossCuttingInfo m_info;
    public AbstractLoggingAspect(CrossCuttingInfo info) {
        m_info = info;
    }

    /**
     * @Around methodsToLog1 || methodsToLog2 || methodsToLog3
     */
    public Object logMethod(final JoinPoint joinPoint) throws Throwable {
        MemberSignature signature = (MemberSignature)joinPoint.getSignature();
        indent();
        System.out.println("--> " + joinPoint.getTargetClass().getName() + "::" + signature.getName() + " / " + m_info.getUuid());
        m_level++;
        final Object result = joinPoint.proceed();
        m_level--;
        indent();
        System.out.println("<-- " + joinPoint.getTargetClass().getName() + "::" + signature.getName());
        return result;
    }

    /**
     * @Before logSet
     * @Before logGet
     */
    public void logEntry(final JoinPoint joinPoint) throws Throwable {
        MemberSignature signature = (MemberSignature)joinPoint.getSignature();
        System.out.println("ENTER: " + joinPoint.getTargetClass().getName() + "::" + signature.getName());
    }

    /**
     * @After logSet
     * @After logGet
     */
    public void logExit(final JoinPoint joinPoint) throws Throwable {
        MemberSignature signature = (MemberSignature)joinPoint.getSignature();
        System.out.println("EXIT: " + joinPoint.getTargetClass().getName() + "::" + signature.getName());
    }

    private void indent() {
        for (int i = 0; i < m_level; i++) {
            System.out.print("  ");
        }
    }
}
