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
package org.codehaus.aspectwerkz.pointcut;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.jexl.JexlHelper;
import org.apache.commons.jexl.JexlContext;
import org.apache.commons.jexl.ExpressionFactory;
import org.apache.commons.jexl.Expression;

import org.codehaus.aspectwerkz.AspectWerkz;
import org.codehaus.aspectwerkz.exception.WrappedRuntimeException;
import org.codehaus.aspectwerkz.regexp.FieldPattern;
import org.codehaus.aspectwerkz.regexp.PointcutPatternTuple;
import org.codehaus.aspectwerkz.metadata.FieldMetaData;
import org.codehaus.aspectwerkz.advice.AdviceIndexTuple;
import org.codehaus.aspectwerkz.definition.PointcutDefinition;

/**
 * Implements the pointcut concept for field access.
 * Is an abstraction of a well defined point of execution in the program.<br/>
 * Could matches one or many points as long as they are well defined.<br/>
 * Stores the advices for this specific pointcut.
 *
 * @author <a href="mailto:jboner@codehaus.org">Jonas Bon�r</a>
 * @version $Id: FieldPointcut.java,v 1.6 2003-06-30 15:55:25 jboner Exp $
 */
public class FieldPointcut implements Pointcut {

    /**
     * The expression for the pointcut.
     */
    protected final String m_expression;

    /**
     * The pointcut definitions referenced in the m_expression.
     * Mapped to the name of the pointcut definition.
     */
    protected final Map m_pointcutDefs = new HashMap();

    /**
     * The UUID for the AspectWerkz system.
     */
    protected final String m_uuid;

    /**
     * Holds the names of the pre advices.
     */
    protected String[] m_preNames = new String[0];

    /**
     * Holds the names of the post advices.
     */
    protected String[] m_postNames = new String[0];

    /**
     * Holds the indexes of the pre advices.
     */
    protected int[] m_preIndexes = new int[0];

    /**
     * Holds the indexes of the post advices.
     */
    protected int[] m_postIndexes = new int[0];

    /**
     * Creates a new field pointcut.
     *
     * @param pattern the pattern of the pointcut
     */
    public FieldPointcut(final String pattern) {
        this(AspectWerkz.DEFAULT_SYSTEM, pattern);
    }

    /**
     * Creates a new field pointcut.
     *
     * @param uuid the UUID for the AspectWerkz system
     * @param pattern the pattern for the pointcut
     */
    public FieldPointcut(final String uuid,
                         final String pattern) {
        if (uuid == null) throw new IllegalArgumentException("uuid can not be null");
        if (pattern == null || pattern.trim().length() == 0) throw new IllegalArgumentException("pattern of pointcut can not be null or an empty string");
        m_uuid = uuid;
        m_expression = pattern;
    }

    /**
     * Adds a new pointcut definition.
     *
     * @param pointcut the pointcut definition
     */
    public void addPointcutDef(final PointcutDefinition pointcut) {
        m_pointcutDefs.put(pointcut.getName(),
                new PointcutPatternTuple(
                        pointcut.getRegexpClassPattern(),
                        pointcut.getRegexpPattern()));
    }

    /**
     * Adds a pre advice to the pointcut.
     *
     * @param advice the name of the advice to add
     */
    public void addPreAdvice(final String advice) {
        if (advice == null || advice.trim().length() == 0) throw new IllegalArgumentException("name of advice to add can not be null or an empty string");
        synchronized (m_preNames) {
            synchronized (m_preIndexes) {
                final String[] tmp = new String[m_preNames.length + 1];
                System.arraycopy(m_preNames, 0, tmp, 0, m_preNames.length);

                tmp[m_preNames.length] = advice;

                m_preNames = new String[m_preNames.length + 1];
                System.arraycopy(tmp, 0, m_preNames, 0, tmp.length);

                m_preIndexes = new int[m_preNames.length];
                for (int i = 0, j = m_preNames.length; i < j; i++) {
                    m_preIndexes[i] = AspectWerkz.getSystem(m_uuid).
                            getAdviceIndexFor(m_preNames[i]);
                }
            }
        }
    }

