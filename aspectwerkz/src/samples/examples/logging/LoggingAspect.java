/**************************************************************************************
 * Copyright (c) The AspectWerkz Team. All rights reserved.                           *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD style license *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package examples.logging;

import org.codehaus.aspectwerkz.Pointcut;

/**
 * @Aspect perInstance
 *
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r</a>
 */
public class LoggingAspect extends AbstractLoggingAspect {

    // ============ Pointcuts ============

    /**
     * @Execution * examples.logging.Target.toLog1(..)
     */
    Pointcut methodsToLog1;

    /**
     * @Execution * examples.logging.Target.toLog2(..)
     */
    Pointcut methodsToLog2;

    /**
     * @Execution * examples.logging.Target.toLog3(..)
     */
    Pointcut methodsToLog3;

    /**
     * @Get int examples.logging.Target.m_counter1
     */
    Pointcut logGet;

    /**
     * @Set int examples.logging.Target.m_counter2
     */
    Pointcut logSet;
}
