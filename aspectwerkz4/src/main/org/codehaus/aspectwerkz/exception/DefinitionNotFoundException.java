/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz.exception;

/**
 * Thrown when no aspectwerkz definition file or class could be found.
 *
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r </a>
 */
public class DefinitionNotFoundException extends RuntimeException {
    /**
     * Sets a message.
     *
     * @param message the message
     */
    public DefinitionNotFoundException(final String message) {
        super(message);
    }
}