    /**
     * Adds post advice to the pointcut.
     *
     * @param advice the name of the advice to add
     */
    public void addPostAdvice(final String advice) {
        if (advice == null || advice.trim().length() == 0) throw new IllegalArgumentException("name of advice to add can not be null or an empty string");
        synchronized (m_postNames) {
            synchronized (m_postIndexes) {
                final String[] tmp = new String[m_postNames.length + 1];
                System.arraycopy(m_postNames, 0, tmp, 0, m_postNames.length);

                tmp[m_postNames.length] = advice;

                m_postNames = new String[m_postNames.length + 1];
                System.arraycopy(tmp, 0, m_postNames, 0, tmp.length);

                m_postIndexes = new int[m_postNames.length];
                for (int i = 0, j = m_postNames.length; i < j; i++) {
                    m_postIndexes[i] = AspectWerkz.getSystem(m_uuid).
                            getAdviceIndexFor(m_postNames[i]);
                }
            }
        }
    }

    /**
     * Adds pre advices to the pointcut.
     *
     * @param advicesToAdd the advices to add
     */
    public void addPreAdvices(final String[] advicesToAdd) {
        for (int i = 0; i < advicesToAdd.length; i++) {
            if (advicesToAdd[i] == null || advicesToAdd[i].trim().length() == 0) throw new IllegalArgumentException("name of advice to add can not be null or an empty string");
        }
        synchronized (m_preNames) {
            synchronized (m_preIndexes) {

                final String[] clone = new String[advicesToAdd.length];
                System.arraycopy(advicesToAdd, 0, clone, 0, advicesToAdd.length);

                final String[] tmp = new String[
                        m_preNames.length + advicesToAdd.length];
                System.arraycopy(m_preNames, 0, tmp, 0, m_preNames.length);
                System.arraycopy(clone, 0, tmp, m_preNames.length, tmp.length);

                m_preNames = new String[tmp.length];
                System.arraycopy(tmp, 0, m_preNames, 0, tmp.length);

                m_preIndexes = new int[m_preNames.length];
                for (int j = 0; j < m_preNames.length; j++) {
                    m_preIndexes[j] = AspectWerkz.getSystem(m_uuid).
                            getAdviceIndexFor(m_preNames[j]);
                }
            }
        }
    }

    /**
     * Adds post advices to the pointcut.
     *
     * @param advicesToAdd the advices to add
     */
    public void addPostAdvices(final String[] advicesToAdd) {
        for (int i = 0; i < advicesToAdd.length; i++) {
            if (advicesToAdd[i] == null || advicesToAdd[i].trim().length() == 0) throw new IllegalArgumentException("name of advice to add can not be null or an empty string");
        }
        synchronized (m_postNames) {
            synchronized (m_postIndexes) {

                final String[] clone = new String[advicesToAdd.length];
                System.arraycopy(advicesToAdd, 0, clone, 0, advicesToAdd.length);

                final String[] tmp = new String[m_postNames.length + advicesToAdd.length];
                System.arraycopy(m_postNames, 0, tmp, 0, m_postNames.length);
                System.arraycopy(clone, 0, tmp, m_postNames.length, tmp.length);

                m_postNames = new String[tmp.length];
                System.arraycopy(tmp, 0, m_postNames, 0, tmp.length);

                m_postIndexes = new int[m_postNames.length];
                for (int j = 0; j < m_postNames.length; j++) {
                    m_postIndexes[j] = AspectWerkz.getSystem(m_uuid).
                            getAdviceIndexFor(m_postNames[j]);
                }
            }
        }
    }

