/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package test.performance;

import org.codehaus.aspectwerkz.joinpoint.JoinPoint;

/**
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r </a>
 * @Aspect perClass
 */
public class PerClassPerformanceAspect {
    /**
     * @Around call(void test.performance.PerformanceTest.methodAdvisedMethodPerClass()) &&
     * within(test.performance.*)
     */
    public Object advice(final JoinPoint joinPoint) throws Throwable {
        return joinPoint.proceed();
    }

    /**
     * @Introduce within(test.performance.PerformanceTest)
     */
    public static class PerClassImpl implements PerClass {
        public void runPerClass() {
        }
    }
}