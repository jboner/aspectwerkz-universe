/*
 * AspectWerkz - a dynamic, lightweight and high-performant AOP/AOSD framework for Java.
 * Copyright (C) 2002-2003  Jonas Bon�r. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package org.codehaus.aspectwerkz.definition.metadata;

import java.io.Serializable;

/**
 * Holds meta-data for a field. Used by the transformers.
 *
 * @author <a href="mailto:jboner@acm.org">Jonas Bon�r</a>
 * @version $Id: FieldMetaData.java,v 1.1.1.1 2003-05-11 15:13:57 jboner Exp $
 */
public class FieldMetaData implements Serializable {

    /**
     * The name of the method.
     */
    private String m_name;

    /**
     * The type.
     */
    private String m_type;

    /**
     * The modifiers.
     */
    private int m_modifiers;

    /**
     * Returns the name.
     *
     * @return the name
     */
    public String getName() {
        return m_name;
    }

    /**
     * Sets the name.
     *
     * @param name the name
     */
    public void setName(final String name) {
        m_name = name;
    }

    /**
     * Returns the type.
     *
     * @return the type
     */
    public String getType() {
        return m_type;
    }

    /**
     * Sets the type.
     *
     * @param type the type
     */
    public void setType(final String type) {
        m_type = type;
    }

    /**
     * Returns the modifiers.
     *
     * @return the modifiers
     */
    public int getModifiers() {
        return m_modifiers;
    }

    /**
     * Sets the modifiers.
     *
     * @param modifiers the modifiers
     */
    public void setModifiers(final int modifiers) {
        m_modifiers = modifiers;
    }
}