    /**
     * Removes a pre advice from the pointcut.
     *
     * @param advice the name of the pre advice to remove
     */
    public void removePreAdvice(final String advice) {
        if (advice == null || advice.trim().length() == 0) throw new IllegalArgumentException("name of advice to remove can not be null or an empty string");
        synchronized (m_preNames) {
            synchronized (m_preIndexes) {
                int index = -1;
                for (int i = 0; i < m_preNames.length; i++) {
                    if (m_preNames[i].equals(advice)) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) throw new RuntimeException("can not remove pre advice with the name " + advice + ": no such advice");

                final String[] names = new String[m_preNames.length - 1];
                int j, k;
                for (j = 0, k = 0; j < index; j++, k++) {
                    names[j] = m_preNames[j];
                }
                j++;
                for (; j < m_preNames.length; j++, k++) {
                    names[k] = m_preNames[j];
                }
                m_preNames = new String[names.length];
                System.arraycopy(names, 0, m_preNames, 0, names.length);

                final int[] indexes = new int[m_preIndexes.length - 1];
                for (j = 0, k = 0; j < index; j++, k++) {
                    indexes[j] = m_preIndexes[j];
                }
                j++;
                for (; j < m_preIndexes.length; j++, k++) {
                    indexes[k] = m_preIndexes[j];
                }
                m_preIndexes = new int[indexes.length];
                System.arraycopy(indexes, 0, m_preIndexes, 0, indexes.length);
            }
        }
    }

    /**
     * Removes a post advice from the pointcut.
     *
     * @param advice the name of the pre advice to remove
     */
    public void removePostAdvice(final String advice) {
        if (advice == null || advice.trim().length() == 0) throw new IllegalArgumentException("name of advice to remove can not be null or an empty string");
        synchronized (m_postNames) {
            synchronized (m_postIndexes) {
                int index = -1;
                for (int i = 0; i < m_postNames.length; i++) {
                    if (m_postNames[i].equals(advice)) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) throw new RuntimeException("can not remove post advice with the name " + advice + ": no such advice");

                final String[] names = new String[m_postNames.length - 1];
                int j, k;
                for (j = 0, k = 0; j < index; j++, k++) {
                    names[j] = m_postNames[j];
                }
                j++;
                for (; j < m_postNames.length; j++, k++) {
                    names[k] = m_postNames[j];
                }
                m_postNames = new String[names.length];
                System.arraycopy(names, 0, m_postNames, 0, names.length);

                final int[] indexes = new int[m_postIndexes.length - 1];
                for (j = 0, k = 0; j < index; j++, k++) {
                    indexes[j] = m_postIndexes[j];
                }
                j++;
                for (; j < m_postIndexes.length; j++, k++) {
                    indexes[k] = m_postIndexes[j];
                }
                m_postIndexes = new int[indexes.length];
                System.arraycopy(indexes, 0, m_postIndexes, 0, indexes.length);
            }
        }
    }

