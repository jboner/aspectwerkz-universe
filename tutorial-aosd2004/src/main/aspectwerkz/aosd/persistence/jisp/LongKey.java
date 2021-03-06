/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD-style license *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package aspectwerkz.aosd.persistence.jisp;

/**
 * Extends the com.coyotegulch.jisp.LongKey from the JISP distribution.
 * Provides the possibility to build the key based on the java.lang.Long type.
 *
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r</a>
 */
public class LongKey extends com.coyotegulch.jisp.LongKey {

    public LongKey() {
        super();
    }

    public LongKey(final long key) {
        super(key);
    }

    public LongKey(final Long key) {
        super(key.longValue());
    }
}


