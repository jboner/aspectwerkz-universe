/**************************************************************************************
 * Copyright (c) Jonas Bon�r, Alexandre Vasseur. All rights reserved.                 *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the LGPL license      *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz.annotation;

import org.codehaus.aspectwerkz.util.Strings;

/**
 * The aspect annotation proxy.
 * <br/>
 * Note: this untyped annotation is like @Aspect perXXX name=foo [name is optional etc]
 * ie perXX is sort of anonymous and name as well, but without defaullt, hence the setter.
 *
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r </a>
 */
public class AspectAnnotationProxy extends UntypedAnnotationProxy {
    private String m_deploymentModel = "perJVM";

    private String m_aspectName = null;

    public String deploymentModel() {
        return m_deploymentModel;
    }

    public String aspectName() {
        return m_aspectName;
    }

    public void setAspectName(String aspectName) {
        m_aspectName = aspectName;
        // update m_value for proper serialization
        m_value = "name=" + aspectName + " " + m_deploymentModel;
    }

    public void setValue(final String value) {
        String[] parts = Strings.splitString(value, " ");
        StringBuffer deploymentModel = new StringBuffer();
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            int equals = part.indexOf('=');
            if (equals > 0) {
                String name = part.substring(0, equals);
                String param = part.substring(equals + 1, part.length());
                if (name.equalsIgnoreCase("name")) {
                    m_aspectName = param;
                }
            } else {
                deploymentModel.append(' ');
                deploymentModel.append(part);
            }
        }
        String tmp = deploymentModel.toString().trim();
        if ((tmp != null) && !tmp.equals("")) {
            m_deploymentModel = tmp.trim();
        }
    }
}