    /**
     * Checks if the pointcuts has a certain pre advice.
     *
     * @param advice the advice to check for existence
     * @return boolean
     */
    public boolean hasPreAdvice(final String advice) {
        for (int i = 0; i < m_preNames.length; i++) {
            if (m_preNames[i].equals(advice)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the pointcuts has a certain post advice.
     *
     * @param advice the advice to check for existence
     * @return boolean
     */
    public boolean hasPostAdvice(final String advice) {
        for (int i = 0; i < m_postNames.length; i++) {
            if (m_postNames[i].equals(advice)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the advices in the form of an array with advice/index tuples.
     * To be used when a reordering of the advices is necessary.
     *
     * @return the current advice/index tuple array
     */
    public AdviceIndexTuple[] getPreAdviceIndexTuples() {
        synchronized (m_preIndexes) {
            synchronized (m_preNames) {
                final AdviceIndexTuple[] tuples = new AdviceIndexTuple[m_preNames.length];
                for (int i = 0; i < m_preNames.length; i++) {
                    tuples[i] = new AdviceIndexTuple(m_preNames[i], m_preIndexes[i]);
                }
                return tuples;
            }
        }
    }

    /**
     * Sets the advices. To be used when a reordering of the advices is necessary.
     *
     * @param tuple the new advice/index tuple array
     */
    public void setPreAdviceIndexTuples(final AdviceIndexTuple[] tuple) {
        synchronized (m_preIndexes) {
            synchronized (m_preNames) {
                m_preNames = new String[tuple.length];
                m_preIndexes = new int[tuple.length];
                for (int i = 0; i < tuple.length; i++) {
                    m_preNames[i] = tuple[i].getName();
                    m_preIndexes[i] = tuple[i].getIndex();
                }
            }
        }
    }

    /**
     * Returns the advices in the form of an array with advice/index tuples.
     * To be used when a reordering of the advices is necessary.
     *
     * @return the current advice/index tuple array
     */
    public AdviceIndexTuple[] getPostAdviceIndexTuples() {
        synchronized (m_postIndexes) {
            synchronized (m_postNames) {
                final AdviceIndexTuple[] tuples = new AdviceIndexTuple[m_postNames.length];
                for (int i = 0; i < m_postNames.length; i++) {
                    tuples[i] = new AdviceIndexTuple(m_postNames[i], m_postIndexes[i]);
                }
                return tuples;
            }
        }
    }

    /**
     * Sets the advices. To be used when a reordering of the advices is necessary.
     *
     * @param tuple the new advice/index tuple array
     */
    public void setPostAdviceIndexTuples(final AdviceIndexTuple[] tuple) {
        synchronized (m_postIndexes) {
            synchronized (m_postNames) {
                m_postNames = new String[tuple.length];
                m_postIndexes = new int[tuple.length];
                for (int i = 0; i < tuple.length; i++) {
                    m_postNames[i] = tuple[i].getName();
                    m_postIndexes[i] = tuple[i].getIndex();
                }
            }
        }
    }

    /**
     * Returns a list with the indexes for the pre advices for the pointcut.
     *
     * @return the pre advice indexes
     */
    public int[] getPreAdviceIndexes() {
        return m_preIndexes;
    }

    /**
     * Returns a list with the names for the pre advices for the pointcut.
     *
     * @return the pre advice names
     */
    public String[] getPreAdviceNames() {
        return m_preNames;
    }

    /**
     * Returns a list with the indexes for the post advices for the pointcut.
     *
     * @return the pre advice indexes
     */
    public int[] getPostAdviceIndexes() {
        return m_postIndexes;
    }

    /**
     * Returns a list with the names for the post advices for the pointcut.
     *
     * @return the post advice names
     */
    public String[] getPostAdviceNames() {
        return m_postNames;
    }

    /**
     * Returns the expression of the pointcut.
     *
     * @return the expression
     */
    public String getExpression() {
        return m_expression;
    }

    /**
     * Sets the pre advices.
     * Caution: the index and name arrays have to be in synch.
     *
     * @param indexes the new pre advice index array
     * @param names the new pre advice names array
     */
    public void setPreAdvices(final int[] indexes, final String[] names) {
        synchronized (m_preIndexes) {
            synchronized (m_preNames) {
                m_preIndexes = indexes;
                m_preNames = names;
            }
        }
    }

    /**
     * Sets the post advices.
     * Caution: the index and name arrays have to be in synch.
     *
     * @param indexes the new post advice index array
     * @param names the new post advice names array
     */
    public void setPostAdvices(final int[] indexes, final String[] names) {
        synchronized (m_postIndexes) {
            synchronized (m_postNames) {
                m_postIndexes = indexes;
                m_postNames = names;
            }
        }
    }

    /**
     * Checks if the pointcut matches a certain join point.
     *
     * @param className the name of the class
     * @param fieldMetaData the meta-data for the field
     * @return boolean
     */
    public boolean matches(final String className,
                           final FieldMetaData fieldMetaData) {
        JexlContext jexlContext = JexlHelper.createContext();

        for (Iterator it = m_pointcutDefs.entrySet().iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry)it.next();
            String name = (String)entry.getKey();
            PointcutPatternTuple pointcutPattern = (PointcutPatternTuple)entry.getValue();

            if (pointcutPattern.getClassPattern().matches(className) &&
                    ((FieldPattern)pointcutPattern.getPattern()).matches(fieldMetaData)) {
                jexlContext.getVars().put(name, Boolean.TRUE);
            }
            else {
                jexlContext.getVars().put(name, Boolean.FALSE);
            }
        }
        try {
            Expression e = ExpressionFactory.createExpression(m_expression);
            Boolean result = (Boolean)e.evaluate(jexlContext);

            if (result.booleanValue()) {
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            throw new WrappedRuntimeException(e);
        }
    }
}
