/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz;

import java.net.URL;
import java.io.InputStream;

/**
 * Methods to deal with the context class loader.
 * Fail-over is provided to the default class loader.
 *
 * @author <a href="mailto:vta@medios.fi">Tibor Varga</a>
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r</a>
 */
public class ContextClassLoader {

    /**
     * Loads a class from the context class loader or, if that fails, from the
     * default class loader.
     *
     * @param name is the name of the class to load.
     * @return a <code>Class</code> object.
     * @throws ClassNotFoundException if the class was not found.
     */
    public static Class loadClass(final String name) throws ClassNotFoundException {
        Class cls = null;
        try {
            cls = Thread.currentThread().getContextClassLoader().loadClass(name);
        }
        catch (Exception e) {
            cls = Class.forName(name);
        }
        return cls;
    }

    /**
     * Loads a resource from the context class loader or, if that fails, from the
     * default class loader.
     *
     * @param name is the name of the resource to load.
     * @return a <code>URL</code> object.
     */
    public static URL loadResource(final String name) {
        try {
            return Thread.currentThread().getContextClassLoader().getResource(name);
        }
        catch (Exception e) {
            return ClassLoader.class.getClassLoader().getResource(name);
        }
    }

    /**
     * Loads a resource from the context class loader or, if that fails, from the
     * default class loader, as stream
     *
     * @param name is the name of the resource to load.
     * @return a <code>InputStream</code> object.
     */
    public static InputStream getResourceAsStream(final String name) {
        InputStream stream = null;
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader != null) {
            stream = contextClassLoader.getResourceAsStream(name);
        }
        if (stream == null) {
            stream = ClassLoader.getSystemClassLoader().getResourceAsStream(name);
        }
        if (stream == null) {
            ClassLoader classLoader = ClassLoader.class.getClassLoader();
            if (classLoader != null) {
                stream = classLoader.getResourceAsStream(name);
            }
        }
        return stream;
    }

    /**
     * Returns the context class loader.
     *
     * @return the context class loader
     */
    public static ClassLoader getLoader() {
        return Thread.currentThread().getContextClassLoader();
    }
}
