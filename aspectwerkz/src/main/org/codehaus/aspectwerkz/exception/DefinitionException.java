/**************************************************************************************
 * Copyright (c) The AspectWerkz Team. All rights reserved.                           *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD style license *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz.exception;

/**
 * Thrown when no aspectwerkz definition file or class could be found.
 *
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r</a>
 */
public class DefinitionException extends RuntimeException {

    /**
     * Sets a message.
     *
     * @param message the message
     */
    public DefinitionException(final String message) {
        super(message);
    }
}