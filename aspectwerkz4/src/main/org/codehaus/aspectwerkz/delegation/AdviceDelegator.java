/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz.delegation;

import org.codehaus.aspectwerkz.definition.AdviceDefinition;

/**
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r </a>
 */
public interface AdviceDelegator {

    /**
     * @param advisable
     */
    void register(Advisable advisable);

    /**
     * @return
     */
    Object getAspect();

    /**
     * @return
     */
    AdviceDefinition getAdviceDefinition();
}
