/**************************************************************************************
 * Copyright (c) The AspectWerkz Team. All rights reserved.                           *
 * http://aspectwerkz.codehaus.org                                                    *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD style license *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package org.codehaus.aspectwerkz.joinpoint;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Iterator;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.codehaus.aspectwerkz.pointcut.MethodPointcut;
import org.codehaus.aspectwerkz.exception.WrappedRuntimeException;

/**
 * Matches well defined point of execution in the program where a
 * member method is executed.<br/>
 * Stores meta data from the join point.
 * I.e. a reference to original object A method, the parameters to
 * A the result from the original method invocation etc.<br/>
 * Handles the invocation of the advices added to the join point.
 *
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r</a>
 */
public class MemberMethodJoinPoint extends MethodJoinPoint {

    /**
     * The serial version uid for the class.
     */
    private static final long serialVersionUID = -1514240227634639181L;

    /**
     * A soft reference to the target instance.
     */
    protected SoftReference m_targetObjectReference;

    /**
     * Creates a new MemberMethodJoinPoint object.
     *
     * @param uuid the UUID for the AspectWerkz system to use
     * @param targetObject the target object
     * @param targetClass the target class
     * @param methodId the id of the original method
     * @param controllerClass the class name of the controller class to use
     */
    public MemberMethodJoinPoint(final String uuid,
                                 final Object targetObject,
                                 final String targetClassName,
                                 final int methodId,
                                 final String controllerClass) {

        super(uuid, methodId, controllerClass);
        if (targetObject == null) throw new IllegalArgumentException("target object can not be null");
        m_targetObjectReference = new SoftReference(targetObject);

        try {
            m_targetClass = targetObject.getClass().getClassLoader().loadClass(targetClassName);
        }
        catch (ClassNotFoundException e) {
            throw new WrappedRuntimeException(e);
        }

        m_originalMethod = m_system.getMethod(m_targetClass, m_methodId);
        m_originalMethod.setAccessible(true);

        createMetaData();

        // get all the pointcuts for this class
        List pointcuts = m_system.getMethodPointcuts(m_classMetaData, m_methodMetaData);

        // put the pointcuts in an array
        m_pointcuts = new MethodPointcut[pointcuts.size()];
        int i = 0;
        for (Iterator it = pointcuts.iterator(); it.hasNext(); i++) {
            m_pointcuts[i] = (MethodPointcut)it.next();
        }

        if (m_pointcuts.length == 0) {
            // we are at this point with no poincuts defined => the method has
            // a ThrowsJoinPoint defined at this method, since the method is
            // already advised, create a method pointcut for this method anyway
            handleThrowsPointcut();
        }

        // get the cflow pointcuts that affects this join point
        m_cflowPointcuts = m_system.getCFlowPointcuts(m_targetClass.getName(), m_methodMetaData);

        // check if the one of the pointcuts is marked as non-reentrant
        for (int j = 0; j < m_pointcuts.length; j++) {
            if (m_pointcuts[j].isNonReentrant()) {
                m_isNonReentrant = true;
                break;
            }
        }
    }

    /**
     * Returns the original object.
     *
     * @return the original object
     */
    public Object getTargetObject() {
        return m_targetObjectReference.get();
    }

    /**
     * Makes a deep copy of the join point.
     *
     * @return the clone of the join point
     */
    protected MethodJoinPoint deepCopy() {
        final MemberMethodJoinPoint clone = new MemberMethodJoinPoint(
                m_uuid,
                m_targetObjectReference.get(),
                m_targetClass.getName(),
                m_methodId,
                m_controller.getClass().getName()
        );
        clone.m_targetClass = m_targetClass;
        clone.m_originalMethod = m_originalMethod;
        clone.m_pointcuts = m_pointcuts;
        clone.m_parameters = m_parameters;
        clone.m_result = m_result;
        clone.m_methodMetaData = m_methodMetaData;
        clone.m_controller = m_controller.deepCopy();
        return clone;
    }

    /**
     * Overrides hashCode.
     *
     * @return the hash code
     */
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * The overridden equals method.
     *
     * @param o the other object
     * @return boolean
     */
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberMethodJoinPoint)) return false;
        final MemberMethodJoinPoint obj = (MemberMethodJoinPoint)o;
        return areEqualsOrBothNull(obj.m_originalMethod, this.m_originalMethod) &&
                areEqualsOrBothNull(obj.m_parameters, this.m_parameters) &&
                areEqualsOrBothNull(obj.m_targetObjectReference, this.m_targetObjectReference) &&
                areEqualsOrBothNull(obj.m_targetClass, this.m_targetClass) &&
                areEqualsOrBothNull(obj.m_pointcuts, this.m_pointcuts) &&
                areEqualsOrBothNull(obj.m_result, this.m_result) &&
                areEqualsOrBothNull(obj.m_classMetaData, this.m_classMetaData) &&
                areEqualsOrBothNull(obj.m_methodMetaData, this.m_methodMetaData) &&
                areEqualsOrBothNull(obj.m_controller, this.m_controller) &&
                (obj.m_methodId == this.m_methodId);
    }

    /**
     * Provides custom serialization.
     *
     * @param stream the object output stream that should write the serialized object
     * @throws java.lang.Exception in case of failure
     */
    private void writeObject(final ObjectOutputStream stream) throws Exception {
        ObjectOutputStream.PutField fields = stream.putFields();
        fields.put("m_targetObjectReference", m_targetObjectReference.get());
        stream.writeFields();
    }

    /**
     * Provides custom deserialization.
     *
     * @param stream the object input stream containing the serialized object
     * @throws java.lang.Exception in case of failure
     */
    private void readObject(final ObjectInputStream stream) throws Exception {
        ObjectInputStream.GetField fields = stream.readFields();
        m_targetObjectReference = new SoftReference(fields.get("m_targetObjectReference", null));
    }
}