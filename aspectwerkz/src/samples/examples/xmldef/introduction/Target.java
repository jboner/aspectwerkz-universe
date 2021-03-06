/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package examples.xmldef.introduction;

/**
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r</a>
 *
 * @aspectwerkz.introduction mixin
 */
public class Target {
    public static void main(String[] args) {
        Target target = new Target();
        System.out.println("The mixin says: " + ((Mixin)target).sayHello());
    }
}
