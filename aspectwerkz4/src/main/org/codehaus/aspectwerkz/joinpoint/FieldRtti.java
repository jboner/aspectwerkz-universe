/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz.joinpoint;

import java.lang.reflect.Field;

/**
 * Interface for the field RTTI (Runtime Type Information).
 *
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r </a>
 */
public interface FieldRtti extends MemberRtti {
    /**
     * Returns the field.
     *
     * @return the field
     */
    Field getField();

    /**
     * Returns the field type.
     *
     * @return the field type
     */
    Class getFieldType();

    /**
     * Returns the value of the field.
     *
     * @return the value of the field
     */
    Object getFieldValue();
}